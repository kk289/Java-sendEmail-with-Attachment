package sendEmailAttachment;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class mailAttachment {

    public static void sendMail(String toRecipient, String myAccountEmail, String password) {

        // Get the session object
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        // Compose the Email
        try {
            // Email: Subject Part
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toRecipient));
            message.setSubject("My First Email with Attachment from Java App");

            // Email: Body Part (Text)
            BodyPart text = new MimeBodyPart();
            text.setText("Hello World, \nExample of sending email in Java through SMTP server provided by the host provider.");

            // Attachment Part
            MimeBodyPart attach = new MimeBodyPart();
            String filename = "/usr/local/bin/JavaAttachmentfile.txt"; // file source
            DataSource source = new FileDataSource(filename);
            attach.setDataHandler(new DataHandler(source));
            attach.setFileName(filename);

            // Multipart object and add MimeBodyPart object to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(text);
            multipart.addBodyPart(attach);

            // Set the Multipart object to the message object
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("Message Sent Successfully");

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("Preparing to send Email");
        String toRecipient = "example@gmail.com";  // Receiver Email Address
        final String myAccountEmail = "example@gmail.com"; // Sender Email Address
        final String password = "xxxxx"; // Sender Email password 

        // pass variable to sendMail Method
        sendMail(toRecipient, myAccountEmail, password);
    }
}

