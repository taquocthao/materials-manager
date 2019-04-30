package com.tathao.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tathao.springmvc.utils.MyAuthorities;

@Controller
public class MyController {
	
	@RequestMapping(value= {"/branch/{branch_id}/home"}, method=RequestMethod.GET)
	public String getHomeView(@PathVariable(name = "branch_id") String branch_id,
			Model model)  {
		
		if(MyAuthorities.hasRole(MyAuthorities.ROLE_USER)) {
			model.addAttribute("msg", "User Role");
		} else if(MyAuthorities.hasRole(MyAuthorities.ROLE_BRANCH)) {
			model.addAttribute("msg", "Chi nhanh role");
		} else if(MyAuthorities.hasRole(MyAuthorities.ROLE_COMPANY)) {
			model.addAttribute("msg", "Role company");
		}
		
		return "homepage";
	}

}
