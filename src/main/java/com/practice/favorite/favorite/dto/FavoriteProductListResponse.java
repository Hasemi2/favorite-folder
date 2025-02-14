package com.practice.favorite.favorite.dto;

import com.practice.favorite.common.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "내 찜 서립의 찜 목록 조회 response")
@Builder
@Getter
public class FavoriteProductListResponse {

  @Schema(description = "서랍 id", example = "1")
  private Long folderId;

  @Schema(description = "서랍명", example = "기본서랍")
  private String folderName;

  @Schema(description = "상품리스트")
  private List<FavoriteProductResponse> products;

  @Schema(description = "상품 페이징정보")
  private Pagination pagination;

}
