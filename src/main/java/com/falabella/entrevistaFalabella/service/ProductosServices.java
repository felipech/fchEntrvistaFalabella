package com.falabella.entrevistaFalabella.service;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.falabella.entrevistaFalabella.Errores.ErrorCustom;
import com.falabella.entrevistaFalabella.dao.ProductosRepository;
import com.falabella.entrevistaFalabella.model.Productos;

import javassist.NotFoundException;

@Service
public class ProductosServices {

	@Autowired
	private ProductosRepository productosRepo;
	
	@Autowired
	private Productos productoActual;
	
	private static int tasaDeAumento = 1;
	
	private static int tasaDeBaja = 1;
	
	public List<Productos> obtenerTodosLosProductos(){
		return productosRepo.findAll();
	}
	
	
	public Productos guardarProducto(Productos producto) throws ErrorCustom {
		if(producto.getNombre().equals("Mega cobertura") && producto.getPrice() != 80) {
			throw new ErrorCustom("El precio para 'Mega Cobertura' tiene que ser 80");
		}
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
				
				productoActual  = calculosProductosPorCobertura(i, p);
				logSimulacion.add(productoActual);
				log = "Dia  " + i + " producto " + productoActual.getNombre() + " price " + productoActual.getPrice() + " sellIn " + productoActual.getSellIn();
				logsimulacionResumido.add(log);
					
			}
		}
		
		return logsimulacionResumido;
	}
	
	
	
	public Productos calculosProductosPorCobertura(int diaActual, Productos producto) {
		//se asume que si no hay definicion en cuanto a la disminucion entonces se disminuye en 1
		//necesito obtener el sell in y el price para ver en cuanto queda
		//days seria el ultimo dia
		
		if(producto.getNombre().equals("Cobertura")) {
			return calculoProductoCobertura(producto, diaActual);
		}else if(producto.getNombre().equals("Full cobertura")) {
			return calculoProductoFullCobertura(producto, diaActual);
		}else if(producto.getNombre().equals("Baja cobertura")) {
			return calculoProductoCobertura(producto, diaActual);
		}else if(producto.getNombre().equals("Mega cobertura")) {
			return calculoProductoMegaCobertura(producto, diaActual);
		}else if(producto.getNombre().equals("Full cobertura super duper")) {
			return calculoProductoFullCobertura(producto, diaActual);
		}else if(producto.getNombre().equals("Super avance")) {
			
		}
		
		
		
		return null;
	}
	
	/*
	 *todos los productos pueden tener distintas reglas por eso se separan cada uno con su propia funcion 
	 *para cada regla se podria tener configuraciones en tablas o archivos, y la logica quiza cambia por eso
	 *no se junta todo en una solo funcion con multiples condiciones
	 *Funcion para producto "Cobertura" o "Baja cobertura"
	 * */
	
	public Productos calculoProductoCobertura(Productos producto, int dia) {
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
	
	/*
	 * Funcion para producto FullCobertura o Full cobertura Super duper
	 * */
	public Productos calculoProductoFullCobertura(Productos producto, int dia) {
		
		
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
	
	/*
	 * Producto Super avance
	 * */
	public Productos calculoProductoSuperAvance(Productos producto, int dia) {
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
	
	
	public Productos calculoProductoMegaCobertura(Productos producto, int dia) {
		
		producto.setSellIn(producto.getSellIn() - 1);
		return producto;
		
	}
	
	
}
