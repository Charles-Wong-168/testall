package com.ansel.testall.aop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Aop2Controller {

	@RequestMapping(value = "/aop2")
	public String testAop2() {
		return "index.html";
	}
	
}
