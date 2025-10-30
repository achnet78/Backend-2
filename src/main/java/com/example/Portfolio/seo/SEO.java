package com.example.Portfolio.seo;

import com.example.Portfolio.common_helpers.Text;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "SEO")
public class SEO {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    @Field("_id")
    private ObjectId _id;
    private String title;
    private String desc;
    private String keywords;
    private String imgUrl;
}
