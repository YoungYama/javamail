package com.yzz.javamail;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

public class JavaMailSampleReceiver {

	// 初始化邮箱服务器参数
	public static Properties props = PropertiesLoader.loadProperties("javamail.properties");

	public static boolean receiveMailByPop3() {
		boolean result = false;

		// 根据属性新建一个邮件会话
		Session session = Session.getInstance(props);
		// 从会话对象中获得POP3协议的Store对象
		Store store = null;
		Folder folder = null;
		try {
			store = session.getStore(props.getProperty("mail.store.protocol"));
			// 连接邮件服务器
			store.connect(props.getProperty("mail.pop3.host"), props.getProperty("mail.username"),
					props.getProperty("mail.password"));
			// 获取邮件服务器的收件箱
			folder = store.getFolder("INBOX");
			if (folder == null) {
				throw new RuntimeException("No POP3 INBOX");
			}
			// 以只读权限打开收件箱
			folder.open(Folder.READ_ONLY);
			// 获取收件箱中的邮件，也可以使用getMessage(int 邮件的编号)来获取具体某一封邮件
			Message messages[] = folder.getMessages();

			for (int i = 0, n = messages.length; i < n; i++) {
				String subject = messages[i].getSubject();// 获得邮件主题
				Address fromUser = (Address) messages[i].getFrom()[0];// 获得发送者地址
				Address[] toUser = messages[i].getRecipients(RecipientType.TO);// 所有收件人
				// Address[] ccUser =
				// message[i].getRecipients(RecipientType.CC);// 所有抄送人
				// Address[] bccUser =
				// message[i].getRecipients(RecipientType.BCC);// 所有密送人

				Date date = messages[i].getSentDate();
				System.out.println("邮件的主题为: " + subject);
				System.out.println("发件人地址为: " + fromUser);
				System.out.println("发送日期: " + date);
				if (toUser != null && toUser.length > 0) {
					System.out.println("所有收件人: " + toUser[0]);
					// System.out.println("所有抄送人: " + ccUser);
					// System.out.println("所有密送人: " + bccUser);
				}

				try {
					System.out.println("type------" + messages[i].getContentType());
					if (messages[i].isMimeType("multipart/mixed")) {
						MimeMultipart mimeMultipart = (MimeMultipart) messages[i].getContent();

						int count = mimeMultipart.getCount();
						for (int j = 0; j < count; j++) {
							BodyPart bodyPart = mimeMultipart.getBodyPart(j);

							System.out.println("类型：" + bodyPart.getContentType());
							String disposition = bodyPart.getDisposition();
							if (disposition != null && disposition.equals("attachment")) {
								System.out.println(bodyPart.getFileName());
								// 下载附件可以把Folder对象保存在session中，取出对应的Message中的BodyPart的InputStream
								System.out.println(bodyPart.getInputStream());
							} else {
								System.out.println(bodyPart.getContent());
							}
						}
					} else {
						System.out.println(messages[i].getContent());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

				// messages[i].writeTo(os);
				System.out.println("----------------------");

			}

			result = true;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接
			try {
				if (store != null) {
					store.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			try {
				if (folder != null) {
					//boolean 表示是否清除已删除的消息从而更新 folder
					folder.close(false);
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	public static void main(String[] args) {
		receiveMailByPop3();
	}

}
