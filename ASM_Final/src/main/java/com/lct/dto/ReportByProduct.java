package com.lct.dto;

import java.sql.Date;

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
public class ReportByProduct {
	
	@Id
	private String productName;
	private double doanhThu;
}
