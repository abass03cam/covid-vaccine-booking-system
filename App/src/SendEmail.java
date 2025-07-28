
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

   public static void main(String[] args) {

      String to = "nothingdone73@gmail.com"; // adresse e-mail du destinataire
      String from = "kontoumzug@gmail.com"; // adresse e-mail de l'expediteur
      String passwordmail = "kgvrghwqrtglfnxm"; // mot de passe de l'expediteur

      // Configuration des proprietes pour se connecter au serveur SMTP de Gmail
      Properties properties = new Properties();
      properties.put("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.port", "587");
      properties.put("mail.smtp.starttls.enable", "true");

      // Creation d'une session pour l'envoi de l'e-mail
      Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, passwordmail);
         }
      });

      try {
         // Creation d'un objet MimeMessage
         MimeMessage message = new MimeMessage(session);

         // Definition des details de l'e-mail
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject("Test JavaMail");
         message.setText("Ceci est un test d'envoi de message a partir de JavaMail");

         // Envoi de l'e-mail
         Transport.send(message);
         System.out.println("Le message a ete envoye avec succes.");

      } catch (MessagingException e) {
         e.printStackTrace();
      }
   }
}