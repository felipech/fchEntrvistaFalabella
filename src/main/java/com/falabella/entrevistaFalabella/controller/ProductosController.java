package com.falabella.entrevistaFalabella.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falabella.entrevistaFalabella.Errores.ErrorCustom;
import com.falabella.entrevistaFalabella.model.Productos;
import com.falabella.entrevistaFalabella.model.TiposProductos;
import com.falabella.entrevistaFalabella.service.ProductosServices;

import javassist.NotFoundException;

@RestController
@RequestMapping("/productos")
public class ProductosController {

	@Autowired
	private ProductosServices productosServ;
	
	@GetMapping("/test")
	public String testController() {
		return "Test controller";
	}
	
	@GetMapping("/lista_Productos")
	public List<TiposProductos> listaProductosDisponibles(){
		return productosServ.obtenerTodosLosTiposProductosDisponibles();
	}
	
	@PostMapping("/guardar/tipoProducto")
	public HttpStatus guardarTipoProductoNuevo(@RequestBody TiposProductos tiposProductos) {
		return productosServ.guardarTiposProductos(tiposProductos);
	}
	
	@GetMapping("/all")
	public List<Productos> obtenerProductosVendidos(){
		return productosServ.obtenerTodosLosProductosVendidos();
	}
	
	@GetMapping("/obtener/{id}")
	public Productos obtenerProductoPorId(@PathVariable Long id) throws NotFoundException {
		return productosServ.getProducto(id);
	}
	
	@PostMapping("/vender")
	public Productos guardarProducto(@RequestBody Productos productos) throws ErrorCustom {
		
		return productosServ.guardarProducto(productos);
	}
	
	@GetMapping("/evaluateProducts/{days}")
	public List<String> simluacionProductos(@PathVariable int days) {
		return productosServ.simular(days);
	}
	
	@PutMapping("/actualizar/{id}")
	public Productos updateProducto(@PathVariable Long id ,@RequestBody Productos producto) throws NotFoundException {
		return productosServ.actualizarProducto(id, producto);
	}
	
}
