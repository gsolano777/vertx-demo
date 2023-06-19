package com.gabosol777.demo;

import com.gabosol777.demo.verticle.ServerVerticle;
import com.gabosol777.demo.verticle.WordAnalyzerVerticle;
import io.vertx.core.Vertx;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VertxApplication {

	@Autowired
	private ServerVerticle serverVerticle;
	@Autowired
	private WordAnalyzerVerticle wordAnalyzerVerticle;

	public static void main(String[] args) {
		SpringApplication.run(VertxApplication.class, args);
	}

	@PostConstruct
	public void deployVerticles() {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(serverVerticle);
		vertx.deployVerticle(wordAnalyzerVerticle);
	}

}
