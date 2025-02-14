package com.practice.favorite.product.dto;

import com.practice.favorite.product.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductDto {

  private Long productId;
  private String name;
  private String thumbnailUrl;
  private Integer price;


  public ProductDto(Product product) {
    this.productId = product.getId();
    this.name = product.getName();
    this.thumbnailUrl = product.getThumbnailUrl();
    this.price = product.getPrice();
  }


}
