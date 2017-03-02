package com.mcpfp.web.common.util.recognition.IK;

public class Segment {
	private String term;      /*得到的分词*/
	private String type;      /*分词的类型*/
	private int startoffset;  /*开始位置*/
	private int endoffset;    /*结束位置*/
	
	public int getStartoffset() {
		return startoffset;
	}
	public void setStartoffset(int startoffset) {
		this.startoffset = startoffset;
	}
	public int getEndoffset() {
		return endoffset;
	}
	public void setEndoffset(int endoffset) {
		this.endoffset = endoffset;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}