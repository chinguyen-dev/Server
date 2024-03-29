package com.yody.Server.repositories;

import com.yody.Server.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long cateId);

    Page<Product> findByCategoryId(Pageable pageable, Long cateId);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findAll(Specification specification, Pageable pageable);

    Page<Product> findByNameContaining(Pageable pageable, String name);
}
