package com.practice.favorite.favorite.dto;

import com.practice.favorite.common.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "찜 서랍 목록 조회 response")
@Builder
@Getter
public class FavoriteFolderListResponse {

  private List<FavoriteFolderResponse> folders;
  private Pagination pagination;

}
