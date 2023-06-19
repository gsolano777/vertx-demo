package com.gabosol777.demo.verticle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabosol777.demo.domain.Word;
import com.gabosol777.demo.repository.WordRepository;
import com.gabosol777.demo.service.WordAnalyzer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WordAnalyzerVerticle extends AbstractVerticle {

    @Autowired
    private WordAnalyzer wordAnalyzer;

    @Autowired
    private WordRepository wordRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void start() throws Exception {
        super.start();
        vertx.eventBus().<String>consumer(Messages.ANALYZE_WORD.toString())
                .handler(analyzeWord(wordAnalyzer, wordRepository));
    }

    private Handler<Message<String>> analyzeWord(WordAnalyzer wordAnalyzer, WordRepository wordRepository) {
        return msg -> vertx.<String> executeBlocking(future -> {
            try {
                future.complete(mapper.writeValueAsString(wordAnalyzer.analyze(msg.body())));
            } catch (JsonProcessingException e) {
                future.fail(e);
            }
            wordRepository.save(new Word(msg.body()));
        }, result -> {
            if (result.succeeded()) {
                msg.reply(result.result());
            } else {
                msg.reply(result.cause().toString());
            }
        });
    }

    @PostConstruct
    private void preloadWords() {
        wordRepository.findAll()
            .stream()
            .map(word -> word.getWord())
            .forEach(word -> wordAnalyzer.loadWord(word));
    }
}
