package in.ashokit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String to, String sub, String body) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		try {
			helper.setTo(to);
			helper.setSubject(sub);
			helper.setText(body, true);
			mailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
