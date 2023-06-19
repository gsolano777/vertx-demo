package com.gabosol777.demo.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.springframework.stereotype.Service;

@Service
public class ServerVerticle extends AbstractVerticle {

    @Override
    public void start() throws Exception {
        super.start();

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.post("/analyze")
                .handler(this::analyzeWords);

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8080));
    }

    private void analyzeWords(RoutingContext routingContext) {
        vertx.eventBus().<String> send(Messages.ANALYZE_WORD.toString(), routingContext.getBodyAsJson().getString("text"),
            result -> {
                    if (result.succeeded()) {
                    routingContext.response()
                        .putHeader("content-type", "application/json")
                        .setStatusCode(200)
                        .end(result.result().body());
                } else {
                    routingContext.response()
                        .setStatusCode(500)
                        .end();
                }
            });
    }
}
