package com.falabella.entrevistaFalabella.model;

public class ProductoMegaCobertura extends Productos {

	private static int tasaDeAumento = 1;
	private static int tasaDeBaja = 1;
	
	public ProductoMegaCobertura() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductoMegaCobertura(String nombre, int sellIn, int price) {
		super(nombre, sellIn, price);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Productos calculoReglas(Productos producto, int dia) {
		producto.setSellIn(producto.getSellIn() - 1);
		return producto;
	}

}
