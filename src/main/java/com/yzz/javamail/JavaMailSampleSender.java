package com.yzz.javamail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 简单邮件（带附件的邮件）发送器
 * 
 * @author yzz
 *
 */
public class JavaMailSampleSender {

	// 初始化邮箱服务器参数
	public static Properties props = PropertiesLoader.loadProperties("javamail.properties");

	/**
	 * 发送文本邮件
	 * 
	 * @param mailInfo
	 * @return
	 */
	public static boolean sendTextMail(MailInfo mailInfo) {
		boolean result = false;

		String fromUser = mailInfo.getFromUser();
		String[] toUsers = mailInfo.getToUsers();
		String[] ccUsers = mailInfo.getCcUsers();
		String[] bccUsers = mailInfo.getBccUsers();
		String subject = mailInfo.getSubject();
		String content = mailInfo.getContent();
		String[] files = mailInfo.getFiles();
		Date sentDate = mailInfo.getSentDate();

		int toLength = toUsers.length;

		if (toUsers == null || toLength <= 0) {
			return false;
		}
		// 设置环境信息
		Session session = Session.getInstance(props);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		Transport transport = null;
		try {
			// 设置发件人
			msg.setFrom(new InternetAddress(fromUser));

			Address[] toUsersAddress = new Address[toLength];
			// 设置收件人
			InternetAddress internetAddressTo = null;
			for (int i = 0; i < toLength; i++) {
				internetAddressTo = new InternetAddress(toUsers[i]);
				toUsersAddress[i] = internetAddressTo;
			}
			msg.setRecipients(RecipientType.TO, toUsersAddress);// 收件人
			// 抄送人
			int ccLength = 0;
			if (ccUsers != null && (ccLength = ccUsers.length) > 0) {

				Address[] ccUsersAddress = new Address[ccLength];
				// 设置抄送人
				InternetAddress internetAddressCC = null;
				for (int i = 0; i < ccLength; i++) {
					internetAddressCC = new InternetAddress(ccUsers[i]);
					ccUsersAddress[i] = internetAddressCC;
				}
				msg.setRecipients(RecipientType.CC, ccUsersAddress);// 抄送人
			}

			// 密送人
			int bccLength = 0;
			if (bccUsers != null && (bccLength = bccUsers.length) > 0) {

				Address[] bccUsersAddress = new Address[bccLength];
				// 设置密送人
				InternetAddress internetAddressBCC = null;
				for (int i = 0; i < bccLength; i++) {
					internetAddressBCC = new InternetAddress(bccUsers[i]);
					bccUsersAddress[i] = internetAddressBCC;
				}
				msg.setRecipients(RecipientType.BCC, bccUsersAddress);// 密送人
			}

			msg.setSentDate(sentDate);//发信日期
			// 设置主题
			msg.setSubject(subject);
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart textBody = new MimeBodyPart();
			// 设置邮件内容
			textBody.setContent(content, "text/plain; charset=UTF-8");
			mainPart.addBodyPart(textBody);
			/* 添加附件 */
			if (files != null && files.length > 0) {
				File usFile = null;
				MimeBodyPart fileBody = null;
				DataSource source = null;
				for (String file : files) {
					usFile = new File(file);
					if (!usFile.exists()) {
						continue;
					}
					fileBody = new MimeBodyPart();
					source = new FileDataSource(file);
					fileBody.setDataHandler(new DataHandler(source));
					fileBody.setFileName(usFile.getName());
					mainPart.addBodyPart(fileBody);
				}
			}
			// 将MiniMultipart对象设置为邮件内容
			msg.setContent(mainPart);

			transport = session.getTransport();
			// 连接邮件服务器
			transport.connect(props.getProperty("mail.username"), props.getProperty("mail.password"));
			// 发送邮件
			transport.sendMessage(msg, msg.getAllRecipients());

			result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 发送HTML邮件
	 * 
	 * @param mailInfo
	 * @return
	 */
	public static boolean sendHtmlMail(MailInfo mailInfo) {
		boolean result = false;

		String fromUser = mailInfo.getFromUser();
		String[] toUsers = mailInfo.getToUsers();
		String[] ccUsers = mailInfo.getCcUsers();
		String[] bccUsers = mailInfo.getBccUsers();
		String subject = mailInfo.getSubject();
		String content = mailInfo.getContent();
		String[] files = mailInfo.getFiles();
		Date sentDate = mailInfo.getSentDate();

		int toLength = toUsers.length;

		if (toUsers == null || toLength <= 0) {
			return false;
		}
		// 设置环境信息
		Session session = Session.getInstance(props);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		Transport transport = null;
		try {
			// 设置发件人
			msg.setFrom(new InternetAddress(fromUser));

			Address[] toUsersAddress = new Address[toLength];
			// 设置收件人
			InternetAddress internetAddressTo = null;
			for (int i = 0; i < toLength; i++) {
				internetAddressTo = new InternetAddress(toUsers[i]);
				toUsersAddress[i] = internetAddressTo;
			}
			msg.setRecipients(RecipientType.TO, toUsersAddress);// 收件人
			// 抄送人
			int ccLength = 0;
			if (ccUsers != null && (ccLength = ccUsers.length) > 0) {
				Address[] ccUsersAddress = new Address[ccLength];
				// 设置抄送人
				InternetAddress internetAddressCC = null;
				for (int i = 0; i < ccLength; i++) {
					internetAddressCC = new InternetAddress(ccUsers[i]);
					ccUsersAddress[i] = internetAddressCC;
				}
				msg.setRecipients(RecipientType.CC, ccUsersAddress);// 抄送人
			}

			// 密送人
			int bccLength = 0;
			if (bccUsers != null && (bccLength = bccUsers.length) > 0) {
				Address[] bccUsersAddress = new Address[bccLength];
				// 设置密送人
				InternetAddress internetAddressBCC = null;
				for (int i = 0; i < bccLength; i++) {
					internetAddressBCC = new InternetAddress(bccUsers[i]);
					bccUsersAddress[i] = internetAddressBCC;
				}
				msg.setRecipients(RecipientType.BCC, bccUsersAddress);// 密送人
			}

			// 设置主题
			msg.setSubject(subject);
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(content, "text/html; charset=UTF-8");
			mainPart.addBodyPart(html);
			/* 添加附件 */
			if (files != null && files.length > 0) {
				File usFile = null;
				MimeBodyPart fileBody = null;
				DataSource source = null;
				for (String file : files) {
					usFile = new File(file);
					if (!usFile.exists()) {
						continue;
					}
					fileBody = new MimeBodyPart();
					source = new FileDataSource(file);
					fileBody.setDataHandler(new DataHandler(source));
					fileBody.setFileName(usFile.getName());
					mainPart.addBodyPart(fileBody);
				}
			}

			msg.setSentDate(sentDate);//发信日期
			// 将MiniMultipart对象设置为邮件内容
			msg.setContent(mainPart);

			transport = session.getTransport();
			// 连接邮件服务器
			transport.connect(props.getProperty("mail.username"), props.getProperty("mail.password"));
			// 发送邮件
			transport.sendMessage(msg, msg.getAllRecipients());

			result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			try {
				if (transport != null) {
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static void main(String[] args) throws MessagingException {
		String fromUser = "18718995466@163.com";
		String[] toUsers = new String[] { "18718995466@163.com" };
		String[] ccUsers = new String[] { "test@qq.com" };
		String[] bccUsers = new String[] {};
		String subject = "JavaMail测试";
		String content = "<span style='color:red;'>邮箱内容</span>";

		String[] files = new String[] { "C:/Users/Administrator/Desktop/pager/articles-div-pager.png",
				"C:/Users/Administrator/Desktop/pager/table-pager.png" };

		MailInfo mailInfo = new MailInfo();
		mailInfo.setFromUser(fromUser);
		mailInfo.setToUsers(toUsers);
		mailInfo.setCcUsers(ccUsers);
		mailInfo.setBccUsers(bccUsers);
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		mailInfo.setFiles(files);
		mailInfo.setSentDate(new Date());

		sendTextMail(mailInfo);
//		 sendHtmlMail(mailInfo);
		System.out.println("ok");
	}
}
