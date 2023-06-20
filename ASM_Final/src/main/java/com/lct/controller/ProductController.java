package com.lct.controller;

import java.util.*;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lct.entities.Product;
import com.lct.repository.ProductRepository;
import com.lct.utils.SessionService;

import javax.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private SessionService session;
	

	@GetMapping("/product/detail/{id}")
	public String index(@PathVariable("id") long id, Model model) {
		Product products = productRepo.findById(id).get();
		model.addAttribute("list", products);
		
		List<Product> products1 = productRepo.findAll();


		List<Product> relatedProducts = new ArrayList<Product>();
		for (Product product : products1) {
			if (Math.abs(product.getPrice() - products.getPrice()) <= 50000.0) {
				relatedProducts.add(product); // Thêm sản phẩm có giá chênh lệch +- 1.0 vào danh sách
			}
		}
		
		model.addAttribute("lPrice", relatedProducts);

		return "product-details";
	}
	
}
