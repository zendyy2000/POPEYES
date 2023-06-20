package com.lct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lct.beans.HistoryModel;
import com.lct.dto.Report;
import com.lct.dto.ReportByProduct;
import com.lct.entities.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

	@Query("SELECT o FROM OrderDetail o WHERE o.order.id = ?1")
	Page<OrderDetail> findByOrder(Long id, Pageable pageable);
	
	List<OrderDetail> findOrdersDetailByOrderId(long orderId);


//	
//	@Query("SELECT new Top10(o.product, sum(o.quantity)) FROM OrderDetail o GROUP BY o.product ORDER BY sum(o.quantity) DESC")
//	Page<Top10> getTop10(Pageable pegeable);

//	
//	@Query("Select new RevenueReport(d.product.category, sum(d.price*d.quantity), sum(d.quantity))"
//			+ " from OrderDetail d Group By d.product.category")
//	List<RevenueReport> getRevenueByCategory();

//	@Query("SELECT new Report(od.order.account.username, od.order.account.fullname, od.product.productName, od.order.date, SUM(od.price)) "
//			+ "FROM OrderDetail od "
//			+ "GROUP BY od.order.account.username, od.order.account.fullname, od.product.productName, od.order.date")
//	List<Report> calculateRevenueByUser();

	@Query("SELECT new Report(d.product.category, sum(d.price*d.quantity),"
			+ " sum(d.quantity)) FROM OrderDetail d GROUP BY d.product.category")
	List<Report> revenueByCategory();

//	@Query("SELECT new ReportByProduct(p.productName , SUM(od.price * od.quantity))"
//			+ " FROM OrderDetail od"
//			+ " JOIN od.product p"
//			+ " JOIN od.order o"
//			+ " WHERE o.orderStatus = 'Completed'"
//			+ " GROUP BY p.productName"
//			+ " ORDER BY revenue DESC")
//	List<ReportByProduct> revenueByProduct();
	/*
	 * @Query(value = "SELECT new HistoryModel(p, od, o ) FROM OrderDetail od " +
	 * "INNER JOIN od.order o INNER JOIN od.product p WHERE o.orderStatus = 0 AND o.account.userid =:username"
	 * ) public List<HistoryModel> getHistory(@Param("username") String username);
	 */
}
