package com.practice.favorite.favorite.dto;

import com.practice.favorite.favorite.domain.FavoriteFolder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Schema(description = "찜 서랍 조회 response")
@Getter
public class FavoriteFolderResponse {

  @Schema(description = "pk", example = "1")
  private final Long id;

  @Schema(description = "서랍명", example = "기본서랍")
  private final String name;

  @Schema(description = "생성일시")
  private final LocalDateTime createdAt;

  @Schema(description = "수정일시")
  private final LocalDateTime updatedAt;

  public FavoriteFolderResponse(FavoriteFolder favoriteFolder) {
    this.id = favoriteFolder.getId();
    this.name = favoriteFolder.getName();
    this.createdAt = favoriteFolder.getCreatedAt();
    this.updatedAt = favoriteFolder.getUpdatedAt();

  }
}
