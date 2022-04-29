package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Bpi;


public interface BpiRepository extends JpaRepository<Bpi, String>{
	
}
