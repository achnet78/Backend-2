package com.example.Portfolio.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContactService {
    private final MongoTemplate temp;
    private final EmailService service;

    public Contact addContact(ContactRequest request){
        Contact c = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();

        temp.save(c , "Contact");

        // إرسال حسب اللغة
        String lang = request.getLang() != null ? request.getLang().toLowerCase() : "ar";

        // تأكيد للمرسل
        service.sendComplaintConfirmation(request.getEmail(), request.getName(), lang);

        // رسالة لصاحب الموقع
        service.sendComplaintToReceiverEmail(request.getEmail(), request.getName(), request.getMessage(), lang);

        return c;
    }
}
