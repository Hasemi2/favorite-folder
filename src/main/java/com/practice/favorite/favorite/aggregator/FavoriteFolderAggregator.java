package com.practice.favorite.favorite.aggregator;


import com.practice.favorite.common.Pagination;
import com.practice.favorite.favorite.domain.FavoriteFolder;
import com.practice.favorite.favorite.domain.FavoriteProduct;
import com.practice.favorite.favorite.dto.FavoriteFolderListResponse;
import com.practice.favorite.favorite.dto.FavoriteFolderResponse;
import com.practice.favorite.favorite.dto.FavoriteProductListResponse;
import com.practice.favorite.favorite.dto.FavoriteProductResponse;
import com.practice.favorite.favorite.service.FavoriteFolderService;
import com.practice.favorite.favorite.service.FavoriteProductService;
import com.practice.favorite.product.dto.ProductDto;
import com.practice.favorite.product.service.ProductService;
import com.practice.favorite.security.CustomUserDetailsService;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FavoriteFolderAggregator {

  private final CustomUserDetailsService customUserDetailsService;
  private final FavoriteFolderService favoriteFolderService;
  private final FavoriteProductService favoriteProductService;
  private final ProductService productService;


  public FavoriteFolderListResponse list(int page, int size) {
    Page<FavoriteFolder> favoriteFolders =
        favoriteFolderService.findAllByMemberIdWithPaging(page, size, getMemberId());
    return FavoriteFolderListResponse.builder()
        .folders(favoriteFolders.getContent()
            .stream()
            .map(FavoriteFolderResponse::new)
            .collect(Collectors.toList()))
        .pagination(Pagination.builder()
            .currentPage(favoriteFolders.getNumber())
            .lastPage(favoriteFolders.getTotalPages())
            .pageSize(favoriteFolders.getSize())
            .totalItemSize(favoriteFolders.getTotalElements())
            .currentItemSize(favoriteFolders.getNumberOfElements())
            .build())
        .build();
  }

  public FavoriteProductListResponse productList(Long folderId, int page, int size) {
    FavoriteFolder favoriteFolder = favoriteFolderService.findByMemberIdAndId(folderId,
        getMemberId());

    Page<FavoriteProduct> favoriteProducts = favoriteProductService.findAllByFolderIdAndMemberIdWithPaging(
        folderId, getMemberId(), page, size);

    Map<Long, ProductDto> productMap = productService.findByIdIn(favoriteProducts.getContent()
            .stream()
            .map(FavoriteProduct::getProductId)
            .collect(Collectors.toSet())
        )
        .stream()
        .filter(o -> Objects.isNull(o.getDeletedAt()))
        .map(ProductDto::new)
        .collect(Collectors.toMap(ProductDto::getProductId, Function.identity(), (o1, o2) -> o1));

    ProductDto deletedProduct = ProductDto.builder()
        .name("삭제된 상품")
        .thumbnailUrl("삭제된 상품")
        .price(0)
        .build();

    List<FavoriteProductResponse> favoriteProductResponses =
        favoriteProducts.getContent()
            .stream()
            .map(favoriteProduct -> {
              ProductDto productDto = productMap.getOrDefault(favoriteProduct.getProductId(),
                  deletedProduct);

              return FavoriteProductResponse.builder()
                  .id(favoriteProduct.getId())
                  .folderId(favoriteProduct.getFolderId())
                  .productId(favoriteProduct.getProductId())
                  .name(productDto.getName())
                  .thumbnailUrl(productDto.getThumbnailUrl())
                  .price(productDto.getPrice())
                  .build();
            })
            .collect(Collectors.toList());

    return FavoriteProductListResponse.builder()
        .folderId(favoriteFolder.getId())
        .folderName(favoriteFolder.getName())
        .products(favoriteProductResponses)
        .pagination(Pagination.builder()
            .currentPage(favoriteProducts.getNumber())
            .lastPage(favoriteProducts.getTotalPages())
            .pageSize(favoriteProducts.getSize())
            .totalItemSize(favoriteProducts.getTotalElements())
            .currentItemSize(favoriteProducts.getNumberOfElements())
            .build())
        .build();
  }

  private Long getMemberId() {
    return customUserDetailsService.getCurrentMember().getId();
  }
}
