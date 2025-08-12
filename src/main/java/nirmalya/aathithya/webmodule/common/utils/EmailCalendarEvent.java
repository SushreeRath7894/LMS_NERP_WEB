package nirmalya.aathithya.webmodule.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class EmailCalendarEvent {

	public EmailCalendarEvent() {
	}

	public void send(String subject, String mailFrom, String password, List<String> toAddress,
			String host, String port, String location, String summary, String description, String startDateTime, String endDateTime) throws Exception {

		try {
			Properties prop = new Properties();
			prop.put("mail.smtp.host", host);
			prop.put("mail.smtp.port", port);
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.user", mailFrom);
			prop.put("mail.password", password);
			
		    Date date = new Date();  
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date stDate = format.parse(startDateTime);
			Date endDate = format.parse(endDateTime);
			
			SimpleDateFormat sdf;
			sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC+5:30"));
			String dateStart = sdf.format(stDate);
			String dateEnd = sdf.format(endDate);
			String currentDate = sdf.format(date);
			
			Authenticator auth = new Authenticator() {
				public PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailFrom, password);
				}
			};
			
			Session session = Session.getInstance(prop, auth);
			
			MimeMessage message = new MimeMessage(session);
			message.addHeaderLine("method=REQUEST");
			message.addHeaderLine("charset=UTF-8");
			message.addHeaderLine("component=VEVENT");
			
			InternetAddress[] toAddresses = new InternetAddress[toAddress.size()];
			int counter = 0;
			for (String toAddress1 : toAddress) {
				toAddresses[counter] = new InternetAddress(toAddress1.trim());
				counter++;
			}

			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, toAddresses);
			message.setSubject(subject);

			StringBuffer sb = new StringBuffer();
			
			/*
			 * String mailTo = ""; for(String m : toAddress) { mailTo = mailTo + m + ","; }
			 * mailTo = mailTo.substring(0, mailTo.length() - 1);
			 */
			StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n"
					+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" 
					+ "VERSION:2.0\n"
					+ "METHOD:REQUEST\n" 
					+ "BEGIN:VEVENT\n"
					+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+toAddress+"\n" 
					+ "ORGANIZER:MAILTO:"+mailFrom+"\n"
					+ "DTSTART:"+dateStart+"\n" 
					+ "DTEND:"+dateEnd+"\n" 
					+ "LOCATION:"+location+"\n"
					+ "TRANSP:OPAQUE\n" + "SEQUENCE:0\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
					+ " 000004377FE5C37984842BF9440448399EB02\n" 
					+ "DTSTAMP:"+currentDate+"\n" 
					+ "CATEGORIES:Meeting\n"
					+ "DESCRIPTION:"+description+"\n\n" 
					+ "SUMMARY:"+summary+"\n"
					+ "PRIORITY:5\n" 
					+ "CLASS:PUBLIC\n" 
					+ "BEGIN:VALARM\n" 
					+ "TRIGGER:PT1440M\n" 
					+ "ACTION:DISPLAY\n"
					+ "DESCRIPTION:Reminder\n" 
					+ "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart
					.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very
																													// important

			// Create a Multipart
			Multipart multipart = new MimeMultipart();

			// Add part one
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			// send message
			Transport.send(message);
		} catch (MessagingException me) {
			me.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
