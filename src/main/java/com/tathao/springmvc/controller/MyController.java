package com.tathao.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {

	@RequestMapping(value= {"/", "/home", "/dashboard"}, method=RequestMethod.GET)
	public String getHomeView() {
		return "homepage";
	}

}
