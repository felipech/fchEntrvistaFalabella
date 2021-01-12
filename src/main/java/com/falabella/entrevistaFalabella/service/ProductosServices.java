package com.falabella.entrevistaFalabella.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.falabella.entrevistaFalabella.dao.ProductosRepository;
import com.falabella.entrevistaFalabella.model.Productos;

import javassist.NotFoundException;

@Service
public class ProductosServices {

	private ProductosRepository productosRepo;
	
	
	public List<Productos> obtenerTodosLosProductos(){
		return productosRepo.findAll();
	}
	
	
	public Productos guardarProducto(Productos producto) {
		return productosRepo.save(producto);
	}


	public Productos getProducto(Long id) throws NotFoundException {
		// TODO Auto-generated method stub
		if(productosRepo.findById(id).isPresent()) {
			return productosRepo.findById(id).get();	
		}else {
			throw new NotFoundException("No se encontro el producto");
		} 
	}
	
}
