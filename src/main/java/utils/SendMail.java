package utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.MultiPartEmail;

public class SendMail {

    public  boolean sendEmail(String from, String password, String to, String subject, String content, String attachmentPath) {

        MultiPartEmail email = new MultiPartEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setStartTLSRequired(true);
        email.setStartTLSEnabled(true);
        email.setSSLOnConnect(true);

        email.setAuthenticator(new DefaultAuthenticator(from, password));

        try {
            email.setFrom(from);
            email.setSubject(subject);
            email.setMsg(content);
            email.addTo(to);

            if (attachmentPath != null) {
                EmailAttachment attachment = new EmailAttachment();
                attachment.setPath(attachmentPath);
                attachment.setDisposition(EmailAttachment.ATTACHMENT);
                attachment.setDescription("Send e-mail");
                attachment.setName("E-mails");

                email.attach(attachment);
            }

            email.send();
            return true;

        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }
}
