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

import com.tathao.springmvc.dao.MaterialDAO;
import com.tathao.springmvc.model.Material;
import com.tathao.springmvc.utils.MyAuthorities;

@Controller
public class MaterialController {

	@Autowired
	private MaterialDAO materialDAO;

	@RequestMapping(value = "/branch/{branchId}/material", method = RequestMethod.GET)
	public String getViewPage(
			@RequestParam(name = "page", required = false, defaultValue = "0") int pageID,
			@PathVariable("branchId") String branchId ,
			ModelMap model) {

		boolean isUser = MyAuthorities.hasRole(MyAuthorities.ROLE_USER);
		boolean isBranch = MyAuthorities.hasRole(MyAuthorities.ROLE_BRANCH);
		boolean isCompany = MyAuthorities.hasRole(MyAuthorities.ROLE_COMPANY);
		
		// if role is:
		if(isUser || isBranch) { // USER or CHINHANH
			// do nothing
		} else if(isCompany) { // CONGTY (read-only data)
			model.addAttribute("role", "role_company");
		}
		
		int limit = 11;
		int start = 0;
		if (pageID == 0) {
			// do nothing
		} else {
			start = (pageID - 1) * limit;
		}
		
		Material material = new Material();
		model.addAttribute("material", material);
		
		List<Material> list = materialDAO.getListForEachPage(start, limit);
		model.addAttribute("materials", list);

		return "materialPage";
	}

	@RequestMapping(value = "branch/{branchId}/material", params = "action", method = RequestMethod.POST)
	public String actionMaterial(
			@Valid Material material, 
			BindingResult result,
			RedirectAttributes redirect,
			@RequestParam(name = "action") String action,
			@PathVariable(name="branchId") String branchId) {

		if (action.equals("add")) {

			if (result.hasErrors()) { // lỗi

				redirect.addFlashAttribute("msgFailure", "Thêm thất bại");

			} else {

				boolean addSuccess = materialDAO.add(material);

				if (addSuccess) {

					redirect.addFlashAttribute("msgSuccess", "Thêm thành công");
				} else {

					redirect.addFlashAttribute("msgFailure", "Thêm thất bại");

				}
			}

		} else if (action.equals("update")) {

			if (result.hasErrors()) { // lỗi
				redirect.addFlashAttribute("msgFailure", "Cập nhật thất bại");
			} else {
				boolean updateSuccess = materialDAO.update(material);

				if (updateSuccess) {
					redirect.addFlashAttribute("msgSuccess", "Cập nhật thành công");
				} else {
					redirect.addFlashAttribute("msgFailure", "Cập nhật thất bại");
				}
			}

		}

		return "redirect:/branch/" + branchId + "/material";
	}

	// delete material
	@RequestMapping(value = "branch/{branchId}/material/{materialId}", params = "action=delete", method = RequestMethod.GET)
	public String deleteMaterial(@PathVariable(name = "materialId") String materialId,
			RedirectAttributes redirect,
			@PathVariable(name="branchId")String branchId) {

		boolean deleteSuccess = materialDAO.delete(materialId);

		if (deleteSuccess) {

			redirect.addFlashAttribute("msgSuccess", "Xóa thành công");

		} else {

			redirect.addFlashAttribute("msgFailure", "Xóa thất bại");

		}

		return "redirect:/branch/" + branchId + "/material";

	}

}
