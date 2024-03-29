package com.yody.Server.repositories;

import com.yody.Server.entities.Product;
import com.yody.Server.entities.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {
    List<ProductVariant> findByProductId(Long id);
}
