package com.gabosol777.demo.repository;

import com.gabosol777.demo.domain.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WordRepository extends MongoRepository<Word, String>  {
}
