package com.tutego.date4u.service;

import org.springframework.scheduling.annotation.Async;

import java.util.concurrent.Future;

public interface Thumbnail {

	@Async
	Future<byte[]> thumbnail( byte[] imageBytes );
}