package com.backend.ecom.services;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @SneakyThrows
    public void sendEmail(String to, String body, String topic) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(new InternetAddress("digiworldwebsite@gmail.com", "DiGi World"));
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(topic);
        mimeMessageHelper.setText(body);
        javaMailSender.send(mimeMessage);
    }
}
