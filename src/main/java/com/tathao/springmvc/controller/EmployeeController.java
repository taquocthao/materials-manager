package com.tathao.springmvc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tathao.springmvc.dao.BranchDAO;
import com.tathao.springmvc.dao.EmployeeDAO;
import com.tathao.springmvc.model.Branch;
import com.tathao.springmvc.model.Employee;
import com.tathao.springmvc.utils.MyAuthorities;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeDAO empDAO;
	
	@Autowired
	private BranchDAO branchDAO;

	private static final String contextPath = "/branch/{branchId}/";
	
	@RequestMapping(value = { contextPath + "/employee"} ,method = RequestMethod.GET)
	public String getEmployeeView(
			ModelMap model,
			@RequestParam(name="page", required = false, defaultValue = "0") 
			int pageID,
			@PathVariable(name="branchId") String branchId) {
		
		boolean isUser = MyAuthorities.hasRole(MyAuthorities.ROLE_USER);
		boolean isBranch = MyAuthorities.hasRole(MyAuthorities.ROLE_BRANCH);
		boolean isCompany = MyAuthorities.hasRole(MyAuthorities.ROLE_COMPANY);
		
		if(isUser || isBranch) {
//			do nothing
		} else if(isCompany) {
			model.addAttribute("role", "role_company");
		}
		
		// limit record in each page
		int start = 0;
		int limit = 10;
		if(pageID == 0) {
			// do nothing
		} else {
			
			start = (pageID - 1) * limit;
		}
		List<Employee> listEmployee = empDAO.getEmployeesForEachPage(start, limit);
		
		model.addAttribute("listEmployees", listEmployee);
		
		return "employeePage";
	}

	@RequestMapping(value = contextPath + "/employee", params = "action=add", method = RequestMethod.GET)
	public String getAddEmployeeView(ModelMap model) {

		List<Branch> listBranch = branchDAO.getAllBranchs();
		model.addAttribute("branchs", listBranch);
		
		Employee e = new Employee();
		model.addAttribute("employee", e);
		
		return "addEmployeePage";
	}

	
	@RequestMapping(value = contextPath + "/employee", params = "action=add", method = RequestMethod.POST)
	public ModelAndView addEmployee(@Valid Employee e, BindingResult bindingResult,
			RedirectAttributes redirect,
			@PathVariable(name="branchId") String branchId) {

		ModelAndView model = new ModelAndView();
		if (bindingResult.hasErrors()) {
			
			List<Branch> listBranch = branchDAO.getAllBranchs();
			
			model.setViewName("addEmployeePage");
			model.addObject("branchs", listBranch);
			
		} else {
			
			e.setId(0);
			
			boolean isAddSuccess = empDAO.addEmployee(e);

			if (isAddSuccess) {
				
				redirect.addFlashAttribute("msgSuccess", "Thêm nhân viên thành công!");
				
			} else {
				
				redirect.addFlashAttribute("msgFailure", "Thêm nhân viên thất bại!");
				
			}

			model.setViewName("redirect:/branch/" + branchId + "/employee");

		}

		return model;
	}

	@RequestMapping(value = contextPath + "/employee/{employeeId}", params = ("action=delete"))
	public String deleteEmployee(@PathVariable(name = "employeeId") int id, 
			RedirectAttributes redirect,
			@PathVariable(name="branchId") String branchId) {

		boolean isDeleteSuccess = empDAO.deleteEmployee(id);

		if (isDeleteSuccess) {
			
			redirect.addFlashAttribute("msgSuccess", "Xóa nhân viên thành công");
			
		} else {
			
			redirect.addFlashAttribute("msgFailure", "Xóa nhân viên thất bại!");
			
		}
		return "redirect:/branch/" + branchId + "/employee";

	}

	@RequestMapping(value = contextPath + "/employee", params = {"action", "id"}, method = RequestMethod.GET)
	public String getUpdateEmployeeView(
			@RequestParam(name = "action") String action,
			@RequestParam(name = "id") int id,
			@PathVariable(name = "branchId") String branchId,
			ModelMap model) {

		if(action.equals("update")) {
			
			List<Branch> branchs = branchDAO.getAllBranchs();
			Employee employee = empDAO.getEmployeeById(id);
			
			model.addAttribute("branchs", branchs);			
			model.addAttribute("employee", employee);
			
			return "updateEmployeePage";
		}

		return null;
	}
	
	@RequestMapping(value= contextPath + "/employee", params="action=update", method=RequestMethod.POST)
	public ModelAndView updateEmployee(@Valid Employee e, BindingResult result ,
			RedirectAttributes redirect,
			@PathVariable(name = "branchId") String branchId) {
		
		ModelAndView model = new ModelAndView();
		
		if(result.hasErrors()) {
			
			List<Branch> listBranch = branchDAO.getAllBranchs();
			Employee employee = empDAO.getEmployeeById(e.getId());
			
			model.addObject("employee", employee);
			model.addObject("branchs", listBranch);
			
			model.setViewName("updateEmployeePage");
		} else {
			
			boolean isUpdateSuccess = empDAO.updateEmployee(e);
			
			if(isUpdateSuccess) {
				
				redirect.addFlashAttribute("msgSuccess", "Cập nhật thành công");
				model.setViewName("redirect:/branch/" + branchId + "/employee");
				
			} else {
				
				List<Branch> listBranch = branchDAO.getAllBranchs();
				Employee employee = empDAO.getEmployeeById(e.getId());
				
				model.addObject("employee", employee);
				model.addObject("branchs", listBranch);
				
				model.setViewName("updateEmployeePage");
			}
		}
		
		return model;
	}

}
