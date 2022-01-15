/**
 * 
 */
package tech.javacloud;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author javacloud.tech
 *
 */
@Component
public class RequestMatcherFilter extends GenericFilterBean {

	private static Logger logger = LoggerFactory.getLogger(RequestMatcherFilter.class);
	
	private List<RequestMatcher> urlMatcher = new ArrayList<>();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if(isFilterApplied((HttpServletRequest)request)) {
			logger.info("Applying the filter to the request");
		} else {
			logger.info("Not Applying the filter to the request");
		}
		
		chain.doFilter(request, response);
	}
	
	@Value("#{'${request.matcher}'.split(',')}")
	private void setUrlMatcher(List<String> requestMatcher) {
		if(requestMatcher != null) {
			this.urlMatcher = new ArrayList<>();
			requestMatcher.forEach(request -> {
				this.urlMatcher.add(new AntPathRequestMatcher(request));
			});
		}
	}
	
	private boolean isFilterApplied(HttpServletRequest request) {
		return this.urlMatcher.stream().anyMatch(matcher -> matcher.matches(request));
	}

}
