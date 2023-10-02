package dev.opal.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<SignatureVerificationFilter> signatureFilter(SignatureVerificationFilter filter) {
		FilterRegistrationBean<SignatureVerificationFilter> registrationBean = new FilterRegistrationBean<>();

		// Register filter for these endpoints
		registrationBean.setFilter(filter);
		registrationBean.addUrlPatterns("/groups/*");
		registrationBean.addUrlPatterns("/resources/*");
		registrationBean.addUrlPatterns("/users/*");

		return registrationBean;
	}
}
