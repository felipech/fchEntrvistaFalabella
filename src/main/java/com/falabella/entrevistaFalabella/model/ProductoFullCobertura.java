package com.falabella.entrevistaFalabella.model;

import java.io.Serializable;

public class ProductoFullCobertura extends Productos implements Serializable{
	
	private static int tasaDeAumento = 1;
	private static int tasaDeBaja = 1;
	
	
	public ProductoFullCobertura() {
		super();
		// TODO Auto-generated constructor stub
	}


	public ProductoFullCobertura(String nombre, int sellIn, int price) {
		super(nombre, sellIn, price);
		// TODO Auto-generated constructor stub
	}


	@Override
	public Productos calculoReglas(Productos producto, int dia) {
		double precioActualizado = 0.0;
		
		//dia 0 dia inicial
		if(dia != 0 && producto.getPrice() >= 0 && producto.getPrice() <= 100 && producto.getSellIn() > 0) {
			
			if(producto.getSellIn() <= 10 && producto.getSellIn() > 5) {
				tasaDeAumento = tasaDeAumento + 1;
			}else if(producto.getSellIn() <= 5 && producto.getSellIn() > 0) {
				tasaDeAumento = tasaDeAumento + 2;
			}
			precioActualizado = producto.getPrice() + tasaDeAumento;
			if(precioActualizado < 0) {
				precioActualizado = 0;
			}
			producto.setPrice(precioActualizado);
			producto.setSellIn(producto.getSellIn() - 1);
		}else if(producto.getSellIn() == 0 && dia != 0) {
			producto.setPrice(0.0);
		}
		
		return producto;
	}

}
