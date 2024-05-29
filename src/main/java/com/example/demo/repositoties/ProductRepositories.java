package com.example.demo.repositoties;

import com.example.demo.moduls.enums.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepositories extends JpaRepository<Product, Long> {
    List<Product> findByTitle(String title);
}
