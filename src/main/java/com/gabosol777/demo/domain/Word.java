package com.gabosol777.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "words")
@AllArgsConstructor
@Data
public class Word  {
    private String word;
}
