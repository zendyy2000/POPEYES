package com.lct.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lct.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("SELECT o FROM Order o WHERE o.account.username = ?1")
	Page<Order> findOrderByAccount(Pageable pageable, String username);
//	
//	@Query("Select o from Order o where o.createDate between ?1 and ?2")
//	Page<Order> findOrderByDate(Date firstDate, Date lastDate, Pageable pageable);
	@Query("SELECT Max(o.id) FROM Order o")
	int getNewOrder();
	
	
}
