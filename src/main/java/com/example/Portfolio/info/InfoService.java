package com.example.Portfolio.info;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final MongoTemplate temp;

    public Info getInformation() {
        Info info = temp.findOne(new Query(), Info.class);
        if (info == null) throw new NoSuchElementException("no information");
        return info;
    }

    public Info updateInformation(UpdateInfoRequest request) {
        Info g = temp.findOne(new Query(), Info.class);
        if (g == null) throw new NoSuchElementException();
        if (request.getEmail() != null) g.setEmail(request.getEmail());
        if (request.getPhone() != null) g.setPhone(request.getPhone());
        if (request.getLinkedInLink() != null) g.setLinkedInLink(request.getLinkedInLink());
        if (request.getCvLink() != null) g.setCvLink(request.getCvLink());
        if (request.getAddress() != null) {
            if (request.getAddress().getAr() != null) {
                g.getAddress().setAr(request.getAddress().getAr());
            }
            if (request.getAddress().getEn() != null) {
                g.getAddress().setEn(request.getAddress().getEn());
            }
        }
        if (request.getFooter() != null) {
            if (request.getFooter().getAr() != null) {
                g.getFooter().setAr(request.getFooter().getAr());
            }
            if (request.getFooter().getEn() != null) {
                g.getFooter().setEn(request.getFooter().getEn());
            }
        }
        temp.save(g, "Info");
        return g;
    }
}
