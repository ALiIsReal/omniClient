package com.tn.set.service.util.client.base;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;



public interface OmniService {
	<T extends Response> T send(
            Request request, Class<T> responseType) throws IOException;

    <T extends Response> CompletableFuture<T> sendAsync(
Request request, Class<T> responseType);
}
