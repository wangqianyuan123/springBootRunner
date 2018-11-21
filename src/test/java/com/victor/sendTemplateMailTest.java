package com.victor;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class)
@SpringBootTest
public class sendTemplateMailTest {
	private static Log logger = LogFactory.getLog(sendTemplateMailTest.class);
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private TemplateEngine templateEngine;
	@Test
	public void sendTemplateMail() {
	    //创建邮件正文
	    Context context = new Context();
	    //<span th:text="${name}"></span>   设置变量时 用<span>标签包围
	    context.setVariable("name", "victorWang");
	    String emailContent = templateEngine.process("emailTemplate", context);
	    try {
	    	MimeMessage message = mailSender.createMimeMessage();
	        //true表示需要创建一个multipart message
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom("896943899@qq.com");
	        helper.setTo("896943899@qq.com");
	        helper.setSubject("模板邮件");
	        helper.setText(emailContent, true);

	        mailSender.send(message);
	        logger.info("html邮件发送成功");
	    } catch (MessagingException e) {
	        logger.error("发送html邮件时发生异常！", e);
	    }
	}
}
