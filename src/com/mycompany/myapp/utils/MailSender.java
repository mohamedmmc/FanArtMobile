/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.utils;



import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author splin
 */
public class MailSender {
        final String username = "fanart3a18@gmail.com";
        final String password = "3A18java123";

        public void send (String email, String name,String msg) throws InterruptedException, AddressException, MessagingException{
            

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
 
        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            
            
            message.setSubject("un nouveau événement vous attend ");
            message.setContent(
              msg,
             "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
        
        public void send (String email, String name) throws InterruptedException{
            

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            message.setSubject("Bienvenue sur l'application FanArt");
            message.setContent(
              "<h1>Bievenue Mr "+name+"</h1>",
             "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
        
        
    public void sendHack (String email, String img) throws InterruptedException{
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            BodyPart messageBodyPart = new MimeBodyPart();
             messageBodyPart.setText("Ci-joint une capture avec la caméra");
               Multipart multipart = new MimeMultipart();
                 multipart.addBodyPart(messageBodyPart);
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            message.setSubject("Connexion douteuse");
                             messageBodyPart = new MimeBodyPart();
         
         DataSource source = new FileDataSource(img);
         messageBodyPart.setDataHandler(new DataHandler(source));
         messageBodyPart.setFileName(img);
         multipart.addBodyPart(messageBodyPart);
          message.setContent(multipart);

          Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void Recm (String email) throws InterruptedException{
            

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            message.setSubject("Reponse reclamation");
            message.setContent(
              "<h1>Cher client</h1> "
                      + "<p> merci pour votre reclamation, elle sera traiteee le plutot possible "
                      + " <h3>ceci est un mail automatique, merci de ne pas repondre </h3>",
                    
             "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
         
                  public void Recadm (String email, String sub, String cont) throws InterruptedException{
            

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            message.setSubject(sub);
            message.setContent("<h3>"+cont+"</h3>" ,
                    
             "text/html");

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
                  public void sendpdf (String email, String name,int panier) throws InterruptedException{
            

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            message.setSubject("facture");
            /*message.setContent(
              "<h1>Bievenue Mr "+name+"</h1>",
             "text/html");*/
            
      // Attacher le fichier à l'email
      MimeBodyPart mbp1 = new MimeBodyPart();
      mbp1.setContent("<h1>Bonjour et merci de votre achat, Votre facture ci joint Mr "+name+"</h1>","text/html");
      MimeBodyPart mbp2= new MimeBodyPart();
      mbp2.setContent("<h1>Facture</h1>","text/html");
      FileDataSource fichier_joint = new FileDataSource("D:/facture"+panier+".pdf");
      MimeBodyPart mbp3 = new MimeBodyPart();
      mbp3.setDataHandler(new DataHandler(fichier_joint));
      mbp3.setFileName(fichier_joint.getName());
      Multipart mp = new MimeMultipart();
      mp.addBodyPart(mbp1);
      mp.addBodyPart(mbp2);
      mp.addBodyPart(mbp3);
      message.setContent(mp);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
                          
    public void sendQRcode (String email) throws InterruptedException{  
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse("balachandralucky2@gmail.com, "+email)
            );
            message.setSubject("facture");
            /*message.setContent(
              "<h1>Bievenue Mr "+name+"</h1>",
             "text/html");*/
            
      // Attacher le fichier à l'email
      MimeBodyPart mbp1 = new MimeBodyPart();
      mbp1.setContent("<h1>Bonjour et merci de votre achat, Votre facture ci joint Mr le Client</h1>","text/html");
      MimeBodyPart mbp2= new MimeBodyPart();
      mbp2.setContent("<h1>Facture</h1>","text/html");
      FileDataSource fichier_joint = new FileDataSource("C:\\xampp\\htdocs\\img\\qrcode.png");
      MimeBodyPart mbp3 = new MimeBodyPart();
      mbp3.setDataHandler(new DataHandler(fichier_joint));
      mbp3.setFileName(fichier_joint.getName());
      Multipart mp = new MimeMultipart();
      mp.addBodyPart(mbp1);
      mp.addBodyPart(mbp2);
      mp.addBodyPart(mbp3);
      message.setContent(mp);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
