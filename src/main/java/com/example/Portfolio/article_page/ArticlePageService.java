package com.example.Portfolio.article_page;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.Portfolio.articles.Article;
import com.example.Portfolio.common_helpers.PageUpdateRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ArticlePageService {
    private final MongoTemplate temp;
    private final Cloudinary cloudinary;

    public ArticlePageDto getArticlePage(){
        List<Article> articleList=temp.findAll(Article.class);
        List<ArticleDto> articleDtos=articleList.stream().map(article -> ArticleDto.builder()
                .slug(article.getSlug())
                .header(article.getHeader())
                .isPinned(article.getIsPinned())
                .build()).toList();
        ArticlePage page=temp.findOne(new Query() , ArticlePage.class);
        if (page == null) throw new NoSuchElementException("No Articles Page");
        return ArticlePageDto.builder()
                .header(page.getHeader())
                .articleDtoList(articleDtos)
                .build();
    }

    public ArticlePage updateArticlesPage(PageUpdateRequest request){
        ArticlePage page = temp.findOne(new Query(), ArticlePage.class);
        if (page == null) throw new NoSuchElementException("No Page");
        if (request.getTitle() != null) {
            if (request.getTitle().getEn() != null)
                page.getHeader().getTitle().setEn(request.getTitle().getEn());

            if (request.getTitle().getAr() != null)
                page.getHeader().getTitle().setAr(request.getTitle().getAr());
        }
        if (request.getDesc() != null) {
            if (request.getDesc().getEn() != null)
                page.getHeader().getDesc().setEn(request.getDesc().getEn());

            if (request.getDesc().getAr() != null)
                page.getHeader().getDesc().setAr(request.getDesc().getAr());
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
                page.getHeader().setImgUrl(url);
            } catch (IOException e) {
                throw new RuntimeException("Image upload problem");
            }
        }
        return temp.save(page, "ArticlePage");
    }

}
