package com.yzz.javamail;

public class MailInfo {

	private String fromUser;// 发件人
	private String[] toUsers;// 收件人
	private String[] ccUsers;// 抄送人
	private String[] bccUsers;// 密送人
	private String subject;// 主题
	private String content;// 内容
	private String[] files;// 文件路径

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String[] getToUsers() {
		return toUsers;
	}

	public void setToUsers(String[] toUsers) {
		this.toUsers = toUsers;
	}

	public String[] getCcUsers() {
		return ccUsers;
	}

	public void setCcUsers(String[] ccUsers) {
		this.ccUsers = ccUsers;
	}

	public String[] getBccUsers() {
		return bccUsers;
	}

	public void setBccUsers(String[] bccUsers) {
		this.bccUsers = bccUsers;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

}
