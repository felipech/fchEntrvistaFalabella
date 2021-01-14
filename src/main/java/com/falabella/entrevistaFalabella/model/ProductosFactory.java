package com.falabella.entrevistaFalabella.model;

import org.springframework.stereotype.Component;

@Component
public class ProductosFactory {
	
	public static Productos getProducto(String nombre) {
		
		if(nombre.equals("Cobertura")) {
			return new ProductoCobertura();
		}else if(nombre.equals("Full cobertura")) {
			return new ProductoFullCobertura();
		}else if(nombre.equals("Full cobertura super duper")) {
			return new ProductoFullCoberturaSuperDuper();
		}else if(nombre.equals("Baja cobertura")) {
			return new ProductoBajaCobertura();
		}else if(nombre.equals("Mega cobertura")) {
			return new ProductoMegaCobertura();
		}else if(nombre.equals("Super avance")) {
			return new ProductoSuperAvance();
		}
		
		return null;
	}

}
