package com.lct.AdminController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lct.entities.Order;
import com.lct.entities.OrderDetail;
import com.lct.repository.OrderDetailRepository;

@Controller
public class ControllerOrderDetail {
	@Autowired
	private OrderDetailRepository dao;
	
	

	@RequestMapping("asm/admin/orderdetail")
	public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Page<OrderDetail> items = dao.findAll(pageable);
		model.addAttribute("items", items);
		return "admin/orderdetail";
	}

}
