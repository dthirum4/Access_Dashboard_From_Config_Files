package Config_Files;

import java.io.IOException;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

import Config_Files.Select_Credentials_from_TestData;

import javax.activation.*;

public class Send_Email {
	
	public static void send_email_alert() throws IOException, Exception
	{
		// send email with spreadsheet
		//get email credentials from excel sheet
		final String username = Select_Credentials_from_TestData.get_email_alert_credentials(0, 0);
		final String password = Select_Credentials_from_TestData.get_email_alert_credentials(1, 0);
		
		String toEmail=Select_Credentials_from_TestData.get_email_alert_credentials(2, 0);
		String subject=Select_Credentials_from_TestData.get_email_alert_credentials(3, 0);
		String body=Select_Credentials_from_TestData.get_email_alert_credentials(4, 0);
		//String attachment_path=".\\test-output\\Config_Spreadsheet.xlsx";
		
		// Set up the SMTP server properties
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.office365.com");
		props.put("mail.smtp.port", "587");
		
		// Create a session with an authenticator
		Session session = Session.getInstance(props, 
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		System.out.println("Started sending email");
		
		try {
			//create a new email message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			
			//create message body part
			//MimeBodyPart messageBodyPart = new MimeBodyPart();
			//messageBodyPart.setText(body);
			message.setText(body);
			
	/*		//create attachment body part
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachment_path);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName(source.getName());
			
			System.out.println("Attachment added"); 
			
			//create a multipart and add parts
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			multipart.addBodyPart(attachmentBodyPart); 
			
			//set the complete message parts
			message.setContent(multipart);
			//send the message
			System.out.println("Sending email..."); */
			session.setDebug(true);
			try {
			Transport.send(message);
			System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException, Exception {
		// TODO Auto-generated method stub
		send_email_alert();
	}

}
