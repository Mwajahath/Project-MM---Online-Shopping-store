package com.massmutual.demo.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="orders")
public class Order {

	@Id
	@SequenceGenerator(name = "order_sequence",
		sequenceName = "order_sequence",
		allocationSize =1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
		generator = "order_sequence")
	@Column(name = "order_id")
	private Long orderID;
	
	@Column(name="order_date")
	private LocalDate orderDate;
	
	@Column(name="order_status")
	private String orderStatus;
	
	@ManyToOne(cascade =  CascadeType.MERGE)
	private User customer;
	
	@ManyToOne(cascade =  CascadeType.MERGE)
	private Product product;
	
	@ManyToOne(cascade =  CascadeType.MERGE)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Address address;
}
