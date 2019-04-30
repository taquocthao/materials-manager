package com.tathao.springmvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tathao.springmvc.dao.BranchDAO;
import com.tathao.springmvc.model.Branch;

@Controller
public class LoginController {

	@Autowired
	private BranchDAO branchDAO;
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		
		System.out.println("LoginController::loginPage()");
		
		List<Branch> branchs = branchDAO.getAllBranchs();
		
		System.out.println("Branch size: " + branchs.size());
		
		model.addAttribute("branchs", branchs);
		
		return "loginPage";
	}

}
