package com.lct.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lct.entities.Account;



@Repository
public interface AccountRepository extends JpaRepository<Account, String>{

	public Account findByUsername(String username);
	
	public Account findByEmailEquals(String email);

	   
	@Query("SELECT o FROM Account o WHERE o.fullname LIKE ?1")
	Page<Account> findByKeywords(String keywords, Pageable pageable);
	
	@Query("SELECT o FROM Account o")
	Page<Account> findAll(Pageable pageable);
	
	
}
