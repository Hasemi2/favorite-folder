package com.practice.favorite.favorite.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "상품 찜 등록/해제 request")
@Getter
public class FavoriteProductRequest {

  @Nullable
  @Schema(description = "folder id", example = "1")
  private Long folderId;

  @NotNull
  @Schema(description = "product id", example = "1")
  private Long productId;
}
