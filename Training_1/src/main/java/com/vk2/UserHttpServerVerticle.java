package com.vk2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.handler.codec.http.HttpResponseStatus;
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
import io.vertx.ext.web.handler.BodyHandler;

public class UserHttpServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture)
	{
		System.out.println("UserHttpServerVerticle started");
		startFuture.complete();
	}
	
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println("UserHttpServerVerticle stopped");
		stopFuture.complete();
	}
	
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle("com.vk2.UserHttpServerVerticle", new Handler<AsyncResult<String>>() {
			
			@Override
			public void handle(AsyncResult<String> event) {
				HttpServer server = vertx.createHttpServer();
				Router restAPI = Router.router(vertx);
				restAPI.route().handler(BodyHandler.create());
				
				restAPI.post("/services/user").handler(rc -> {
					String jsonStr = rc.getBodyAsString();
					ObjectMapper mapper = new ObjectMapper();
					mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					try {
						User u = mapper.readValue(jsonStr, User.class);
						System.out.println("Got user with name " + u.name + " and age " + u.age);
						HttpServerResponse response = rc.response();
						response.putHeader("content-type", "application/json");
						response.setStatusCode(HttpResponseStatus.OK.code());
						response.end("User created");
					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
						HttpServerResponse response = rc.response();
						response.putHeader("content-type", "application/json");
						response.setStatusCode(HttpResponseStatus.NOT_ACCEPTABLE.code());
						response.end("User failed to create");
					}
				});
				
				server.requestHandler(restAPI::accept).listen(8090);
				
				System.out.println("UserHttpServerVerticle deployment complete");
			}
		});
	}
}
