package com.lct.controller;

import java.net.http.HttpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lct.constant.SessionAttr;
import com.lct.entities.Account;
import com.lct.repository.AccountRepository;
import com.lct.utils.CookieService;
import com.lct.utils.ParamService;
import com.lct.utils.SessionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private AccountRepository accRepo;
	@Autowired
	private ParamService paramSv;
	@Autowired
	private SessionService sessionSv;
	@Autowired
	private CookieService cookieSv;

	@RequestMapping("asm/login")
	public String view() {
		return "login";
	}

	@PostMapping("asm/login")
	public String login(@Validated @ModelAttribute("ac") Account account, BindingResult result, Model model,
			HttpSession session) {

		if (result.hasErrors()) {
			return "login";
		} else {
			String username = paramSv.getString("username", "");
			String password = paramSv.getString("password", "");
			boolean remember = paramSv.getBoolean("remember", false);

			System.out.println(username);
			System.out.println(password);
			System.out.println(remember);
			String check = String.valueOf(remember);
			try {
				Account user = accRepo.findByUsername(username);
				if (user.getUsername().equalsIgnoreCase(username) && user.getPassword().equalsIgnoreCase(password)) {
					System.out.print(user);
					System.out.print(session);

					if (check.matches("true")) {
						cookieSv.add("username", user.getUsername(), 12);
						cookieSv.add("password", user.getPassword(), 12);
					} else {
						cookieSv.remove("username");
						cookieSv.remove("password");
					}
					sessionSv.set(SessionAttr.CURRENT_USER, user);
					String uri = sessionSv.get("security-uri");

					if (uri != null) {
						if (!uri.equals("") && uri.contains("/asm/admin"))
							return "redirect:/asm/admin/home";
						else if(uri.contains("/asm/home"))
							return "redirect:index";
					}

				} else {
					model.addAttribute("message", "Tài khoản hoặc mật khẩu không chính xác!");
					return "login";
				}
			} catch (Exception e) {
				model.addAttribute("message", "Tài khoản không tồn tại");
			}
		}
		return "login";
	}
	
	 @GetMapping("/success")
	    public String success(Model model, OAuth2AuthenticationToken authentication) {
	        // Lấy thông tin người dùng từ xác thực OAuth2
	        OAuth2User user = authentication.getPrincipal();
	        String email = user.getAttribute("email");
	        String name = user.getAttribute("name");
	        model.addAttribute("email", email);
	        model.addAttribute("name", name);
	        Account existingAccount = accRepo.findByEmailEquals(email);
	        if(existingAccount != null) {
	        	if(existingAccount.getRole()) {
	        		existingAccount.setFullname(name);
		        	accRepo.save(existingAccount);
		        	sessionSv.set(SessionAttr.CURRENT_USER, existingAccount);
	        	}else {
	        		existingAccount.setFullname(name);
		        	existingAccount.setRole(false);
		        	accRepo.save(existingAccount);
		        	sessionSv.set(SessionAttr.CURRENT_USER, existingAccount);
	        	}
	        	
	        }else {
	        	Account newAcc = new Account();
	        	newAcc.setUsername(email);
	        	newAcc.setEmail(email);
	        	newAcc.setFullname(name);
	        	newAcc.setRole(false);
	        	accRepo.save(newAcc);
	        	sessionSv.set(SessionAttr.CURRENT_USER, newAcc);
	        }
	        return "message"; // Trả về template thành công
	    }
}
