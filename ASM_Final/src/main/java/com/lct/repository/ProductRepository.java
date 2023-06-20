package com.lct.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lct.entities.Product;




@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	
	
	@Query("SELECT o FROM Product o WHERE o.productName LIKE ?1")
	List<Product> findProductByName(String id);
	
	@Query("SELECT o FROM Product o WHERE o.price BETWEEN ?1 AND ?2")
	Page<Product> findByPrice(Integer minPrice, Integer maxPrice, Pageable pageable);
	

	@Query("SELECT o FROM Product o WHERE o.category.categoryid = ?1")
	List<Product> findByCategoryHome(String id);
	
	@Query("SELECT o FROM Product o WHERE o.productid=?1")
	Product findProductById(Long id);
}
