package com.lct.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.lct.beans.MailInformation;
import com.lct.constant.SessionAttr;
import com.lct.entities.Account;
import com.lct.entities.Order;
import com.lct.entities.OrderDetail;
import com.lct.entities.Product;
import com.lct.repository.AccountRepository;
import com.lct.repository.OrderDetailRepository;
import com.lct.repository.OrderRepository;
import com.lct.repository.ProductRepository;
import com.lct.service.MailService;
import com.lct.service.ShoppingCartService;
import com.lct.service.implement.MailServiceImpl;
import com.lct.service.implement.ShoppingCartServiceImpl;
import com.lct.utils.ParamService;
import com.lct.utils.SessionService;


@Controller
public class CartController {
	@Autowired
	ShoppingCartService cart;
	@Autowired
	ShoppingCartServiceImpl shoppingCart;
	@Autowired
	ParamService param;
	@Autowired
	ProductRepository productRepo;
	@Autowired
	OrderRepository orderDao;
	@Autowired
	OrderDetailRepository orderDetailDao;
	@Autowired
	SessionService sessionSv;
	@Autowired
	AccountRepository accRepo;
	@Autowired
	MailServiceImpl mailer;
	@Autowired
	HttpServletRequest req;
	
	@RequestMapping("asm/cart/view")
	public String view(Model model) {
		model.addAttribute("count", cart.getCount());
		model.addAttribute("cart", cart);

		return "shop-cart";
	}
	
	@RequestMapping("asm/cart/add/{id}")
	public String add(@PathVariable("id") Long id, RedirectAttributes redirectAttributes, @RequestParam("quantity") int quantity) {
		Product product = productRepo.findById(id).get();
		System.out.println(quantity);
		if(quantity <= product.getQuantity()) {
			cart.add(id, quantity);
		}else {
			redirectAttributes.addAttribute("message", "Không đủ số lượng sản phẩm!");
		}
		
		return "redirect:/asm/cart/view";
	}
	
	@RequestMapping("asm/cart/sub/{id}")
	public String sub(@PathVariable("id") Long id) {
		cart.sub(id);
		return "redirect:/asm/cart/view";
	}
	
	@RequestMapping("asm/cart/remove/{id}")
	public String remove(@PathVariable("id") Long id) {
		cart.remove(id);
		return "redirect:/asm/cart/view";
	}

	@RequestMapping("asm/cart/update/{id}")
	public String update(@PathVariable("id") Long id) {
		cart.update(id);
		return "redirect:/asm/cart/view";
	}

	@RequestMapping("asm/cart/clear")
	public String clear() {
		cart.clear();
		return "redirect:/asm/cart/view";
	}
	
	@PostMapping("asm/cart/checkout")
	public String create(@RequestParam(value = "address", defaultValue = "") String address, @RequestParam("nphone") String nphone,
			RedirectAttributes redirectAttributes) {
		try {
			Account currentUser = sessionSv.get("currentUser");
			if(currentUser == null) {
				return "redirect:/asm/login";
			}else {
				double totalPrice = cart.getAmount();
				Order order = new Order();
				order.setUserid(currentUser.getUsername());
				order.setDate(new Date());
				order.setAddress(address);
				order.setNphone(nphone);
				order.setTotalPrice(totalPrice);
				order.setOrderStatus(1);
				orderDao.save(order);
				shoppingCart.getProductsInShoppingCartInsertIntoOrderDetail(order);
				MailInformation mail = new MailInformation();
				mail.setTo(currentUser.getEmail());
				String subject = "POPEYES Chào Bạn!";
				String body = "Cảm ơn bạn đã đặt hàng của chúng tôi!";
				mail.setSubject(subject);
				mail.setBody(body);
				mailer.send(mail);
				redirectAttributes.addAttribute("buyMessage", "Thanh toán thành công! Bạn hãy kiểm tra email.");
				System.out.print(order);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			redirectAttributes.addAttribute("buyMessage","Thanh toán thất bại");
		}
		
		return "redirect:/asm/cart/view";
	}

}
