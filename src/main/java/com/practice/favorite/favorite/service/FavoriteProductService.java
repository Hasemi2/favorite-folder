package com.practice.favorite.favorite.service;


import static com.practice.favorite.error.ErrorCode.CONFLICT_FAVORITE_PRODUCT;

import com.practice.favorite.error.GeneralException;
import com.practice.favorite.favorite.domain.FavoriteProduct;
import com.practice.favorite.favorite.domain.FavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteProductService {

  private final FavoriteProductRepository favoriteProductRepository;


  @Transactional(readOnly = true)
  public Page<FavoriteProduct> findAllByFolderIdAndMemberIdWithPaging(
      Long folderId,
      Long memberId,
      int page,
      int size) {
    return favoriteProductRepository.findAllByFolderIdAndMemberIdOrderByCreatedAtDesc(
        folderId,
        memberId,
        PageRequest.of(page, size));

  }

  @Transactional(readOnly = true)
  public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
    return favoriteProductRepository.existsByMemberIdAndProductId(memberId, productId);

  }

  @Transactional
  public FavoriteProduct save(Long folderId, Long productId, Long memberId) {
    favoriteProductRepository.findByMemberIdAndProductId(memberId, productId)
        .ifPresent(c -> {
          throw new GeneralException(CONFLICT_FAVORITE_PRODUCT);
        });

    return favoriteProductRepository.save(
        FavoriteProduct.builder()
            .memberId(memberId)
            .folderId(folderId)
            .productId(productId)
            .build()
    );

  }


  @Transactional
  public void delete(Long productId, Long memberId) {
    favoriteProductRepository.deleteByMemberIdAndProductId(memberId, productId);
  }

}