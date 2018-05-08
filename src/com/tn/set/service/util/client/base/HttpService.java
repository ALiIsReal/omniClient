package com.tn.set.service.util.client.base;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tn.set.service.util.PropUtil;

public class HttpService extends Service {

	public static final MediaType JSON_MEDIA_TYPE = MediaType
			.parse("application/json; charset=utf-8");

	public static final String DEFAULT_URL = "http://localhost:18832/";

	private static final Logger log = LoggerFactory
			.getLogger(HttpService.class);

	private OkHttpClient httpClient ;

	private final String url;
	private String username = PropUtil.getProps().getProperty("USERNAME");
	private String password = PropUtil.getProps().getProperty("PASSWORD");

	private final boolean includeRawResponse;

	private HashMap<String, String> headers = new HashMap<>();

	public HttpService(String url, OkHttpClient httpClient,
			boolean includeRawResponses) {
		super(includeRawResponses);
		this.url = url;
		this.httpClient = httpClient;
		this.includeRawResponse = includeRawResponses;
	}

	public HttpService(OkHttpClient httpClient, boolean includeRawResponses) {
		this(DEFAULT_URL, httpClient, includeRawResponses);
	}

	private HttpService(String url, OkHttpClient httpClient) {
		this(url, httpClient, false);
	}

	public HttpService(String url) {
		this(url, createOkHttpClient());
	}

	public HttpService(String url, boolean includeRawResponse) {
		this(url, createOkHttpClient(), includeRawResponse);
	}

	public HttpService(OkHttpClient httpClient) {
		this(DEFAULT_URL, httpClient);
	}

	public HttpService(boolean includeRawResponse) {
		this(DEFAULT_URL, includeRawResponse);
	}

	public HttpService() {
		this(DEFAULT_URL);
	}

	private static OkHttpClient createOkHttpClient() {
		OkHttpClient.Builder builder = getUnsafeOkHttpClient().newBuilder();
		builder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30,
				TimeUnit.SECONDS);
		configureLogging(builder);
		return builder.build();
	}

	private static void configureLogging(OkHttpClient.Builder builder) {
		if (log.isDebugEnabled()) {
			HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
					log::debug);
			logging.setLevel(HttpLoggingInterceptor.Level.BODY);
			builder.addInterceptor(logging);
		}
	}

	@Override
	protected InputStream performIO(String request) throws IOException {

		RequestBody requestBody = RequestBody.create(JSON_MEDIA_TYPE, request);

		// TODO user password
		String creb = Base64.encodeBase64String((username + ":" + password)
				.getBytes());
		this.headers.put("Authorization", "Basic " + creb);

		Headers headers = buildHeaders();
		okhttp3.Request httpRequest = new okhttp3.Request.Builder().url(url)
				.headers(headers).post(requestBody).build();

		okhttp3.Response response = httpClient.newCall(httpRequest).execute();
		if (response.isSuccessful()) {
			ResponseBody responseBody = response.body();
			if (responseBody != null) {
				return buildInputStream(responseBody);
			} else {
				return null;
			}
		} else {
			System.out.println(response.message());
			throw new ClientConnectionException("Invalid response received: "
					+ response.body());
		}
	}

	private InputStream buildInputStream(ResponseBody responseBody)
			throws IOException {
		InputStream inputStream = responseBody.byteStream();

		if (includeRawResponse) {
			// we have to buffer the entire input payload, so that after
			// processing
			// it can be re-read and used to populate the rawResponse field.

			BufferedSource source = responseBody.source();
			source.request(Long.MAX_VALUE); // Buffer the entire body
			Buffer buffer = source.buffer();

			long size = buffer.size();
			if (size > Integer.MAX_VALUE) {
				throw new UnsupportedOperationException(
						"Non-integer input buffer size specified: " + size);
			}

			int bufferSize = (int) size;
			BufferedInputStream bufferedinputStream = new BufferedInputStream(
					inputStream, bufferSize);

			bufferedinputStream.mark(inputStream.available());
			return bufferedinputStream;

		} else {
			return inputStream;
		}
	}

	/**
	 * 跳过证书验证
	 */
	public static OkHttpClient getUnsafeOkHttpClient() {

		try {
			final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType) {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType) {
				}

				@Override
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[] {};
				}
			} };

			final SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustAllCerts,
					new java.security.SecureRandom());
			final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext
					.getSocketFactory();
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.sslSocketFactory(sslSocketFactory);

			builder.hostnameVerifier(new HostnameVerifier() {
				@Override
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});

			return builder.build();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private Headers buildHeaders() {
		return Headers.of(headers);
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void addHeaders(Map<String, String> headersToAdd) {
		headers.putAll(headersToAdd);
	}

	public HashMap<String, String> getHeaders() {
		return headers;
	}
}
