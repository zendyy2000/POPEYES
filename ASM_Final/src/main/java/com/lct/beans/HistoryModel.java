package com.lct.beans;

import java.io.Serializable;

import com.lct.entities.Order;
import com.lct.entities.OrderDetail;
import com.lct.entities.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoryModel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private Product product;
	private OrderDetail orderDetail;
	private Order order;
}
