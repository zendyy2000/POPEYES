package com.lct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lct.constant.SessionAttr;
import com.lct.entities.Account;
import com.lct.repository.AccountRepository;
import com.lct.utils.EncryptUtil;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

	@Autowired
	private AccountRepository accRepo;
	
	@RequestMapping("asm/register")
	public String view() {
		return "register";
	}
	
	@PostMapping("asm/register/create")
	public String create(@Validated @ModelAttribute("ac") Account account, BindingResult result, Model model, HttpSession session) {
		account.setRole(false);
		account.setPassword(EncryptUtil.encrypt(account.getPassword().trim()));
		accRepo.save(account);
		model.addAttribute("message", "Insert successfully");
		return "redirect:/asm/login";
	}
}
