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

import com.lct.entities.Account;
import com.lct.repository.AccountRepository;

@Controller
public class ControllerUser {
	@Autowired
	private AccountRepository dao;
	
	@RequestMapping("asm/admin/account/edit/{username}")
	public String edit(Model model, @PathVariable("username") String id,@RequestParam("p") Optional<Integer> p) {
		Account item = dao.findById(id).get();
		model.addAttribute("item", item);
		Pageable pageable = PageRequest.of(p.orElse(0), 3);
		Page<Account> items = dao.findAll(pageable);
//		List<Account> items = dao.findAll();
		model.addAttribute("items", items);
		return "admin/user";
	}
	
	@RequestMapping("asm/admin/account/create")
	public String create(Account item) {
		dao.save(item);
		return "redirect:/asm/admin/account";
	}
	
	@RequestMapping("asm/admin/account/update")
	public String update(Account item) {
		dao.save(item);
		return "redirect:/asm/admin/account";
	}
	@RequestMapping("asm/admin/account/delete/{username}")
	public String delete(@PathVariable("username") String id,Model model) {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			
		}
		return "redirect:/asm/admin/account";
	}
	
	@RequestMapping("asm/admin/account")
	public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
		int pageValue = p.orElseGet(() -> 0);
		if (pageValue < 0) {
			pageValue = 0;
		}
		Account item = new Account();
		model.addAttribute("item",item);
		Pageable pageable = PageRequest.of(pageValue, 5);
		Page<Account> items = dao.findAll(pageable);
		model.addAttribute("items", items);
		return "admin/user";
	}
}
