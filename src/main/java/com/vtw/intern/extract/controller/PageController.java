package com.vtw.intern.extract.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class PageController {

	@GetMapping("/basic")
	private String basic() {
		
		return "forward:/WEB-INF/views/intern/basic.jsp";
	}
	
	@GetMapping("/advanced")
	private String advanced() {
		
		return "forward:/WEB-INF/views/intern/advanced.jsp";
	}
	
	@GetMapping("/expert")
	private String expert() {
		
		return "forward:/WEB-INF/views/intern/expert.jsp";
	}
	
	
}
