package com.practice.favorite.favorite.controller;


import com.practice.favorite.favorite.aggregator.FavoriteFolderAggregator;
import com.practice.favorite.favorite.dto.FavoriteFolderCreateRequest;
import com.practice.favorite.favorite.dto.FavoriteFolderListResponse;
import com.practice.favorite.favorite.dto.FavoriteFolderResponse;
import com.practice.favorite.favorite.dto.FavoriteProductListResponse;
import com.practice.favorite.favorite.service.FavoriteFolderService;
import com.practice.favorite.security.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("favorite-folders")
@Tag(name = "02. 찜 서랍 관리")
public class FavoriteFolderController {

  private final FavoriteFolderAggregator aggregator;
  private final FavoriteFolderService favoriteFolderService;
  private final CustomUserDetailsService customUserDetailsService;


  @Operation(summary = "찜 서랍 목록 조회")
  @GetMapping
  public FavoriteFolderListResponse list(
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size
  ) {
    return aggregator.list(page, size);
  }

  @Operation(summary = "찜 서랍 생성")
  @PostMapping
  public FavoriteFolderResponse create(
      @Valid @RequestBody FavoriteFolderCreateRequest request) {

    Long memberId = customUserDetailsService.getCurrentMember().getId();
    return new FavoriteFolderResponse(
        favoriteFolderService.save(request, memberId)
    );
  }

  @Operation(summary = "찜 서랍 삭제")
  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    Long memberId = customUserDetailsService.getCurrentMember().getId();
    favoriteFolderService.delete(id, memberId);
  }

  @Operation(summary = "찜 서랍 상품 목록 조회")
  @GetMapping("/{folderId}/products")
  public FavoriteProductListResponse productList(
      @PathVariable Long folderId,
      @RequestParam(required = false, defaultValue = "0") int page,
      @RequestParam(required = false, defaultValue = "10") int size
  ) {
    return aggregator.productList(folderId, page, size);
  }

}
