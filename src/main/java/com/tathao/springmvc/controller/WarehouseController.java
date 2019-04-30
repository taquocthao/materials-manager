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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tathao.springmvc.dao.BranchDAO;
import com.tathao.springmvc.dao.WarehouseDAO;
import com.tathao.springmvc.model.Branch;
import com.tathao.springmvc.model.Warehouse;
import com.tathao.springmvc.utils.MyAuthorities;

@Controller
public class WarehouseController {
	
	@Autowired
	private WarehouseDAO warehouseDAO;
	@Autowired
	private BranchDAO branchDAO;
	
	private static final String contextPath = "/branch/{branchId}";
	
	@RequestMapping(value= contextPath + "/warehouse", method = RequestMethod.GET)
	public String getView(ModelMap model, 
			@RequestParam(name = "page", required = false, defaultValue = "0") int pageID,
			@PathVariable(name = "branchId") String branchId) {
		
		boolean isUser = MyAuthorities.hasRole(MyAuthorities.ROLE_USER);
		boolean isBranch = MyAuthorities.hasRole(MyAuthorities.ROLE_BRANCH);
		boolean isCompany = MyAuthorities.hasRole(MyAuthorities.ROLE_COMPANY);
		
		if(isUser || isBranch) {
//			do nothing
		} else if(isCompany) {
			model.addAttribute("role", "role_company");
		}
		
		
		int start = 0;
		int limit = 10;
		
		if(pageID == 0) {
			// do nothing
		} else {
			start = (pageID - 1) * limit;
		}
		
		List<Warehouse> list = warehouseDAO.getAll(start, limit);
		Warehouse warehouse = new Warehouse();
		List<Branch> branchs = branchDAO.getAllBranchs();
		
		model.addAttribute("warehouse", warehouse);
		model.addAttribute("listWarehouse", list);
		model.addAttribute("branchs", branchs);
		
		return "warehousePage";
	}
	
	@RequestMapping(value= contextPath + "/warehouse", params= "action" ,method=RequestMethod.POST)
	public String editWarehouse(
			@Valid Warehouse warehouse, 
			BindingResult result,
			@RequestParam(name="action", required = false, defaultValue = "") String action,
			@RequestParam(name="id", required = false, defaultValue = "") String id,
			@PathVariable(name = "branchId") String branchId,
			RedirectAttributes redirect) {
		
		if(action.equals("add")) {
			
			if(result.hasErrors()) {
				
				redirect.addFlashAttribute("msgFailure", "Thêm thất bại");
				
			} else {
				
				boolean isSuccess = warehouseDAO.add(warehouse);
				if(isSuccess) {
					
					redirect.addFlashAttribute("msgSuccess", "Thêm thành công");
					
				} else {
					
					redirect.addFlashAttribute("msgFailure", "Thêm thất bại");
					
				}
			}
			
		} else if(action.equals("update")) {
			
			if(result.hasErrors()) {
				
				redirect.addFlashAttribute("msgFailure", "Cập nhật thất bại");
				
			} else {
				
				boolean isSuccess = warehouseDAO.update(warehouse);
				if(isSuccess) {
					
					redirect.addFlashAttribute("msgSuccess", "Cập nhật thành công");
					
				} else {
					
					redirect.addFlashAttribute("msgFailure", "Cập nhật thất bại");
					
				}
				
			}
			
		} else  if(action.equals("delete")) {
			
			boolean isSuccess = warehouseDAO.delete(id);
			if(isSuccess) {
				
				redirect.addFlashAttribute("msgSuccess", "Xoá thành công");
				
			} else {
				
				redirect.addFlashAttribute("msgFailure", "Xóa thất bại");
				
			}
			
		}
		
		return "redirect:/branch/" + branchId + "/warehouse";
	}
	
	
}
