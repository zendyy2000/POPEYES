package com.lct.dto;

import com.lct.entities.Category;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Report {
	
	@Id
	private Category loai;
	private double doanhThu;
	private long soLuong;
}
