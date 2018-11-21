package com.victor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 通过单元测试来实现一封简单邮件的发送：
 * @author victor
 *
 */
    @RunWith(SpringRunner.class)
    @SpringBootTest
	public class MailOneTests {

		@Autowired
		private JavaMailSender mailSender;

		/**
		 * 我们通过使用SimpleMailMessage实现了简单的邮件发送
		 * @throws Exception
		 */
		@Test
		public void sendSimpleMail() throws Exception {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("896943899@qq.com");
			message.setTo("896943899@qq.com");
			message.setSubject("主题：简单邮件");
			message.setText("测试邮件内容");

			mailSender.send(message);
		}

	}

