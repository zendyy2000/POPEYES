package com.lct.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import com.lct.utils.SessionService;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.lct.entities.*;
import com.lct.repository.OrderDetailRepository;
import com.lct.repository.OrderRepository;

@Controller
public class OrderController {
	@Autowired
	SessionService sessionSv;
	@Autowired
	OrderRepository orderRepo;
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	OrderDetailRepository orderDetailRepo;

	@RequestMapping("asm/order")
	public String getHistory(Model model, @RequestParam("p") Optional<Integer> p) {
		int pageValue = p.orElseGet(() -> 0);
		if (pageValue < 0) {
			pageValue = 0;
		}
		Pageable pageable = PageRequest.of(pageValue, 8);
		Account currentUser = sessionSv.get("currentUser");
		Page<Order> order = orderRepo.findOrderByAccount(pageable, currentUser.getUsername());
		model.addAttribute("order", order);
		return "orderUser";
	}

//	Xuất file pdf cho hóa đơn
	@RequestMapping("/asm/order/{id}/export")
	public void exportInvoiceToPdf(@PathVariable("id") Long id, HttpServletResponse response,
			HttpServletRequest request) throws IOException, DocumentException {
		// Lấy hóa đơn thanh toán từ CSDL hoặc bất kỳ nguồn nào khác
		Order order = orderRepo.findById(id).get();
		ServletContext servletContext = request.getServletContext();

		// Tạo HTML từ template Thymeleaf và hóa đơn thanh toán
		WebContext context = new WebContext(request, response, servletContext, request.getLocale());

		context.setVariable("order", order);
		String html = templateEngine.process("admin/order", context);

		// Thiết lập các thông số cho phản hồi HTTP
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"payment_invoice_" + id + ".pdf\"");

		// Tạo document và PdfWriter
		Document document = new Document();
		OutputStream outputStream = response.getOutputStream();
		PdfWriter.getInstance(document, outputStream);

		// Mở document và thêm nội dung
		document.open();
		document.add(new Paragraph("Order ID: " + order.getId()));
		document.add(new Paragraph("Customer Name: " + order.getUserid()));
		document.add(new Paragraph("Amount: " + order.getTotalPrice()));
		document.add(new Paragraph("Amount: " + order.getAddress()));
		document.add(new Paragraph("Payment Date: " + order.getDate()));
		// Thêm các thông tin khác cần thiết

		// Đóng document
		document.close();

		// Đóng outputStream
		outputStream.close();
	}

	@RequestMapping("/asm/order/detail")
	public String getOrderDetail(Model model, @RequestParam("orderId") Long orderId) {
		List<OrderDetail> ordersDetails = orderDetailRepo.findOrdersDetailByOrderId(orderId);
		model.addAttribute("ordersDetails", ordersDetails);
		return "orderdetailUser";
	}
}
