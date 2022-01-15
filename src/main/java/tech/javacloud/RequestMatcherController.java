/**
 * 
 */
package tech.javacloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author javacloud.tech
 *
 */
@Controller
public final class RequestMatcherController {

	@GetMapping("/hello")
	public String hello(final Model model) {
		model.addAttribute("hello", "Hello!!!");
		return "hello";
	}
	
	@GetMapping("/secure")
	public String secure(final Model model, final HttpServletRequest request) {
		model.addAttribute("secure", request.getUserPrincipal().getName());
		return "secure";
	}
	
	@GetMapping("/hello/second")
	public String third(final Model model, final HttpServletRequest request) {
		model.addAttribute("secure", "Second Hello!!!");
		return "secure";
	}
}
