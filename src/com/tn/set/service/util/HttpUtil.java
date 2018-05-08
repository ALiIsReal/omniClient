package com.tn.set.service.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;

/**
 * http 工具类
 * 
 * @author fenhualei
 *
 */
public class HttpUtil {

	private static final String CHARSET_UTF_8 = "UTF-8";
	private static final String HTTP_POST = "POST";
	private static final String HTTP_GET = "GET";

	private static final String HTTP_PUT = "PUT";

	public static String get(String url, Map<String, String> queryParas,
			Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas),
					HTTP_GET, headers);
			conn.connect();
			return readResponseString(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String get(String url, Map<String, String> queryParas) {
		return get(url, queryParas, null);
	}

	public static String get(String url) {
		return get(url, null, null);
	}

	public static String jsonGet(String url, Map<String, String> params) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		return get(url, params, headers);
	}

	/**
	 * Send POST request
	 */
	public static String post(String url, Map<String, String> queryParas,
			String data, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas),
					HTTP_POST, headers);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes(CHARSET_UTF_8));
			out.flush();
			out.close();
			return readResponseString(conn);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String post(String url, Map<String, String> queryParas,
			String data) {
		return post(url, queryParas, data, null);
	}

	public static String post(String url, String data,
			Map<String, String> headers) {
		return post(url, null, data, headers);
	}

	public static String post(String url, String data) {
		return post(url, null, data, null);
	}

	public static String jsonPost(String url,String data){
        Map<String,String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        return post(url,null,data,headers);
    }

	public static String jsonPost(String url,Map<String,String>headers,String data){
        if(headers == null){
            headers = new HashMap<String, String>();
        }
        headers.put("Content-Type","application/json");
        return post(url,null,data,headers);
    }

	/**
	 * Send POST request
	 */
	public static String put(String url, Map<String, String> queryParas,
			String data, Map<String, String> headers) {
		HttpURLConnection conn = null;
		try {
			conn = getHttpConnection(buildUrlWithQueryString(url, queryParas),
					HTTP_PUT, headers);
			conn.connect();
			OutputStream out = conn.getOutputStream();
			out.write(data.getBytes(CHARSET_UTF_8));
			out.flush();
			out.close();
			return readResponseString(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	public static String jsonPut(String url,String data){
		Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        return put(url,null,data,headers);
    }

	private static SSLSocketFactory initSocketFactory() {
		try {
			TrustManager[] tm = { new TrustAnyTrustManager() };
			SSLContext sslContext = SSLContext.getInstance("TLS", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static final SSLSocketFactory ssLSocketFactory = initSocketFactory();
	private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new TrustAnyHostnameVerifier();

	private static HttpURLConnection getHttpConnection(String url,
			String method, Map<String, String> headers) throws Exception {
		URL _url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
		if (conn instanceof HttpsURLConnection) {
			((HttpsURLConnection) conn).setSSLSocketFactory(ssLSocketFactory);
			((HttpsURLConnection) conn)
					.setHostnameVerifier(trustAnyHostnameVerifier);
		}
		conn.setRequestMethod(method);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setConnectTimeout(3000000);
		conn.setReadTimeout(3000000);
		conn.setUseCaches(false);// Post 请求不允许使用缓存
		if (headers != null) {
			String contentType = headers.get("Content-Type");
			if (StringUtils.isNotEmpty(contentType)) {
				conn.setRequestProperty("Content-Type", contentType);
			} else {
				conn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=utf-8");
			}
		}
		conn.setRequestProperty(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
		if (headers != null && !headers.isEmpty())
			for (Map.Entry<String, String> entry : headers.entrySet())
				conn.setRequestProperty(entry.getKey(), entry.getValue());

		return conn;

	}

	/**
	 * https 域名校验
	 */
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * https 证书管理
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
	}

	private static String buildUrlWithQueryString(String url,
			Map<String, String> queryParas) {
		if (queryParas == null || queryParas.isEmpty())
			return url;
		StringBuilder sb = new StringBuilder(url);
		boolean isFrist;
		if (url.indexOf("?") == -1) {
			isFrist = true;
			sb.append("?");
		} else {
			isFrist = false;
		}
		for (Map.Entry<String, String> entry : queryParas.entrySet()) {
			if (isFrist) {
				isFrist = false;
			}
			String key = entry.getKey();
			String value = entry.getValue();
			if (StringUtils.isEmpty(value)) {
				try {
					value = URLEncoder.encode(value, CHARSET_UTF_8);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				sb.append(key).append("=").append(value);
			}
		}
		return sb.toString();
	}

	private static String readResponseString(HttpURLConnection conn) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, CHARSET_UTF_8));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
