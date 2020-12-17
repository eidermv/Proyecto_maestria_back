package co.edu.unicauca.gestordocumental.controller.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import co.edu.unicauca.gestordocumental.model.Tutor;
import co.edu.unicauca.gestordocumental.repo.TutorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService implements EmailPort{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private TutorRepo tutorRepo;

    private static final String contenidoP1 = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>" +
            "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office'>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<meta content='width=device-width, initial-scale=1' name='viewport'>" +
            "<meta name='x-apple-disable-message-reformatting'>" +
            "<meta http-equiv='X-UA-Compatible' content='IE=edge'>" +
            "<meta content='telephone=no' name='format-detection'>" +
            "<title>solicitud de seguimiento</title>" +
            // "<meta name='viewport' content='width=device-width, initial-scale=1.0'/>" +
            "</head>" +
            "<body>" +
            "<img src='https://i.ibb.co/6JDGDZw/logo-Gestor-Documental.jpg' alt='Logotipo de portada' width='100%' height='150'/>" +
            "<font color='black' face='Roboto,RobotoDraft,Helvetica,Arial,sans-serif'>" +
            "<h1 style='text-align:center;'>¡Tienes una nueva solicitud de seguimiento!</h1></font>" +
            "<hr/><br><br><font color='black' face='Roboto,RobotoDraft,Helvetica,Arial,sans-serif'>" +
            "<p style='text-align:center;'>¡Hola ";
    private static final String contenidoP2 = "!, queremos informarte que se ha enviado una nueva solicitud de seguimiento por parte del administrador, <br> puedes visualizarla en la sección de notificaciones. </p>" +
            "<p style='text-align:center;'>Si quieres revisar la solicitud, dirígete al siguiente enlace:</p>" +
            "<p style='text-align:center;'><a href='https://www.google.com.mx/'>Ir a notificaciones</a></p>" +
            "</font>" +
            "</body>" +
            "</html>";

    @Autowired
    private JavaMailSender sender;

    @Override
    public boolean sendEmail(EmailBody emailBody, String nombre)  {
        LOGGER.info("EmailBody: {}", emailBody.toString());
        // return sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject());
        // Tutor t = tutorRepo.findById(1).get();
        String contenido = contenidoP1 + nombre + contenidoP2;
        return sendEmailTool(contenido,emailBody.getEmail(), emailBody.getSubject());
    }


    private boolean sendEmailTool(String textMessage, String email,String subject) {
        boolean send = false;
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            System.out.println("Cargando mensaje -----");
            helper.setTo(email);
            helper.setText(textMessage, true);
            helper.setSubject(subject);
            sender.send(message);
            send = true;
            LOGGER.info("Mail enviado!");
        } catch (MessagingException e) {
            LOGGER.error("Hubo un error al enviar el mail: {}", e);
        }
        return send;
    }



}
