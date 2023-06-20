package com.lct.AdminController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.WebContext;
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
import org.thymeleaf.context.Context;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;

import com.lct.entities.Order;
import com.lct.entities.OrderDetail;
import com.lct.repository.OrderDetailRepository;
import com.lct.repository.OrderRepository;

@Controller
public class ControllerOrder {
	@Autowired
	private TemplateEngine templateEngine;
	@Autowired
	private OrderRepository dao;
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	

	@RequestMapping("asm/admin/order")
	public String paginate(Model model, @RequestParam("p") Optional<Integer> p) {
		Order item = new Order();
		model.addAttribute("item",item);
		Pageable pageable = PageRequest.of(p.orElse(0), 10);
		Page<Order> items = dao.findAll(pageable);
		model.addAttribute("items", items);
		return "admin/order";
	}
	
//	Xuất file pdf cho hóa đơn
	@RequestMapping("/asm/admin/order/{id}/export")
	public void exportInvoiceToPdf(@PathVariable("id") Long id, HttpServletResponse response, HttpServletRequest request) throws IOException, DocumentException {
        // Lấy hóa đơn thanh toán từ CSDL hoặc bất kỳ nguồn nào khác
        Order order = dao.findById(id).get();
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
	

}
