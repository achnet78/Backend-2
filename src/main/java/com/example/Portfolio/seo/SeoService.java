package com.example.Portfolio.seo;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Portfolio.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SeoService {
    private final MongoTemplate temp;
    private final Cloudinary cloudinary;

    public SEO getSeoInfo(){
        SEO s=temp.findOne(new Query(), SEO.class);
        if(s==null) throw new NoSuchElementException("no Seo Information");
        return s;
    }

    public SEO updateSeoInfo(SeoUpdateRequest request){
        SEO s=temp.findOne(new Query() , SEO.class);
        if(s==null) throw new NoSuchElementException();
        if (request.getTitle() != null) {
             s.setTitle(request.getTitle());
        }
        if (request.getDesc() != null) {
          s.setDesc(request.getDesc());
        }
        if(request.getKeywords()!=null){
           s.setKeywords(request.getKeywords());
        }
        if (request.getImageBase64() != null && !request.getImageBase64().isEmpty()) {
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "image",
                    "timestamp", System.currentTimeMillis() / 1000
            );
            String url = "";
            String base64Data = request.getImageBase64().split(",")[1];
            try {
                byte[] fileData = Base64.getDecoder().decode(base64Data);
                Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
                url = uploadResult.get("secure_url").toString();
               s.setImgUrl(url);
            } catch (IOException e) {
                throw new RuntimeException("Image upload problem");
            }
        }
       return temp.save(s , "SEO");
    }


}
