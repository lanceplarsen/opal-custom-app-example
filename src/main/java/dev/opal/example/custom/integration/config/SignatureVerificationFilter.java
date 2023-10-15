package dev.opal.example.custom.integration.config;

import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class SignatureVerificationFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignatureVerificationFilter.class);
	private static final String HEADER_NAME = "X-Opal-Request-Timestamp";
	private static final String HEADER_SIGNATURE = "X-Opal-Signature";

	@Value("${opal.signature.secret}")
	private String SIGNING_SECRET;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws java.io.IOException, ServletException {

		ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
		chain.doFilter(wrappedRequest, response);

		byte[] contentData = wrappedRequest.getContentAsByteArray();
		String requestBody = new String(contentData, StandardCharsets.UTF_8).trim();
		if (requestBody.isEmpty()) {
			requestBody = "{}";
		}

		String method = wrappedRequest.getMethod();
		String path = wrappedRequest.getRequestURI();

		// Filter these out for the web UI
		if (("POST".equals(method) || "GET".equals(method)) && (path.equals("/groups") || path.equals("/resources")
				|| path.equals("/users") || path.endsWith("/access_levels"))) {
			return;
		}

		String timestamp = wrappedRequest.getHeader(HEADER_NAME);
		String signatureFromHeader = wrappedRequest.getHeader(HEADER_SIGNATURE);

		if (timestamp != null && signatureFromHeader != null) {
			String sigBaseString = "v0:" + timestamp + ":" + requestBody;
			String computedSignature = computeHMAC(sigBaseString, SIGNING_SECRET);

			if (!signatureFromHeader.equals(computedSignature)) {
				LOGGER.warn("Signature validation failed for request from IP: {}", wrappedRequest.getRemoteAddr());
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Signature validation failed");
			}
		} else {
			LOGGER.warn("Missing required headers for request from IP: {}", wrappedRequest.getRemoteAddr());
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing required headers");
		}
	}

	private String computeHMAC(String data, String secret) {
		try {
			SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(secretKey);
			byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return bytesToHex(hmacBytes);
		} catch (Exception ex) {
			LOGGER.error("Error computing HMAC", ex);
			throw new RuntimeException("Error computing HMAC", ex);
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytes) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Intentionally left empty
	}

	@Override
	public void destroy() {
		// Intentionally left empty
	}
}