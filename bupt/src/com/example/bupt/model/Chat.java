package com.example.bupt.model;

import java.util.Date;


public class Chat extends Entity{
	
	private int chatId;
	private String chatFace;
	private String chatContent;
	private int chatAuthorId;
	private Date chatPubTime;
	private boolean chatIsReceive = true;
	
	public String getChatFace() {
		return chatFace;
	}
	public void setChatFace(String chatFace) {
		this.chatFace = chatFace;
	}
	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public int getChatAuthorId() {
		return chatAuthorId;
	}
	public void setChatAuthorId(int chatAuthorId) {
		this.chatAuthorId = chatAuthorId;
	}
	public Date getChatPubTime() {
		return chatPubTime;
	}
	public void setChatPubTime(Date chatPubTime) {
		this.chatPubTime = chatPubTime;
	}
	public boolean getChatIsReceive() {
		return chatIsReceive;
	}
	public void setChatIsReceive(boolean chatIsReceive) {
		this.chatIsReceive = chatIsReceive;
	}
	public int getChatId() {
		return chatId;
	}
	public void setChatId(int chatId) {
		this.chatId = chatId;
	}
	
}
