package com.example.Portfolio.articles;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Portfolio.common_helpers.ApiResponse;
import com.example.Portfolio.common_helpers.Header;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final MongoTemplate temp;
    private final Cloudinary cloudinary;
    private final Random random;

    public String generateSlug(String baseName) {
        if (baseName == null || baseName.trim().isEmpty()) {
            baseName = "project";
        }
        // تحويل إلى kebab-case وإزالة الرموز غير المسموح بها
        String sanitized = baseName.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // إزالة الرموز غير المسموح بها
                .replaceAll("\\s+", "-"); // استبدال المسافات بـ -
        // إضافة رقم عشوائي من 100 إلى 999
        int randomNum = random.nextInt(900) + 100;
        return sanitized + "-" + randomNum;
    }

    public List<Article> getAllArticles() {
        List<Article> articles = temp.findAll(Article.class);
        if (articles.isEmpty()) {
            throw new NoSuchElementException("No Elements");
        }
        return articles;
    }

    public Article findArticleBySlug(String slug) {
        if (slug == null || slug.isEmpty()) throw new IllegalArgumentException("No Slug");
        Query q = new Query();
        q.addCriteria(Criteria.where("slug").is(slug));
        Article article = temp.findOne(q, Article.class);
        if (article == null) throw new NoSuchElementException("Article not found with slug: " + slug);
        return article;
    }

    public ApiResponse deleteBySlug(String slug) {
        if (slug == null || slug.isEmpty()) throw new IllegalArgumentException("No Slug");
        DeleteResult deleteResult = temp.remove(new Query().addCriteria(Criteria.where("slug").is(slug)), Article.class);
        if (deleteResult.getDeletedCount() == 0)
            throw new NoSuchElementException("No article found with slug: " + slug);
        return new ApiResponse(true, "Article deleted successfully.");
    }

    public Article addArticle(CreateArticleRequest request) {
        if (request == null) throw new IllegalArgumentException("no valid");
        Map<String, Object> options = ObjectUtils.asMap(
                "resource_type", "image",
                "timestamp", System.currentTimeMillis() / 1000
        );
        String headerImgUrl = "";
        String base64Data = request.getHeaderImageBase64().split(",")[1];
        try {
            byte[] fileData = Base64.getDecoder().decode(base64Data);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
            headerImgUrl = uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("HeaderImage upload problem");
        }
        Header header = Header.builder()
                .title(request.getTitle())
                .desc(request.getDesc())
                .imgUrl(headerImgUrl)
                .build();

        Map<String, Object> options2 = ObjectUtils.asMap(
                "resource_type", "image",
                "timestamp", System.currentTimeMillis() / 1000
        );
        String contentImgUrl = "";
        String base64Data2 = request.getContentImageBase64().split(",")[1];
        try {
            byte[] fileData = Base64.getDecoder().decode(base64Data2);
            Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options2);
            contentImgUrl = uploadResult.get("secure_url").toString();
        } catch (IOException e) {
            throw new RuntimeException("ContentImage upload problem");
        }

        return temp.save(
                Article.builder()
                        .slug(generateSlug(request.getTitle().getEn()))
                        .header(header)
                        .content(request.getContent())
                        .contentImgUrl(contentImgUrl)
                        .isPinned(request.isPinned())
                        .build(), "Articles"
        );
    }

    public Article updateArticleBySlug(String slug, UpdateArticleRequest request) {
        if (slug == null || slug.isEmpty()) throw new IllegalArgumentException("No Slug");
        Article existing = temp.findOne(new Query(Criteria.where("slug").is(slug)), Article.class);
        if (existing == null) throw new NoSuchElementException("Article not found");

        if (request.getHeaderTitle() != null) {
            if (request.getHeaderTitle().getAr() != null) {
                existing.getHeader().getTitle().setAr(request.getHeaderTitle().getAr());
            }
            if (request.getHeaderTitle().getEn() != null) {
                existing.getHeader().getTitle().setEn(request.getHeaderTitle().getEn());
            }
        }
        if (request.getHeaderDesc() != null) {
            if (request.getHeaderDesc().getAr() != null) {
                existing.getHeader().getDesc().setAr(request.getHeaderDesc().getAr());
            }
            if (request.getHeaderDesc().getEn() != null) {
                existing.getHeader().getDesc().setEn(request.getHeaderDesc().getEn());
            }
        }
        if (request.getHeaderImage() != null) {
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "image",
                    "timestamp", System.currentTimeMillis() / 1000
            );
            String headerImgUrl = "";
            String base64Data = request.getHeaderImage().split(",")[1];
            try {
                byte[] fileData = Base64.getDecoder().decode(base64Data);
                Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
                headerImgUrl = uploadResult.get("secure_url").toString();
            } catch (IOException e) {
                throw new RuntimeException("HeaderImage update problem");
            }
            existing.getHeader().setImgUrl(headerImgUrl);
        }
        if (request.getContent() != null) {
            if (request.getContent().getAr() != null) {
                existing.getContent().setAr(request.getContent().getAr());
            }
            if (request.getContent().getEn() != null) {
                existing.getContent().setEn(request.getContent().getEn());
            }
        }

        if (request.getContentImage() != null) {
            Map<String, Object> options = ObjectUtils.asMap(
                    "resource_type", "image",
                    "timestamp", System.currentTimeMillis() / 1000
            );
            String contentImgUrl = "";
            String base64Data = request.getContentImage().split(",")[1];
            try {
                byte[] fileData = Base64.getDecoder().decode(base64Data);
                Map<?, ?> uploadResult = cloudinary.uploader().upload(fileData, options);
                contentImgUrl = uploadResult.get("secure_url").toString();
            } catch (IOException e) {
                throw new RuntimeException("ContentImage update problem");
            }
            existing.setContentImgUrl(contentImgUrl);
        }
        if (request.getIsPinned() != null) {
            existing.setIsPinned(request.getIsPinned());
        }
        temp.save(existing);
        return existing;

    }

}
