package com.falabella.entrevistaFalabella.model;

public class ProductoBajaCobertura extends Productos  {
	
	private static int tasaDeAumento = 1;
	private static int tasaDeBaja = 1;
	
	
	
	public ProductoBajaCobertura() {
		super();
		// TODO Auto-generated constructor stub
	}



	public ProductoBajaCobertura(String nombre, int sellIn, int price) {
		super(nombre, sellIn, price);
		// TODO Auto-generated constructor stub
	}



	@Override
	public Productos calculoReglas(Productos producto, int dia) {
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
