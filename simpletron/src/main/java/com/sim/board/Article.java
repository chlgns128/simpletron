package com.sim.board;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {

	private int articleNo;
	private String boardCd;
	private String title;
	private String content;
	private String id;
	private int hit;
	private Date regdate;
	private int attachFileNum;
	private int commentNum;
	private int anonymous;
	private int recommend;

	public static final String ENTER = System.getProperty("line.separator");

	public int getAttachFileNum() {
		return attachFileNum;
	}

	public void setAttachFileNum(int attachFileNum) {
		this.attachFileNum = attachFileNum;
	}

	public int getArticleNo() {
		return articleNo;
	}

	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}

	public String getBoardCd() {
		return boardCd;
	}

	public void setBoardCd(String boardCd) {
		this.boardCd = boardCd;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public int getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(int commentNum) {
		this.commentNum = commentNum;
	}

	public int getAnonymous() {
		return anonymous;
	}

	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public String getWriteDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return sdf.format(regdate);
	}

	public String getWriteDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(regdate);
	}

	public String getHtmlContent() {
		if (content != null) {
			return content.replaceAll(ENTER, "<br />");
		}
		return null;
	}

}
