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

@Controller
public class MaterialController {

	@Autowired
	private MaterialDAO materialDAO;

	@RequestMapping(value = "/material", method = RequestMethod.GET)
	public String getViewPage(@RequestParam(name = "page", required = false, defaultValue = "0") int pageID,
			ModelMap model) {

		Material material = new Material();

		int limit = 11;
		int start = 0;
		if (pageID == 0) {
			// do nothing
		} else {

			start = (pageID - 1) * limit;
		}

		List<Material> list = materialDAO.getListForEachPage(start, limit);
		model.addAttribute("materials", list);
		model.addAttribute("material", material);

		return "materialPage";
	}

	/*
	 * @RequestMapping(value="/material", params = "action=add" , method =
	 * RequestMethod.POST) public String addMaterial(@Valid Material material,
	 * BindingResult result, RedirectAttributes redirect) {
	 * 
	 * if(result.hasErrors()) { // lỗi
	 * 
	 * redirect.addFlashAttribute("msgFailure", "Thêm thất bại");
	 * 
	 * } else {
	 * 
	 * boolean addSuccess = materialDAO.add(material);
	 * 
	 * if(addSuccess) {
	 * 
	 * redirect.addFlashAttribute("msgSuccess", "Thêm thành công"); } else {
	 * 
	 * redirect.addFlashAttribute("msgFailure", "Thêm thất bại");
	 * 
	 * }
	 * 
	 * }
	 * 
	 * System.out.println("da vao day nhe!!");
	 * 
	 * return "redirect:/material"; }
	 */

	@RequestMapping(value = "/material", params = "action", method = RequestMethod.POST)
	public String actionMaterial(@Valid Material material, BindingResult result, RedirectAttributes redirect,
			@RequestParam(name = "action") String action) {

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

		return "redirect:/material";
	}

	// delete material
	@RequestMapping(value = "/material/{materialID}", params = "action=delete", method = RequestMethod.GET)
	public String deleteMaterial(@PathVariable(name = "materialID") String id, RedirectAttributes redirect) {

		boolean deleteSuccess = materialDAO.delete(id);

		if (deleteSuccess) {

			redirect.addFlashAttribute("msgSuccess", "Xóa nhân viên thành công");

		} else {

			redirect.addFlashAttribute("msgFailure", "Xóa nhân viên thất bại");

		}

		return "redirect:/material";

	}

}
