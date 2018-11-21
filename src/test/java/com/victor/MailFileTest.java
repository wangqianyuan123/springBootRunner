package com.victor;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 通过单元测试来实现一封简单邮件的发送：
 * @author victor
 *
 */
    @RunWith(SpringRunner.class)
    @SpringBootTest
	public class MailFileTest {

		@Autowired
		private JavaMailSender mailSender;

		/**
		 * 实际使用过程中，我们还可能会带上附件、或是使用邮件模块等。
		 * 这个时候我们就需要使用MimeMessage来设置复杂一些的邮件内容
		 * @throws Exception
		 */
		@Test
		public void sendAttachmentsMail() throws Exception {

			MimeMessage mimeMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("896943899@qq.com");
			helper.setTo("wqyhbmc@163.com");
			helper.setSubject("主题：有附件");
			helper.setText("有附件的邮件");

			FileSystemResource file = new FileSystemResource(new File("IMG_1422.JPG"));
			helper.addAttachment("附件-1.jpg", file);
			helper.addAttachment("附件-2.jpg", file);

			mailSender.send(mimeMessage);

		}

		
	/**
	 * 除了发送附件之外，我们在邮件内容中可能希望通过嵌入图片等静态资源， 让邮件获得更好的阅读体验，而不是从附件中查看具体图片
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendInlineMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("896943899@qq.com");
		helper.setTo("896943899@qq.com");
		helper.setSubject("主题：嵌入静态资源");
		helper.setText("<html><body>图片为<img src=\"cid:IMG_1422.JPG\" ></body></html>", true);

		FileSystemResource file = new FileSystemResource(new File("IMG_1422.JPG"));
		// 这里需要注意的是addInline函数中资源名称IMG_1422需要与正文中cid:IMG_1422对应起来
		helper.addInline("IMG_1422", file);

		mailSender.send(mimeMessage);

	}
	}

