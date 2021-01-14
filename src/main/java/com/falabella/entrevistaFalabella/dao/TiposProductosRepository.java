package com.falabella.entrevistaFalabella.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.falabella.entrevistaFalabella.model.TiposProductos;

@Repository
public interface TiposProductosRepository extends JpaRepository<TiposProductos, Long> {
	
	@Query(value = "select nombre_producto as nombre from tipo_productos where nombre_producto like ?1", nativeQuery = true )
	String getNombreByNombre_Producto(String nombre);
}
