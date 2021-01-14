package com.falabella.entrevistaFalabella;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.falabella.entrevistaFalabella.dao.ProductosRepository;
import com.falabella.entrevistaFalabella.dao.TiposProductosRepository;
import com.falabella.entrevistaFalabella.model.Productos;
import com.falabella.entrevistaFalabella.model.TiposProductos;


@Component
@ConditionalOnProperty(name = "data.init-bd", havingValue = "true")
public class IncializadorData implements CommandLineRunner{
	@Autowired
	private ProductosRepository prod;
	
	@Autowired
	private TiposProductosRepository tipos;

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		TiposProductos tipo1 = tipos.save(new TiposProductos("Cobertura"));
		TiposProductos tipo2 = tipos.save(new TiposProductos("Full cobertura"));
		TiposProductos tipo3 = tipos.save(new TiposProductos("Baja cobertura"));
		TiposProductos tipo4 = tipos.save(new TiposProductos("Mega cobertura"));
		TiposProductos tipo5 = tipos.save(new TiposProductos("Super avance"));
		TiposProductos tipo6 = tipos.save(new TiposProductos("Full cobertura super duper"));
		
		Productos prod1 = prod.save(new Productos("Cobertura full",21,11));
		Productos prod2 = prod.save(new Productos("Mega cobertura",25,11));
		Productos prod3 = prod.save(new Productos("Mega cobertura",12,2));
		Productos prod4 = prod.save(new Productos("Full cobertura super duper",15,20));
		Productos prod5 = prod.save(new Productos("Super avance",2,5));
		
		
		
		
		System.out.println(" iniciando datos ");
	}
	
}
