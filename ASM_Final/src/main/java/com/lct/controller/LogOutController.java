package com.lct.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.lct.constant.SessionAttr;
import com.lct.utils.SessionService;

@Controller
public class LogOutController {

	@Autowired
	private SessionService sessionSv;
	
	@GetMapping("asm/logout")
	public String view() {
		sessionSv.remove(SessionAttr.CURRENT_USER);
		return "redirect:/asm/login";
	}
}
