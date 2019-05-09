// Generated by delombok at Thu Nov 22 18:54:11 CET 2018
package de.failender.heldensoftware.api;

import de.failender.heldensoftware.api.requests.*;
import org.apache.commons.io.IOUtils;
import reactor.core.publisher.Mono;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HeldenApi {

	private CacheHandler cacheHandler;

	public HeldenApi(File cacheDirectory) {
		cacheHandler = new CacheHandler(cacheDirectory);
	}

	public <T> Mono<T> request(ApiRequest<T> request) {
		return request(request, true);
	}

	public <T> Mono<T> request(ApiRequest<T> request, boolean useCache) {
		return requestRaw(request, useCache).map(is -> request.mapResponse(is));
	}


	public void provideDownload(IdCachedRequest<?> request, OutputStream stream) {
		requestRaw(request, true).subscribe(is -> {
			try {
				IOUtils.copy(is, stream);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
	}

	public InputStream provideDownload(IdCachedRequest<?> request) {
		return requestRaw(request, true).block();
	}

	public Mono<InputStream> requestRaw(ApiRequest request, boolean useCache) {
		if (useCache && cacheHandler.hasCacheFor(request)) {
			return Mono.just(cacheHandler.getCache(request));
		}
		return doRequest(request).map(is -> {
			if (cacheHandler.canCache(request)) {
				cacheHandler.doCache(request, is);
				return cacheHandler.getCache(request);
			} else {
				return is;
			}
		});
	}

	public synchronized Mono<InputStream> doRequest(ApiRequest request) {
		String body = buildBody(request);
		System.out.println(request.url());
		System.out.println(body);
		try {
			URL url = new URL(request.url());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(request.requestMethod());
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

			connection.setRequestProperty("Content-Length", String.valueOf(body.length()));
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
			writer.write(body);
			writer.close();
			return Mono.just(connection.getInputStream());
		} catch (Exception e) {
			logError(request, body);
			throw new RuntimeException(e);
		}
	}

	private void logError(ApiRequest request, String body) {
		System.out.println("############");
		System.out.println("Received failed request to url " +  request.url());
		System.out.println("Body is: ");
		System.out.println(body);
		System.out.println("############");
	}

	public String buildBody(ApiRequest request) {
		Map<String,String> data = request.writeRequest();
		return data.entrySet().stream().map(this::mapEntry).collect(Collectors.joining("&"));
	}

	private String mapEntry(Map.Entry<String, String> entry) {
		try {
			return entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}


	public enum Format {
		pdfintern, heldenxml, datenxml;
	}

	public CacheHandler getCacheHandler() {
		return cacheHandler;
	}

	public void setCacheHandler(CacheHandler cacheHandler) {
		this.cacheHandler = cacheHandler;
	}

	public static List<ApiRequest> getDataApiRequests(UUID cacheId) {
		return Arrays.asList(new ReturnHeldPdfRequest(null, null, cacheId), new ReturnHeldDatenWithEreignisseRequest(null, null, cacheId), new ReturnHeldXmlRequest(null, null, cacheId));
	}


}
