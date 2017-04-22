package com.yzz.javamail;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
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

	/**
	 * 初始化配置参数
	 * 
	 * @return
	 */
	public static Properties initProperties() {
		Properties props = new Properties();
		URL rootPath = JavaMailSampleSender.class.getClassLoader().getResource("");
		String path = rootPath.getPath() + "javamail.properties";
		File file = new File(path);
		InputStream is;
		try {
			is = new FileInputStream(file);
			props.load(is);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return props;
	}

	/**
	 * 发送文本邮件
	 * 
	 * @param fromUser
	 * @param toUsers
	 * @param subject
	 * @param content
	 * @return
	 */
	public static boolean sendTextMail(String fromUser, String[] toUsers, String subject, String content,
			String[] files) {
		boolean result = false;
		// 初始化邮箱服务器参数
		Properties props = initProperties();
		// 设置环境信息
		Session session = Session.getInstance(props);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		try {
			// 设置发件人
			msg.setFrom(new InternetAddress(fromUser));

			Address[] toUsersAddress = new Address[toUsers.length];
			// 设置收件人
			for (int i = 0; i < toUsersAddress.length; i++) {
				toUsersAddress[i] = new InternetAddress(toUsers[i]);
			}
			// 设置主题
			msg.setSubject(subject);

			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart textBody = new MimeBodyPart();
			// 设置邮件内容
			textBody.setContent(content, "text/html; charset=UTF-8");
			mainPart.addBodyPart(textBody);
			/* 添加附件 */
			for (String file : files) {
				File usFile = new File(file);
				MimeBodyPart fileBody = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				fileBody.setDataHandler(new DataHandler(source));
				fileBody.setFileName(usFile.getName());
				mainPart.addBodyPart(fileBody);
			}
			// 将MiniMultipart对象设置为邮件内容
			msg.setContent(mainPart);

			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect(props.getProperty("mail.username"), props.getProperty("mail.password"));
			// 发送邮件
			transport.sendMessage(msg, toUsersAddress);
			// 关闭连接
			transport.close();

			result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 发送HTML邮件
	 * 
	 * @param fromUser
	 * @param toUsers
	 * @param subject
	 * @param content
	 * @return
	 */
	public static boolean sendHtmlMail(String fromUser, String[] toUsers, String subject, String content,
			String[] files) {
		boolean result = false;
		// 初始化邮箱服务器参数
		Properties props = initProperties();
		// 设置环境信息
		Session session = Session.getInstance(props);

		// 创建邮件对象
		Message msg = new MimeMessage(session);
		try {
			// 设置发件人
			msg.setFrom(new InternetAddress(fromUser));

			Address[] toUsersAddress = new Address[toUsers.length];
			// 设置收件人
			for (int i = 0; i < toUsersAddress.length; i++) {
				toUsersAddress[i] = new InternetAddress(toUsers[i]);
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
			for (String file : files) {
				File usFile = new File(file);
				MimeBodyPart fileBody = new MimeBodyPart();
				DataSource source = new FileDataSource(file);
				fileBody.setDataHandler(new DataHandler(source));
				fileBody.setFileName(usFile.getName());
				mainPart.addBodyPart(fileBody);
			}
			// 将MiniMultipart对象设置为邮件内容
			msg.setContent(mainPart);

			Transport transport = session.getTransport();
			// 连接邮件服务器
			transport.connect(props.getProperty("mail.username"), props.getProperty("mail.password"));
			// 发送邮件
			transport.sendMessage(msg, toUsersAddress);
			// 关闭连接
			transport.close();

			result = true;
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static void main(String[] args) throws MessagingException {
		String fromUser = "test@163.com";
		String[] toUsers = new String[] { "test@qq.com" };
		String subject = "JavaMail测试";
		String content = "<span style='color:red;'>邮箱内容</span>";

		String[] files = new String[] { "C:/Users/Administrator/Desktop/pager/articles-div-pager.png" };

		// sendTextMail(fromUser, toUsers, subject, content, files);
		 sendHtmlMail(fromUser, toUsers, subject, content, files);
	}
}
