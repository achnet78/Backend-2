package com.example.Portfolio.home_page;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Portfolio.article_page.ArticleDto;
import com.example.Portfolio.article_page.ArticlePage;
import com.example.Portfolio.articles.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class HomePageService {
    private final MongoTemplate temp;
    private final Cloudinary cloudinary;

    public HomePageDto getHomePage() {
        Query query = new Query();
        query.addCriteria(Criteria.where("isPinned").is(true));
        List<Article> articleList = temp.find(query, Article.class);
        List<ArticleDto> articleDtos = articleList.stream()
                .map(article -> ArticleDto.builder()
                        .slug(article.getSlug())
                        .header(article.getHeader())
                        .isPinned(article.getIsPinned())
                        .build())
                .toList();
        HomePage page = temp.findOne(new Query(), HomePage.class);
        if (page == null) throw new NoSuchElementException("No Home Page");
        return HomePageDto.builder()
                .header(page.getHeader())
                .aboutMe(page.getAboutMe())
                .articleDtoList(articleDtos)
                .build();
    }

    public HomePage updateHomePage(UpdateHomePageRequest request) {
        HomePage page = temp.findOne(new Query(), HomePage.class);
        if (page == null) throw new NoSuchElementException("No Page");
        if (request.getHeaderTitle() != null) {
            if (request.getHeaderTitle().getEn() != null)
                page.getHeader().getTitle().setEn(request.getHeaderTitle().getEn());
            if (request.getHeaderTitle().getAr() != null)
                page.getHeader().getTitle().setAr(request.getHeaderTitle().getAr());
        }
        if (request.getHeaderDesc() != null) {
            if (request.getHeaderDesc().getEn() != null)
                page.getHeader().getDesc().setEn(request.getHeaderDesc().getEn());
            if (request.getHeaderDesc().getAr() != null)
                page.getHeader().getDesc().setAr(request.getHeaderDesc().getAr());
        }
        if (request.getHeaderImageBase64() != null && !request.getHeaderImageBase64().isEmpty()) {
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "image",
                    "timestamp", System.currentTimeMillis() / 1000
            );
            try {
                String base64Data = request.getHeaderImageBase64().split(",")[1];
                byte[] fileData = Base64.getDecoder().decode(base64Data);
                Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
                String url = uploadResult.get("secure_url").toString();
                page.getHeader().setImgUrl(url);
            } catch (IOException e) {
                throw new RuntimeException("Header image upload problem");
            }
        }
        if (request.getAboutMeTitle() != null) {
            if (request.getAboutMeTitle().getEn() != null)
                page.getAboutMe().getTitle().setEn(request.getAboutMeTitle().getEn());
            if (request.getAboutMeTitle().getAr() != null)
                page.getAboutMe().getTitle().setAr(request.getAboutMeTitle().getAr());

        }
        if (request.getAboutMeDesc() != null) {
            if (request.getAboutMeDesc().getEn() != null)
                page.getAboutMe().getDesc().setEn(request.getAboutMeDesc().getEn());
            if (request.getAboutMeDesc().getAr() != null)
                page.getAboutMe().getDesc().setAr(request.getAboutMeDesc().getAr());
        }
        if (request.getAboutMeKeyWords() != null) {
            if (request.getAboutMeKeyWords().getEn() != null)
                page.getAboutMe().getKeywords().setEn(request.getAboutMeKeyWords().getEn());
            if (request.getAboutMeKeyWords().getAr() != null)
                page.getAboutMe().getKeywords().setAr(request.getAboutMeKeyWords().getAr());
        }
        if (request.getAboutMeImageBase64() != null && !request.getAboutMeImageBase64().isEmpty()) {
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "image",
                    "timestamp", System.currentTimeMillis() / 1000
            );
            try {
                String base64Data = request.getAboutMeImageBase64().split(",")[1];
                byte[] fileData = Base64.getDecoder().decode(base64Data);
                Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
                String url = uploadResult.get("secure_url").toString();
                page.getAboutMe().setImgUrl(url);
            } catch (IOException e) {
                throw new RuntimeException("About Me image upload problem");
            }
        }
        return temp.save(page, "HomePage");
    }
}
