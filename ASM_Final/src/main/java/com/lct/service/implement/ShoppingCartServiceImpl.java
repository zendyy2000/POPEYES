package com.lct.service.implement;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import com.lct.entities.Order;
import com.lct.entities.Product;
import com.lct.repository.OrderDetailRepository;
import com.lct.repository.ProductRepository;
import com.lct.service.ShoppingCartService;
import com.lct.entities.OrderDetail;

@SessionScope
@Controller
public class ShoppingCartServiceImpl implements ShoppingCartService {
	@Autowired
	ProductRepository productRepo;
	Map<Long, Product> shoppingCart = new HashMap<>();
	@Autowired
	OrderDetailRepository orderDetailRepo;

	@Override
	public Product add(Long id, int quantity) {
		Product item = shoppingCart.get(id);
		if (item == null) {
			item = productRepo.findProductById(id);
			item.setQuantity(quantity);
			shoppingCart.put(id, item);
		} else {
			item.setQuantity(item.getQuantity() + quantity);
			shoppingCart.put(id, item);
		}
		return item;
	}

	@Override
	public Product sub(Long id) {
		Product item = shoppingCart.get(id);
		if (item == null) {
			Product product = productRepo.findById(id).get();
			item = new Product();
			item.setProductid(product.getProductid());
			item.setPhoto(product.getPhoto());
			item.setProductName(product.getProductName());
			item.setPrice(product.getPrice());
			item.setQuantity(product.getQuantity());
			shoppingCart.put(id, item);
		} else {
			if (item.getQuantity() > 1) {
				item.setQuantity(item.getQuantity() - 1);
			} else {
				item.setQuantity(1);
			}
		}
		return item;
	}

	@Override
	public void remove(Long id) {
		// TODO Auto-generated method stub
		shoppingCart.remove(id);
	}

	@Override
	public Product update(Long id) {
		// TODO Auto-generated method stub
		Product item = shoppingCart.get(id);
		if (item == null) {
			Product product = productRepo.findById(id).get();
			item = new Product();
			item.setProductid(product.getProductid());
			item.setPhoto(product.getPhoto());
			item.setProductName(product.getProductName());
			item.setPrice(product.getPrice());
			item.setQuantity(product.getQuantity());
			shoppingCart.put(id, item);
		} else {
			Product product = productRepo.findById(id).get();
			if (item.getQuantity() < product.getQuantity()) {
				item.setQuantity(item.getQuantity() + 1);
			} else {
				item.setQuantity(1);;
			}
		}
		return item;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Product> getItems() {
		// TODO Auto-generated method stub
		return shoppingCart.values();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shoppingCart.values().stream().mapToInt(item -> item.getQuantity()).sum();
	}

	@Override
	public double getAmount() {
		// TODO Auto-generated method stub
		return shoppingCart.values().stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
	}

	public void getProductsInShoppingCartInsertIntoOrderDetail(Order orders) {
		Iterator<Entry<Long, Product>> iterator = shoppingCart.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Long, Product> entry = iterator.next();
			Product product = entry.getValue();
			Product productInStock = productRepo.findProductById(product.getProductid());
			int productRemain = productInStock.getQuantity() - product.getQuantity();
			productInStock.setQuantity(productRemain);
			OrderDetail ordersDetail = new OrderDetail();
			ordersDetail.setOrder(orders);
			ordersDetail.setQuantity(product.getQuantity());
			ordersDetail.setProduct(product);
			ordersDetail.setPrice(product.getPrice());
			orderDetailRepo.save(ordersDetail);
			productRepo.save(productInStock);
		}
		shoppingCart.clear();
	}

}
