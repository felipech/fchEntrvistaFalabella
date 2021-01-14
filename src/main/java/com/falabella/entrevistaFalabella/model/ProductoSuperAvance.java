package com.falabella.entrevistaFalabella.model;

import java.io.Serializable;

public class ProductoSuperAvance extends Productos implements Serializable {
	
	private static int tasaDeAumento = 1;
	private static int tasaDeBaja = 1;
	
	
	
	public ProductoSuperAvance() {
		super();
	}

	public ProductoSuperAvance(String nombre, int sellIn, int price) {
		super(nombre, sellIn, price);
	}



	@Override
	public Productos calculoReglas(Productos producto, int dia) {
		//Productos productoActualizado =  new Productos();
		
		double precioActualizado = 0;
		
		if(dia != 0 && producto.getPrice() >= 0) {
			if(producto.getSellIn() < 0) {
				tasaDeBaja = tasaDeBaja * 2;
			}
			if((producto.getPrice() - tasaDeBaja) >= 0) {
				precioActualizado = producto.getPrice() - tasaDeBaja;
			}else {
				precioActualizado = 0.0;
			}
			producto.setSellIn(producto.getSellIn() - tasaDeBaja);
			producto.setPrice(precioActualizado);
			return producto;
		}
		
		return producto;
	}

}
