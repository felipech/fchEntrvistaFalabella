package com.falabella.entrevistaFalabella.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.falabella.entrevistaFalabella.model.Productos;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {

}
