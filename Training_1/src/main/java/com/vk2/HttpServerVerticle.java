package com.vk2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class HttpServerVerticle extends AbstractVerticle {
		
	@Override
	public void start(Future<Void> startFuture)
	{
		System.out.println("HttpServerVerticle started");
		startFuture.complete();
	}
	
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println("HttpServerVerticle stopped");
		stopFuture.complete();
	}
	
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle("com.vk2.HttpServerVerticle", new Handler<AsyncResult<String>>() {
			
			@Override
			public void handle(AsyncResult<String> event) {
				HttpServer server = vertx.createHttpServer();
				Router router = Router.router(vertx);
				router.get("/services/users/:id").handler(new UserLoader());
				server.requestHandler(router::accept).listen(8090);
				
				System.out.println("HttpServerVerticle deployment complete");
			}
		});
	}
}

class UserLoader implements Handler<RoutingContext> {
	
	public void handle(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		HttpServerResponse response = routingContext.response();
		response.putHeader("content-type", "application/json");
		response.end("This is a response");
	}
}
