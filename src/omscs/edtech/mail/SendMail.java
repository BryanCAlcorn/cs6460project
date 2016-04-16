package omscs.edtech.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class SendMail {

    public static void sendFeedback(String mailTo, String emailBody){
        sendEmail(mailTo, "Assignment Feedback", emailBody);
    }

    public static void sendMissingAssignment(String mailTo, String emailBody){
        sendEmail(mailTo, "Missing Assignment", emailBody);
    }

    private static void sendEmail(String mailTo, String subject, String body){
        final EmailCredentials credentials = getEmailCredentials();

        // Get system properties
        Properties properties = System.getProperties();
        properties.put("mail.smtp.user", credentials.getEmail());
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", "587");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        // Get the default Session object.
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(credentials.getEmail(), credentials.getPassword());
                    }
                });

        try{
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(credentials.getEmail()));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));

            // Set Subject: header field
            message.setSubject(subject);

            // Now set the actual message
            message.setContent(body, "text/html; charset=utf-8");

            // Send message
            Transport transport = session.getTransport("smtps");
            transport.connect("smtp.gmail.com", 465, credentials.getEmail(), credentials.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("Sent e-mail successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    private static EmailCredentials getEmailCredentials(){

        InputStream inputStream = null;
        EmailCredentials credentials = new EmailCredentials();
        try {
            Properties prop = new Properties();
            String propFileName = "resources/config.properties";

            inputStream = new FileInputStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and put it in the object
            credentials.setEmail(prop.getProperty("emailFrom"));
            credentials.setPassword(prop.getProperty("emailPassword"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            if(inputStream != null) {
                try {
                    inputStream.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        return credentials;
    }
}
