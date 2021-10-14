package com.fizbiz.backend.notification;

import com.fizbiz.backend.exception.FizbizException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailSenderServiceImpl implements NotificationService {

    private static final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    private final static String USERNAME = "so.ifeoluwah@gmail.com";
    private final static String PASSWORD = "uwafe5983";

    @Override
    @Async
    public void sendNotification( String fromAddress, String senderName, String toAddress, String subject, String redirectLink, String content) throws FizbizException {
        Properties mailProp = mailSender.getJavaMailProperties();
        mailProp.setProperty("mail.mime.address.strict", "false");
        mailProp.put("mail.transport.protocol", "smtp");
        mailProp.put("mail.smtp.auth", "true");
        mailProp.put("mail.smtp.starttls.enable", "true");
        mailProp.put("mail.smtp.starttls.required", "true");
        mailProp.put("mail.debug", "true");
        mailProp.put("mail.smtp.ssl.enable", "true");
        mailProp.put("mail.host", "smtp.gmail.com");
        mailProp.put("mail.port", 465);
        mailProp.put("mail.default-encoding", "UTF-8");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try{
            helper.setFrom(fromAddress, senderName);
            helper.setTo(toAddress);
            helper.setSubject(subject);
            helper.setText(content, true);

        } catch (Exception ex) {
            throw new FizbizException("There is a problem with the encoding of your message", ex);
        }
//        } catch (MessagingException ex){
//            throw new FizbizException("There is a problem with your message", ex);
//        }
        mailSender.setUsername(USERNAME);
        mailSender.setPassword(PASSWORD);
        mailSender.send(mimeMessage);
    }

}
