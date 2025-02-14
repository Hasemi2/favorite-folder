package com.practice.favorite.favorite.dto;

import static com.practice.favorite.error.ErrorMessage.BAD_REQUEST_FOLDER_IS_NOT_BLANK;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "서랍 생성 request")
@Getter
public class FavoriteFolderCreateRequest {

  @NotBlank(message = BAD_REQUEST_FOLDER_IS_NOT_BLANK)
  @Schema(description = "서랍명", example = "기본서랍")
  private String name;
}
