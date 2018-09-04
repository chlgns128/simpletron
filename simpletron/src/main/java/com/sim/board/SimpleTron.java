package com.sim.board;

public class SimpleTron {
	private int smno;
	private int memory;
	private int acm; // Accumulator register.
	private int ic; // Location in memory.
	private int ir; // Next instruction.
	private int opcode; // Left two digits of instruction.
	private int opand; // Rightmost two digits of instruction.%>

	public int getSmno() {
		return smno;
	}

	public void setSmno(int smno) {
		this.smno = smno;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getAcm() {
		return acm;
	}

	public void setAcm(int acm) {
		this.acm = acm;
	}

	public int getIc() {
		return ic;
	}

	public void setIc(int ic) {
		this.ic = ic;
	}

	public int getIr() {
		return ir;
	}

	public void setIr(int ir) {
		this.ir = ir;
	}

	public int getOpcode() {
		return opcode;
	}

	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}

	public int getOpand() {
		return opand;
	}

	public void setOpand(int opand) {
		this.opand = opand;
	}
}
