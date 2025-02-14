package com.practice.favorite.product.service;

import static com.practice.favorite.error.ErrorCode.NOT_FOUND_PRODUCT;

import com.practice.favorite.error.GeneralException;
import com.practice.favorite.product.domain.Product;
import com.practice.favorite.product.domain.ProductRepository;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional(readOnly = true)
  public Product findById(Long id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new GeneralException(NOT_FOUND_PRODUCT));
  }

  @Transactional(readOnly = true)
  public Collection<Product> findByIdIn(Collection<Long> ids) {
    return productRepository.findByIdIn(ids);
  }
}
