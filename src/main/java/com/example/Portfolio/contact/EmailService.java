package com.example.Portfolio.contact;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String appFrom;

    public void sendComplaintConfirmation(String toEmail, String userName, String lang) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setFrom(appFrom);

            String subject;
            String body;

            if ("en".equalsIgnoreCase(lang)) {
                subject = "Your message has been received";
                body = String.format(
                        "Hello %s,\n\n" +
                                "I hope you are doing well.\n\n" +
                                "I would like to inform you that I have received your message and I truly appreciate you taking the time to reach out. " +
                                "I will personally review the details you shared and get back to you as soon as possible with a clear and helpful response. " +
                                "Your message is important to me, and I will make sure to follow up with the attention it deserves.\n\n" +
                                "If you have any additional details or documents that might help me better understand your inquiry, " +
                                "please feel free to reply directly to this email.\n\n" +
                                "Thank you once again for getting in touch.\n\n" +
                                "Best regards,\n" +
                                "Ashraf Al-Muhtaseb",
                        userName
                );
            } else {
                subject = "تم استلام رسالتك بنجاح";
                body = String.format(
                        "مرحبًا %s،\n\n" +
                                "أتمنى أن تكون بخير.\n\n" +
                                "أود أن أبلغك بأنني استلمت رسالتك وأقدر لك وقتك وجهدك في التواصل. " +
                                "سأقوم بمراجعة التفاصيل التي أرسلتها بعناية، وسأتواصل معك قريبًا بإجابة واضحة ومفيدة. " +
                                "رسالتك محل اهتمامي، وسأتابعها شخصيًا لضمان حصولك على الرد المناسب في أقرب وقت ممكن.\n\n" +
                                "إذا كانت لديك أي تفاصيل أو مرفقات إضافية قد تساعدني في فهم استفسارك بشكل أفضل، " +
                                "فلا تتردد في الرد على هذا البريد مباشرة.\n\n" +
                                "شكرًا لتواصلك،\n\n" +
                                "خالص تحياتي،\n" +
                                "أشرف المحتسب",
                        userName
                );
            }


            helper.setSubject(subject);
            helper.setText(body, false);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("فشل إرسال الإيميل: " + e.getMessage(), e);
        }
    }

    public void sendComplaintToReceiverEmail(String userEmail, String userName, String msg, String lang) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo("Ashraf@iData.center");
            helper.setFrom(appFrom);

            String subject;
            String body;

            if ("en".equalsIgnoreCase(lang)) {
                subject = "New message from your website — " + (userName == null ? "User" : userName);
                body = String.format(
                        "Sender: %s <%s>\n\nMessage:\n%s",
                        userName == null ? "No name" : userName,
                        userEmail == null ? "No email" : userEmail,
                        msg == null ? "" : msg
                );
            } else {
                subject = "رسالة جديدة من موقعك — " + (userName == null ? "مستخدم" : userName);
                body = String.format(
                        "المرسل: %s <%s>\n\nالرسالة:\n%s",
                        userName == null ? "لا يوجد اسم" : userName,
                        userEmail == null ? "لا يوجد بريد" : userEmail,
                        msg == null ? "" : msg
                );
            }

            helper.setSubject(subject);
            helper.setText(body, false);

            // reply-to لو المستخدم كتب إيميله
            if (userEmail != null && !userEmail.trim().isEmpty()) {
                helper.setReplyTo(userEmail);
            } else {
                helper.setReplyTo(appFrom);
            }

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("فشل إرسال الإيميل لمستقبل الشكاوى: " + e.getMessage(), e);
        }
    }
}