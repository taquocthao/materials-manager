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

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeDAO empDAO;
	
	@Autowired
	private BranchDAO branchDAO;

	@RequestMapping(value = {"/employee"} ,method = RequestMethod.GET)
	public String getEmployeeView(ModelMap model, 
			@RequestParam(name="page", required = false, defaultValue = "0") 
			int pageID) {
		
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

	@RequestMapping(value = "/employee", params = "action=add", method = RequestMethod.GET)
	public String getAddEmployeeView(ModelMap model) {

		List<Branch> listBranch = branchDAO.getAllBranchs();
		model.addAttribute("branchs", listBranch);
		
		Employee e = new Employee();
		model.addAttribute("employee", e);
		
		return "addEmployeePage";
	}

	
	@RequestMapping(value = "/employee", params = "action=add", method = RequestMethod.POST)
	public ModelAndView addEmployee(@Valid Employee e, BindingResult bindingResult, RedirectAttributes redirect) {

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

			model.setViewName("redirect:/employee");

		}

		return model;
	}

	@RequestMapping(value = "/employee/{idEmployee}", params = ("action=delete"))
	public String deleteEmployee(@PathVariable(name = "idEmployee") int id, RedirectAttributes redirect) {

		boolean isDeleteSuccess = empDAO.deleteEmployee(id);

		if (isDeleteSuccess) {
			
			redirect.addFlashAttribute("msgSuccess", "Xóa nhân viên thành công");
			
		} else {
			
			redirect.addFlashAttribute("msgFailure", "Xóa nhân viên thất bại!");
			
		}
		return "redirect:/employee";

	}

	@RequestMapping(value = "/employee", params = {"action", "id"}, method = RequestMethod.GET)
	public String getUpdateEmployeeView(
			@RequestParam(name = "action") String action,
			@RequestParam(name = "id") int id,
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
	
	@RequestMapping(value="/employee", params="action=update", method=RequestMethod.POST)
	public ModelAndView updateEmployee(@Valid Employee e, BindingResult result ,RedirectAttributes redirect) {
		
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
				model.setViewName("redirect:/employee");
				
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
