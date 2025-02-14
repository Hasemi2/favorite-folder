package com.practice.favorite.favorite.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "찜 상품 response")
@Builder
@Getter
public class FavoriteProductResponse {

  @Schema(description = "pk", example = "1")
  private Long id;

  @Schema(description = "찜 서랍 id", example = "1")
  private Long  folderId;

  @Schema(description = "상품 id", example = "1")
  private Long  productId;

  @Schema(description = "상품명", example = "후드티")
  private String name;

  @Schema(description = "썸네일 이미지 url", example = "https://image.com/products/thumbnail/product_8.jpeg")
  private String thumbnailUrl;

  @Schema(description = "가격" , example = "3000")
  private Integer price;

}
