package com.falabella.entrevistaFalabella.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.falabella.entrevistaFalabella.Errores.ErrorCustom;
import com.falabella.entrevistaFalabella.dao.ProductosRepository;
import com.falabella.entrevistaFalabella.dao.TiposProductosRepository;
import com.falabella.entrevistaFalabella.model.ProductoCobertura;
import com.falabella.entrevistaFalabella.model.Productos;
import com.falabella.entrevistaFalabella.model.ProductosFactory;
import com.falabella.entrevistaFalabella.model.TiposProductos;

import javassist.NotFoundException;

@Service
public class ProductosServices {

	@Autowired
	private ProductosRepository productosRepo;
	
	@Autowired
	private TiposProductosRepository tiposRepo;
	
	
	public List<Productos> obtenerTodosLosProductosVendidos(){
		return productosRepo.findAll();
	}
	
	
	public List<TiposProductos> obtenerTodosLosTiposProductosDisponibles(){
		return tiposRepo.findAll();
	}
	
	public HttpStatus guardarTiposProductos(TiposProductos tipoProducto) {
		//validamos si existe el nombre del producto antes de guardar
		
		if(tiposRepo.getNombreByNombre_Producto(tipoProducto.getNombreProducto()) != null) {
			if(tiposRepo.getNombreByNombre_Producto(tipoProducto.getNombreProducto()).equals(tipoProducto.getNombreProducto())) {
				
				return HttpStatus.NOT_ACCEPTABLE;
			}
			
		}else {
			if(tiposRepo.save(tipoProducto) != null) {
				return HttpStatus.CREATED;
			}
		}
		
		return HttpStatus.NOT_ACCEPTABLE;
	}
	
	public Productos guardarProducto(Productos producto) throws ErrorCustom {
		if(producto.getNombre().equals("Mega cobertura") && producto.getPrice() != 80) {
			throw new ErrorCustom("El precio para 'Mega Cobertura' tiene que ser 80");
		}else if(tiposRepo.getNombreByNombre_Producto(producto.getNombre()).equals(producto.getNombre())) {
			return productosRepo.save(producto);	
		}
		return null;
	}
	
	
	public Productos actualizarProducto(Long id,Productos productoActualizado) throws NotFoundException {
		return productosRepo.findById(id).map(producto -> {
			producto.setNombre(productoActualizado.getNombre());
			producto.setPrice(productoActualizado.getPrice());
			producto.setSellIn(productoActualizado.getSellIn());
			productosRepo.save(producto);
			return producto;
		}).orElseThrow(() -> new NotFoundException("Viajero no encontrado"));
	}
	
	public String borrarProducto(Long id) throws NotFoundException {
		return productosRepo.findById(id).map(producto -> {
			productosRepo.delete(producto);
			return "Producto Borrado";
		}).orElseThrow(() -> new NotFoundException("Viajero no encontrado"));
	}

	public Productos getProducto(Long id) throws NotFoundException {
		if(productosRepo.findById(id).isPresent()) {
			return productosRepo.findById(id).get();	
		}else {
			throw new NotFoundException("No se encontro el producto");
		} 
	}
	
	/*
	todos los productos tienen un valor de sellIn, que indica la cantidad de dias que tenemos para vender ese producto.
	todos los productos tienen un valor price que indica el costo del producto.
	Al final del dia, el sistema debe disminuir los valores de price y sellIn para cada producto.
	Una vez que la fecha de venta ha pasado, sellIn < 0 , los precios de cada producto, se degradan el doble de rapido.
	El precio de un producto, nunca es negativo.
	el producto "Full cobertura" incrementa su precio a medida que pasa el tiempo.
	el precio de un producto nunca supera los 100.
	el producto "Mega cobertura", nunca vence para vender y nunca disminuye su precio.
	el producto "Full cobertura Super duper", tal como el "Full Cobertura", incrementa su precio a medida que se acerca su fecha de vencimiento:
	El precio se incrementa en 2 cuando quedan 10 dias o menos y se incrementa en 3, cuando quedan 5 dias o menos.
	el precio disminuye a 0 cuando se vence el tiempo de venta.
	El producto "Super avance" dismuniye su precio el doble de rapido que un producto normal.
	el producto "Mega cobertura" tiene un precio fijo de 180.*/
	
	public List<String> simular(int days) {
	
		List<Productos> listaCompletaProductos = productosRepo.findAll();
		
		List<Productos> logSimulacion = new ArrayList<Productos>();
		List<String> logsimulacionResumido = new ArrayList<>();
		String log = "";
		
		for(int i = 0; i < days; i++) {
			for(Productos p: listaCompletaProductos) {
				Productos productoActual = ProductosFactory.getProducto(p.getNombre());
				if(productoActual != null) {
					productoActual = calculosProductosPorCobertura(i, p);
					logSimulacion.add(productoActual);
					log = "Dia  " + i + " producto " + productoActual.getNombre() + " price " + productoActual.getPrice() + " sellIn " + productoActual.getSellIn();
					logsimulacionResumido.add(log);
				}
				
				
					
			}
		}
		
		return logsimulacionResumido;
	}
	
	
	
	public Productos calculosProductosPorCobertura(int diaActual, Productos producto) {
		//se asume que si no hay definicion en cuanto a la disminucion entonces se disminuye en 1
		//necesito obtener el sell in y el price para ver en cuanto queda
		//days seria el ultimo dia
		Productos prodd = ProductosFactory.getProducto(producto.getNombre());
		return prodd.calculoReglas(producto, diaActual);

	}
	

	
	
}
