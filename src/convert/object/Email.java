package convert.object;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public void getPassword(String email, String passwordUser) {
		final String user = "amitshmn@gmail.com";
		final String password = "amit1212";
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);

			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("amitshmn@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject("Password restor");
			message.setContent(
					"<h:body style=background-color:white>" + "Here is your " + "Password=" + passwordUser
							+ "if you didnt ask to restor please change your password  " + "</body>",
					"text/html; charset=utf-8");
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
