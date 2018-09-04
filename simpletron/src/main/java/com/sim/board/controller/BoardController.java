package com.sim.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sim.board.Article;
import com.sim.board.AttachFile;
import com.sim.board.BoardService;
import com.sim.board.Comment;
import com.sim.board.SimpleTron;
import com.sim.commons.PagingHelper;
import com.sim.commons.WebContants;

@Controller
@RequestMapping("/board")
public class BoardController implements SimpleTronMethod {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(String boardCd, Integer curPage, String searchWord, Model model) throws Exception {

		if (boardCd == null)
			boardCd = "free";
		if (curPage == null)
			curPage = 1;
		if (searchWord == null)
			searchWord = "";

		int numPerPage = 10;// 페이지당 레코드 수 지정
		int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정

		int totalRecord = boardService.getTotalRecord(boardCd, searchWord);

		PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);
		boardService.setPagingHelper(pagingHelper);

		int start = pagingHelper.getStartRecord();
		int end = pagingHelper.getEndRecord();

		ArrayList<Article> list = boardService.getArticleList(boardCd, searchWord, start, end);
		String boardNm = boardService.getBoardNm(boardCd);
		Integer no = boardService.getListNo();
		Integer prevLink = boardService.getPrevLink();
		Integer nextLink = boardService.getNextLink();
		Integer firstPage = boardService.getFirstPage();
		Integer lastPage = boardService.getLastPage();
		int[] pageLinks = boardService.getPageLinks();

		// 목록을 위한 데이터
		model.addAttribute("list", list);
		model.addAttribute("boardNm", boardNm);
		model.addAttribute("boardCd", boardCd);// boardCd는 null 값이면 free로 만들어야 하므로

		model.addAttribute("no", no);
		model.addAttribute("prevLink", prevLink);
		model.addAttribute("nextLink", nextLink);
		model.addAttribute("firstPage", firstPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageLinks", pageLinks);
		model.addAttribute("curPage", curPage);// curPage는 null 값이면 1로 만들어야 하므로

		return "board/list";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)

