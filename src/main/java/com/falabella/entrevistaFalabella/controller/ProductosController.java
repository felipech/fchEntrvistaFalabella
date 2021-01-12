package com.falabella.entrevistaFalabella.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falabella.entrevistaFalabella.model.Productos;
import com.falabella.entrevistaFalabella.service.ProductosServices;

import javassist.NotFoundException;

@RestController
@RequestMapping("/productos")
public class ProductosController {

	private ProductosServices productosServ;
	
	@GetMapping("/test")
	public String testController() {
		
		return "Test constroller";
	}
	
	@GetMapping("/all")
	public List<Productos> obtenerProductos(){
		return null;
	}
	
	@GetMapping("/obtener/{id}")
	public Productos obtenerProductoPorId(@PathVariable Long id) throws NotFoundException {
		return productosServ.getProducto(id);
	}
	
	@PostMapping("/agregar")
	public Productos guardarProducto(@RequestBody Productos productos) {
		
		return productosServ.guardarProducto(productos);
	}
	
	
}
