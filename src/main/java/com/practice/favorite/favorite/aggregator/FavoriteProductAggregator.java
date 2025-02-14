package com.practice.favorite.favorite.aggregator;

import static com.practice.favorite.error.ErrorCode.CONFLICT_FAVORITE_PRODUCT;
import static com.practice.favorite.error.ErrorCode.NOT_FOUND_FAVORITE_FOLDER;

import com.practice.favorite.error.GeneralException;
import com.practice.favorite.favorite.domain.FavoriteFolder;
import com.practice.favorite.favorite.domain.FavoriteProduct;
import com.practice.favorite.favorite.dto.FavoriteProductRequest;
import com.practice.favorite.favorite.dto.FavoriteProductResponse;
import com.practice.favorite.favorite.service.FavoriteFolderService;
import com.practice.favorite.favorite.service.FavoriteProductService;
import com.practice.favorite.product.domain.Product;
import com.practice.favorite.product.service.ProductService;
import com.practice.favorite.security.CustomUserDetailsService;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteProductAggregator {

  private final FavoriteFolderService favoriteFolderService;
  private final FavoriteProductService favoriteProductService;
  private final ProductService productService;
  private final CustomUserDetailsService customUserDetailsService;


  @Transactional
  public FavoriteProductResponse save(FavoriteProductRequest request) {

    if (validateExistsFavoriteProduct(request.getProductId())) {
      throw new GeneralException(CONFLICT_FAVORITE_PRODUCT);
    }
    Optional<FavoriteFolder> optionalFavoriteFolder;
    //서랍 선택을 별도로 하지 않았을때 가장 최근에 생성한 서랍 하나를 가져와 저장합시다
    if (Objects.isNull(request.getFolderId())) {
      optionalFavoriteFolder = favoriteFolderService.findAllByMemberIdWithPaging(0, 1,
              getMemberId())
          .getContent()
          .stream()
          .findAny();
    } else {
      optionalFavoriteFolder = favoriteFolderService.findById(request.getFolderId())
          .filter(favoriteFolder -> customUserDetailsService.getCurrentMember().getId()
              .equals(favoriteFolder.getMemberId()));
    }
    return optionalFavoriteFolder
        .map(folder -> save(folder.getId(), request.getProductId()))
        .orElseThrow(() -> new GeneralException(NOT_FOUND_FAVORITE_FOLDER));
  }

  private FavoriteProductResponse save(Long folderId, Long productId) {
    Product product = productService.findById(productId);
    FavoriteProduct favoriteProduct = favoriteProductService.save(folderId, productId,
        getMemberId());

    return FavoriteProductResponse.builder()
        .id(favoriteProduct.getId())
        .folderId(favoriteProduct.getFolderId())
        .productId(favoriteProduct.getProductId())
        .name(product.getName())
        .thumbnailUrl(product.getThumbnailUrl())
        .price(product.getPrice())
        .build();
  }

  private boolean validateExistsFavoriteProduct(Long productId) {
    return favoriteProductService.existsByMemberIdAndProductId(getMemberId(), productId);
  }

  private Long getMemberId() {
    return customUserDetailsService.getCurrentMember().getId();
  }

}
