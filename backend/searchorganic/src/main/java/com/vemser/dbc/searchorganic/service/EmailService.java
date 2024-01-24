package com.vemser.dbc.searchorganic.service;

import com.vemser.dbc.searchorganic.dto.usuario.UsuarioDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@EnableAsync
public class EmailService {

    private final freemarker.template.Configuration fmConfiguration;

    @Value("${spring.mail.username}")
    private String from;
    private String to;

    private final JavaMailSender emailSender;

    public void sendSimpleMessage() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("Assunto TESTE");
        message.setText("Meu e-mail!");
        emailSender.send(message);
    }

    public void sendWithAttachment() throws Exception {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(message,
                    true);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }

        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject("Assunto 1");
        mimeMessageHelper.setText("Meu e-mail!");

//        File file = new File("static/imagem.jpg");
//        FileSystemResource fileSr
//                = new FileSystemResource(file);
//        mimeMessageHelper.addAttachment(file.getName(), fileSr);

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("static/imagem.jpg").getFile());
        FileSystemResource fileRs = new FileSystemResource(file);
        mimeMessageHelper.addAttachment(file.getName(), fileRs);

        System.out.println("File: " + file.getPath());

        emailSender.send(message);
    }

    @Async
    public void sendEmail( Map<String, Object> dados, String assunto) throws Exception {
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(assunto);
            mimeMessageHelper.setText(geContentFromTemplate(dados), true);
            emailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public String geContentFromTemplate(  Map<String, Object> dados) throws IOException, TemplateException {
        Template template = fmConfiguration.getTemplate("email-template.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, dados);
        return html;
    }

    @Async
    public void sendEmail(String mensagem, String assunto, String destinatario) throws Exception {
        this.to = destinatario;
        Map<String, Object> dados = new HashMap<>();
        dados.put("mensagem", mensagem);
        dados.put("email", from);
        sendEmail(dados, assunto);
    }

}
