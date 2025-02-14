package com.practice.favorite.favorite.controller;

import com.practice.favorite.favorite.aggregator.FavoriteProductAggregator;
import com.practice.favorite.favorite.dto.FavoriteProductRequest;
import com.practice.favorite.favorite.dto.FavoriteProductResponse;
import com.practice.favorite.favorite.service.FavoriteProductService;
import com.practice.favorite.security.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("favorite-products")
@Tag(name = "03. 찜 상품 관리")
public class FavoriteProductController {

  private final FavoriteProductAggregator aggregator;
  private final FavoriteProductService service;
  private final CustomUserDetailsService customUserDetailsService;

  @Operation(summary = "찜 상품 등록")
  @PostMapping
  public FavoriteProductResponse save(@Valid @RequestBody FavoriteProductRequest request) {
    return aggregator.save(request);
  }

  @Operation(summary = "찜 상품 삭제")
  @DeleteMapping("/{productId}")
  public void delete(@PathVariable Long productId) {
    service.delete(productId, customUserDetailsService.getCurrentMember().getId());
  }
}
