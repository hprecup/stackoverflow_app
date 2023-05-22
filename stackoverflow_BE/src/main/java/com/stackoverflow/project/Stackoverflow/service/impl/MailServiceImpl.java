package com.stackoverflow.project.Stackoverflow.service.impl;

import com.stackoverflow.project.Stackoverflow.model.MailDetail;
import com.stackoverflow.project.Stackoverflow.service.MailService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSimpleMail(MailDetail mailDetail) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(mailDetail.getRecipient());
            mailMessage.setText(mailDetail.getMsgBody());
            mailMessage.setSubject(mailDetail.getSubject());

            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending email!");
        }
    }
}
