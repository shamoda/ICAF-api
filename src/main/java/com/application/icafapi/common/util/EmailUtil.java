package com.application.icafapi.common.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

import static com.application.icafapi.common.util.QRUtil.generateQR;

@Log4j2
@Service
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailUtil(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@fictionapps.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
        log.info("sending email to: " + to);
    }

    public void sendQR(String name, String to, String subject, String body, String role) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
            helper.addAttachment("Entrance QR Code.png", generateQR(name, to, role));
            helper.setTo(to);
            helper.setText(body);
            helper.setSubject(subject);
            helper.setFrom("noreply@fictionapps.com");
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("sending email to: " + to);
    }
}
