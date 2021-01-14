package com.falabella.entrevistaFalabella.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "productos")
public class Productos implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Long id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "sellIn")
	private int sellIn;
	@Column(name = "price" , scale = 2 )
	private double price;

	
	public Productos(String nombre, int sellIn, int price) {
		super();
		this.nombre = nombre;
		this.sellIn = sellIn;
		this.price = price;

	}

	public Productos() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getSellIn() {
		return sellIn;
	}

	public void setSellIn(int sellIn) {
		this.sellIn = sellIn;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	//metodo basico calculo reglas
	public Productos calculoReglas(Productos producto, int dia) {
		return producto;
	}




}
