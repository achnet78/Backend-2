package com.example.Portfolio.info;

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
@Document(collection = "Info")
public class Info {
    @JsonSerialize(using = ToStringSerializer.class)
    @Id
    @Field("_id")
    private ObjectId _id;
    private String phone;
    private String email;
    private String linkedInLink;
    private String cvLink;
    private Text address;
    private Text footer;
}