	public String write(String boardCd, Model model) throws Exception {

		// 게시판 이름
		String boardNm = boardService.getBoardNm(boardCd);
		model.addAttribute("boardNm", boardNm);

		return "board/writeform";

	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(Article article, MultipartHttpServletRequest mpRequest) throws Exception {

		boardService.insert(article);
		article.setArticleNo(boardService.getNewArticle().getArticleNo());

		// 파일 업로드
		Iterator<String> it = mpRequest.getFileNames();
		List<MultipartFile> fileList = new ArrayList();
		while (it.hasNext()) {
			MultipartFile multiFile = mpRequest.getFile((String) it.next());
			if (multiFile != null && multiFile.getSize() > 0) {
				String filename = multiFile.getOriginalFilename();
				multiFile.transferTo(new File(WebContants.BASE_PATH + filename));
				fileList.add(multiFile);
			}
		}

		// 파일데이터 삽입
		int size = fileList.size();
		for (int i = 0; i < size; i++) {
			MultipartFile mpFile = fileList.get(i);
			AttachFile attachFile = new AttachFile();
			String filename = mpFile.getOriginalFilename();
			attachFile.setFilename(filename);
			attachFile.setFiletype(mpFile.getContentType());
			attachFile.setFilesize(mpFile.getSize());
			attachFile.setArticleNo(article.getArticleNo());
			boardService.insertAttachFile(attachFile);
		}

		return "redirect:/board/list?boardCd=" + article.getBoardCd();
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(int articleNo, String boardCd, Integer curPage, String searchWord, Model model)
			throws Exception {

		int numPerPage = 10;// 페이지당 레코드 수 지정
		int pagePerBlock = 10;// 페이지 링크의 그룹(block)의 크기 지정
		if (searchWord == null)
			searchWord = ""; // 검색어가 null 이면 ""으로 변경

		// 목록보기
		int totalRecord = boardService.getTotalRecord(boardCd, searchWord);
		System.out.println(curPage);
		PagingHelper pagingHelper = new PagingHelper(totalRecord, curPage, numPerPage, pagePerBlock);
		boardService.setPagingHelper(pagingHelper);

		int start = pagingHelper.getStartRecord();
		int end = pagingHelper.getEndRecord();

		ArrayList<Article> list = boardService.getArticleList(boardCd, searchWord, start, end);
		String boardNm = boardService.getBoardNm(boardCd);
		Integer no = boardService.getListNo();
		Integer prevLink = boardService.getPrevLink();
		Integer nextLink = boardService.getNextLink();
		Integer firstPage = boardService.getFirstPage();
		Integer lastPage = boardService.getLastPage();
		int[] pageLinks = boardService.getPageLinks();

		// 목록을 위한 데이터
		model.addAttribute("list", list);
		model.addAttribute("boardNm", boardNm);

		model.addAttribute("no", no);
		model.addAttribute("prevLink", prevLink);
		model.addAttribute("nextLink", nextLink);
		model.addAttribute("firstPage", firstPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("pageLinks", pageLinks);

		boardService.increaseHit(articleNo);// 조회수 증가

		// 상세보기
		Article thisArticle = boardService.getArticle(articleNo);
		Article prevArticle = boardService.getPrevArticle(articleNo, boardCd, searchWord);
		Article nextArticle = boardService.getNextArticle(articleNo, boardCd, searchWord);
		ArrayList<AttachFile> attachFileList = boardService.getAttachFileList(articleNo);
		ArrayList<Comment> commentList = boardService.getCommentList(articleNo);

		model.addAttribute("thisArticle", thisArticle);
		model.addAttribute("prevArticle", prevArticle);
		model.addAttribute("nextArticle", nextArticle);
		model.addAttribute("attachFileList", attachFileList);
		model.addAttribute("commentList", commentList);

		return "board/view";
	}

	@RequestMapping(value = "/commentAdd", method = RequestMethod.POST)
	public String commentAdd(Integer articleNo, String boardCd, Integer curPage, String searchWord, String memo)
			throws Exception {

		Comment comment = new Comment();
		comment.setMemo(memo);
		comment.setEmail("bond007@gmail.org");
		comment.setArticleNo(articleNo);
		boardService.insertComment(comment);

		// searchWord = URLEncoder.encode(searchWord,"UTF-8");

		return "redirect:/board/view?articleNo=" + articleNo + "&boardCd=" + boardCd + "&curPage=" + curPage
				+ "&searchWord=" + searchWord;

	}

	@RequestMapping(value = "/commentUpdate", method = RequestMethod.POST)
	public String commentUpdate(Integer commentNo, Integer articleNo, String boardCd, Integer curPage,
			String searchWord, String memo) throws Exception {

		Comment comment = boardService.getComment(commentNo);
		comment.setMemo(memo);
		boardService.updateComment(comment);
		// searchWord = URLEncoder.encode(searchWord, "UTF-8");

		return "redirect:/board/view?articleNo=" + articleNo + "&boardCd=" + boardCd + "&curPage=" + curPage
				+ "&searchWord=" + searchWord;

	}

	@RequestMapping(value = "/commentDel", method = RequestMethod.POST)
	public String commentDel(Integer commentNo, Integer articleNo, String boardCd, Integer curPage, String searchWord)
			throws Exception {

		boardService.deleteComment(commentNo);

		// searchWord = URLEncoder.encode(searchWord,"UTF-8");

		return "redirect:/board/view?articleNo=" + articleNo + "&boardCd=" + boardCd + "&curPage=" + curPage
				+ "&searchWord=" + searchWord;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(int articleNo, String boardCd, Integer curPage, String searchWord) throws Exception {

		boardService.delete(articleNo);

		return "redirect:/board/list?boardCd=" + boardCd + "&curPage=" + curPage + "&searchWord=" + searchWord;
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String update(int articleNo, String boardCd, Model model) throws Exception {

		Article thisArticle = boardService.getArticle(articleNo);
		String boardNm = boardService.getBoardNm(boardCd);

		// 수정페이지에서 보일 게시글 정보
		model.addAttribute("thisArticle", thisArticle);
		model.addAttribute("boardNm", boardNm);

		return "board/modifyForm";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String update(Article article, Integer curPage, String boardCd, String searchWord, Model model,
			MultipartHttpServletRequest mpRequest) throws Exception {

		boardService.update(article);

		// 파일업로드
		List<MultipartFile> fileList = mpRequest.getFiles("upload");
		for (MultipartFile mf : fileList) {
			String filename = mf.getOriginalFilename();
			mf.transferTo(new File(WebContants.BASE_PATH + filename));
		}

		// 파일데이터 삽입
		int size = fileList.size();
		for (int i = 0; i < size; i++) {
			MultipartFile mpFile = fileList.get(i);
			AttachFile attachFile = new AttachFile();
			String filename = mpFile.getOriginalFilename();
			attachFile.setFilename(filename);
			attachFile.setFiletype(mpFile.getContentType());
			attachFile.setFilesize(mpFile.getSize());
			attachFile.setArticleNo(article.getArticleNo());
			boardService.insertAttachFile(attachFile);
		}

		return "redirect:/board/view?articleNo=" + article.getArticleNo() + "&boardCd=" + article.getBoardCd()
				+ "&curPage=" + curPage + "&searchWord=" + searchWord;
	}

	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String download(String filename, Model model) {
		model.addAttribute("filename", filename);
		return "inc/download";
	}

	@RequestMapping(value = "/attachFileDel", method = RequestMethod.POST)
	public String attachFileDel(Integer attachFileNo, Integer articleNo, String boardCd, Integer curPage,
			String searchWord) throws Exception {

		boardService.deleteFile(attachFileNo);

		// searchWord = URLEncoder.encode(searchWord,"UTF-8");

		return "redirect:/board/view?articleNo=" + articleNo + "&boardCd=" + boardCd + "&curPage=" + curPage
				+ "&searchWord=" + searchWord;

	}

	@RequestMapping(value = "/readCode", method = RequestMethod.GET)
	public String ReadCodeForm(Model model) throws Exception {

		return "board/readCode";

	}

	@RequestMapping(value = "/resultScreen", method = RequestMethod.POST)
	public String resultCoding(Integer value, Integer operand1, Integer ic, Integer sum, Model model) throws Exception {

		int[] memory = new int[102]; // Main memory for Simpletron.

		memory[0] = 0;

		int accumulator = 0; // Accumulator register.
		int instructionCounter = 0; // Location in memory.
		int instructionRegister = 0; // Next instruction.
		int operationCode = 0; // Left two digits of instruction.
		int operand = 0; // Rightmost two digits of instruction.

		Integer ac = ic;

		System.out.println("받은 카운터 : " + ic);

		SimpleTron sm = new SimpleTron();

		if (operand1 != null && value != null) {
			sm.setIc(operand1);
			sm.setMemory(value);
			boardService.updateMemory(sm);
		}

		System.out.println("오퍼 : " + operand1);
		System.out.println("값 : " + value);

		for (ic = 1; ic < 100; ic++) {

			memory[ic] = boardService.getMemory(ic);
		}

		if (ac != null) {
			for (instructionCounter = ac; instructionCounter <= 100; instructionCounter++) {
				instructionRegister = memory[instructionCounter];
				operationCode = instructionRegister / 100;
				operand = instructionRegister % 100;

				System.out.println("카운터 :" + instructionCounter);

				switch (operationCode) {
				case READ:
					model.addAttribute("ic", instructionCounter + 1);
					model.addAttribute("operand", operand + 1);
					return "board/writeValue";
				case WRITE:
					break;
				case LOAD:
					accumulator = memory[operand + 1];
					sm.setMemory(accumulator);
					boardService.loadMemory(sm);
					break;
				case STORE:
					memory[operand + 1] = accumulator;
					sm.setIc(operand + 1);
					sm.setAcm(accumulator);
					boardService.storeMemory(sm);
					break;
				case ADD:
					accumulator = boardService.getAcm();
					int num = boardService.getMemory(operand + 1);
					System.out.println("겟메모리 : " + num);
					int hsum = accumulator + num;
					System.out.println("합 : " + hsum);
					sm.setMemory(hsum);
					boardService.loadMemory(sm);
					num = 0;
					hsum = 0;
					accumulator += memory[operand + 1];

					break;
				case SUBTRACT:
					accumulator = boardService.getAcm();
					num = boardService.getMemory(operand + 1);
					hsum = accumulator - num;
					sm.setMemory(hsum);
					boardService.loadMemory(sm);
					num = 0;
					hsum = 0;
					accumulator -= memory[operand + 1];

					break;
				case DIVIDE:
					try {
						accumulator = boardService.getAcm();
						num = boardService.getMemory(operand + 1);
						hsum = accumulator / num;
						sm.setMemory(hsum);
						boardService.loadMemory(sm);
						num = 0;
						hsum = 0;
						accumulator /= memory[operand + 1];

					} catch (Exception e) { // Handle any form of error, print it, then exit the program.
						return "board/errorPage";
					}
					break;
				case MULTIPLY:
					accumulator = boardService.getAcm();
					num = boardService.getMemory(operand + 1);
					hsum = accumulator * num;
					sm.setMemory(hsum);
					boardService.loadMemory(sm);
					num = 0;
					hsum = 0;
					accumulator *= memory[operand + 1];

					break;
				case BRANCH:
					accumulator = boardService.getAcm();
					instructionCounter = operand;

					break;
				case BRANCHNEG:
					accumulator = boardService.getAcm();
					if (accumulator < 0) {
						instructionCounter = operand;

					}
					break;
				case BRANCHZERO:
					accumulator = boardService.getAcm();
					if (accumulator == 0) {
						instructionCounter = operand;

					}
					break;
				case HALT:
					System.out.println();
					System.out.println("*** Simpletron execution terminated. ***");

					break;
				default:
					break;
				}

			}
		} else {
			for (instructionCounter = 1; instructionCounter <= 100; instructionCounter++) {
				instructionRegister = memory[instructionCounter];
				operationCode = instructionRegister / 100;
				operand = instructionRegister % 100;

				System.out.println("초기 카운터 :" + instructionCounter);

				switch (operationCode) {
				case READ:
					model.addAttribute("ic", instructionCounter + 1);
					model.addAttribute("operand", operand + 1);
					return "board/writeValue";
				case WRITE:
					break;
				case LOAD:
					accumulator = memory[operand + 1];
					sm.setMemory(accumulator);
					boardService.loadMemory(sm);
					break;
				case STORE:
					memory[operand + 1] = accumulator;
					sm.setIc(operand + 1);
					sm.setAcm(accumulator);
					boardService.storeMemory(sm);
					break;
				case ADD:
					int num = boardService.getMemory(operand + 1);
					int hsum = accumulator + num;
					sm.setMemory(hsum);
					sm.setIc(operand + 1);
					boardService.updateMemory(sm);
					num = 0;
					hsum = 0;
					accumulator += memory[operand + 1];

					break;
				case SUBTRACT:
					num = boardService.getMemory(operand + 1);
					hsum = accumulator - num;
					sm.setMemory(hsum);
					sm.setIc(operand + 1);
					boardService.updateMemory(sm);
					num = 0;
					hsum = 0;
					accumulator -= memory[operand + 1];

					break;
				case DIVIDE:
					try {
						num = boardService.getMemory(operand + 1);
						hsum = accumulator / num;
						sm.setMemory(hsum);
						sm.setIc(operand + 1);
						boardService.updateMemory(sm);
						num = 0;
						hsum = 0;
						accumulator /= memory[operand + 1];

					} catch (Exception e) { // Handle any form of error, print it, then exit the program.
						return "board/errorPage";
					}
					break;
				case MULTIPLY:
					num = boardService.getMemory(operand + 1);
					hsum = accumulator * num;
					sm.setMemory(hsum);
					sm.setIc(operand + 1);
					boardService.updateMemory(sm);
					num = 0;
					hsum = 0;
					accumulator *= memory[operand + 1];

					break;
				case BRANCH:
					instructionCounter = operand;
					break;
				case BRANCHNEG:
					if (accumulator < 0) {
						instructionCounter = operand;
					}
					break;
				case BRANCHZERO:
					if (accumulator == 0) {
						instructionCounter = operand;
					}
					break;
				case HALT:
					System.out.println();
					System.out.println("*** Simpletron execution terminated. ***");

					break;
				default:
					break;
				}

			}
		}

		ArrayList<SimpleTron> list = boardService.getMemoryList();

		for (int i = 0; i < 100; i++) {
			String.format("%04d", list.get(i).getMemory());
		}

		model.addAttribute("list", list);

		return "board/resultScreen";

	}

	@RequestMapping(value = "/readCode", method = RequestMethod.POST)
	public String StartCoding(Integer memory, Integer sum, Model model) throws Exception {

		SimpleTron sm = new SimpleTron();

		sm.setIr(memory);
		sm.setMemory(memory);
		sm.setOpcode(memory / 100);
		sm.setOpand(memory % 100);

		int ic = sm.getIc();

		if (sum == null) // 카운터가 null 일 경우 에러 뜨는 경우 방지
			sum = 0;

		sm.setIc(sum + 1);

		if ((9999 < memory || -9999 > memory) && memory != -99999) {

			model.addAttribute("sum", sum);

			return "board/error";
		}

		else if ((9999 < memory || -9999 > memory) && memory == -99999) {

			model.addAttribute("memory", memory);

			return "board/readCode";
		}

		else {

			boardService.updateMemory(sm);

			model.addAttribute("sum", sum + 1);
			model.addAttribute("memory", memory);

			return "board/readCode";
		}
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String UploadCoding(String boardCd, String id, Model model) throws Exception {
		Article article = new Article();

		article.setId(id);
		article.setBoardCd(boardCd);
		article.setTitle("코드");
		article.setAnonymous(0);
		article.setHit(0);
		article.setRecommend(0);

		ArrayList<SimpleTron> list = boardService.getMemoryList();

		StringBuffer sm = new StringBuffer();

		for (int i = 0; i < 100; i++) {
			if (i % 10 == 0 && i != 0) {
				sm.append("<br>");
			}
			sm.append(String.format("%04d", list.get(i).getMemory()) + "&nbsp;&nbsp;&nbsp;&nbsp;");

		}
		sm.append("<br>" + "accumulator : " + list.get(1).getAcm() + "<br>" + "instructionCounter : "
				+ list.get(99).getIc() + "<br>" + "instructionRegister : " + list.get(1).getIr() + "<br>"
				+ "operationCode : " + list.get(1).getOpcode() + "<br>" + "operand : " + list.get(1).getOpand()
				+ "<br>");

		String sm1 = sm.toString();

		System.out.println(sm1);

		article.setContent(sm1);

		boardService.insert(article);

		boardService.resetMemory();

		return "redirect:/board/list?boardCd=free";

	}
}
