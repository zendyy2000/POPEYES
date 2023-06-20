package com.lct.AdminController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lct.dto.Report;
import com.lct.dto.ReportByProduct;
import com.lct.repository.OrderDetailRepository;
import com.lct.repository.ProductRepository;

import java.util.*;

@Controller
public class AdminHomeController {
	@Autowired
	OrderDetailRepository orderDetail;
	@Autowired
	ProductRepository proRepo;


	@RequestMapping("asm/admin/home")
	public String view(Model model) {
		List<Report> revenueList = orderDetail.revenueByCategory();
		model.addAttribute("revenueList", revenueList);
//		List<ReportByProduct> revenueListProduct = orderDetail.revenueByProduct();
//		model.addAttribute("revenueListByProduct", revenueListProduct);
		return "admin/home";
	}
}
