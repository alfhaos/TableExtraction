package com.vtw.intern;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/")
	public String index(HttpSession session) {

		return "forward:/index.jsp";
	}
	
}
