package com.lct.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lct.entities.Account;
import com.lct.entities.Category;
import com.lct.entities.Product;
import com.lct.repository.AccountRepository;
import com.lct.repository.CategoryRepository;
import com.lct.repository.OrderDetailRepository;
import com.lct.repository.ProductRepository;
import com.lct.utils.SessionService;


@Controller
public class HomeController {

	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private CategoryRepository cateRepo;
//	@Autowired
//	private OrderDetailRepository odetailRepo;
	@Autowired
	private HttpSession session;
//	@Autowired
//	private AccountRepository accRepo;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	SessionService sessionSv;
	
	@RequestMapping("asm/home")
	public String index(Model model, @ModelAttribute("product") Product product, @RequestParam("p") Optional<Integer> p) {
		int pageValue = p.orElseGet(() -> 0);
		if (pageValue < 0) {
			pageValue = 0;
		}
		Pageable pageable = PageRequest.of(pageValue, 8);

		Page<Product> page = productRepo.findAll(pageable);
		model.addAttribute("page", page);
		List<Category> cagatory = cateRepo.findAll();
		String username = sessionSv.get("session.currentUser.fullname");

		System.out.print(username);
		model.addAttribute("list", page);
		model.addAttribute("cate", cagatory);
		return "index";
	}
	
	
	@RequestMapping("asm/home/cate/{categoryid}")
	public String productCate(Model model, @PathVariable("categoryid") String categoryid) {
		List<Category> cagatory = cateRepo.findAll();
		List<Product> pc = productRepo.findByCategoryHome(categoryid) ;
		model.addAttribute("cate", cagatory);
		model.addAttribute("pc",pc);
		return "productCate";
	}
	
	@RequestMapping("asm/home/product")
	public String findProductName(Model model, @RequestParam("keywords") Optional<String> kw) {
		String kwords = kw.orElse(sessionSv.get("keywords"));
		sessionSv.set("keywords",kwords);
		List<Product> pf = productRepo.findProductByName("%"+kwords+"%");
		model.addAttribute("pf",pf);
		return "productFind";
	}
	
	
}
