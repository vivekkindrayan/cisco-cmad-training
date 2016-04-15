package com.vk2;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class ConsumerVerticle extends AbstractVerticle {

	@Override
	public void start(Future<Void> startFuture)
	{
		vertx.eventBus().consumer("Channel1", message -> {
			System.out.println("Message Body = " + message.body());
		});
		System.out.println("ConsumerVerticle started");
		startFuture.complete();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void stop(Future stopFuture) throws Exception
	{
		System.out.println("ConsumerVerticle stopped");
		stopFuture.complete();
	}
	
	public static void main(String[] args) {
		VertxOptions options = new VertxOptions().setWorkerPoolSize(10);
		Vertx vertx = Vertx.vertx(options);
		vertx.deployVerticle("com.vk2.ConsumerVerticle", new Handler<AsyncResult<String>>() {
			
			@Override
			public void handle(AsyncResult<String> event) {
				System.out.println("ConsumerVerticle deployment complete");
				vertx.deployVerticle("com.vk2.PublisherVerticle");
			}
		});
	}

}
