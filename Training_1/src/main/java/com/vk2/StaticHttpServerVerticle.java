package com.vk2;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

import io.vertx.ext.web.handler.StaticHandler;

public class StaticHttpServerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture)
	{
		System.out.println("StaticHttpServerVerticle started");
		startFuture.complete();
	}
	
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println("StaticHttpServerVerticle stopped");
		stopFuture.complete();
	}
	
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle("com.vk2.StaticHttpServerVerticle", new Handler<AsyncResult<String>>() {
			
			@Override
			public void handle(AsyncResult<String> event) {
				HttpServer server = vertx.createHttpServer();
				Router restAPI = Router.router(vertx);
				restAPI.route().handler(StaticHandler.create()::handle);
				
				server.requestHandler(restAPI::accept).listen(8090);
				
				System.out.println("StaticHttpServerVerticle deployment complete");
			}
		});
	}
}
