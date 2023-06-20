package com.lct.AdminController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lct.entities.Category;
import com.lct.repository.CategoryRepository;

@Controller
public class ControllerCategory {

	@Autowired
	private CategoryRepository dao;

	@RequestMapping("asm/admin/category/edit/{categoryid}")
	public String edit(Model model, @PathVariable("categoryid") String id, @RequestParam("p") Optional<Integer> p) {
		Category item = dao.findById(id).get();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Page<Category> items = dao.findAll(pageable);
		/* List<Category> items = dao.findAll(); */
		model.addAttribute("items", items);
		return "admin/category";
	}

	@RequestMapping("asm/admin/category/create")
	public String create(Category item, Model model) {
		dao.save(item);
		model.addAttribute("message", "Create success!");
		return "redirect:/asm/admin/category";
	}

	@RequestMapping("asm/admin/category/update")
	public String update(Category item, Model model) {
		dao.save(item);
		model.addAttribute("message", "Update success!");
		return "redirect:/asm/admin/category";
	}

	@RequestMapping("asm/admin/category/delete/{categoryid}")
	public String delete(@PathVariable("categoryid") String id, Model model) {
		try {
			dao.deleteById(id);
			model.addAttribute("message", "Delete success!");
		} catch (Exception e) {
			return "/category/result";
		}
		return "redirect:/asm/admin/category";
	}

	@RequestMapping("asm/admin/category")
	public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
		int pageValue = p.orElseGet(() -> 0);
		if (pageValue < 0) {
			pageValue = 0;
		}
		Category item = new Category();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(pageValue, 5);
		Page<Category> items = dao.findAll(pageable);
		model.addAttribute("items", items);
		return "admin/category";
	}
}
