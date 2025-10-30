package com.example.Portfolio.articles;

import com.example.Portfolio.common_helpers.Header;
import com.example.Portfolio.common_helpers.Text;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Articles")
public class Article {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    @Field("_id")
    private ObjectId _id;
    private String slug;
    private Header header;
    private Text content;
    private String contentImgUrl;
    private Boolean isPinned;
}
