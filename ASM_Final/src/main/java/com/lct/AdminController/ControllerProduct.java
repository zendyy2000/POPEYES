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

import com.lct.entities.Product;
import com.lct.repository.ProductRepository;

@Controller
public class ControllerProduct {

	@Autowired
	private ProductRepository dao;

	@RequestMapping("asm/admin/product")
	public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
		int pageValue = p.orElseGet(() -> 0);
		if (pageValue < 0) {
			pageValue = 0;
		}
		Product item = new Product();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(pageValue, 5);
		Page<Product> items = dao.findAll(pageable);
		model.addAttribute("items", items);
		return "admin/product";
	}

	@RequestMapping("asm/admin/product/edit/{productid}")
	public String edit(Model model, @PathVariable("productid") Long id, @RequestParam("p") Optional<Integer> p) {
		Product item = dao.findById(id).get();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Page<Product> items = dao.findAll(pageable);
//		 List<Product> items = dao.findAll();
		model.addAttribute("items", items);
		return "admin/product";
	}

	@RequestMapping("asm/admin/product/create")
	public String create(Product item) {
		dao.save(item);
		return "redirect:/asm/admin/product";
	}

	@RequestMapping("asm/admin/product/update")
	public String update(Product item) {
		dao.save(item);
		return "redirect:/asm/admin/product";
	}

	@RequestMapping("asm/admin/product/delete/{productid}")
	public String delete(@PathVariable("productid") Long id, Model model) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {

		}
		return "redirect:/asm/admin/product";
	}

}
