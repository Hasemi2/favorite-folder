package com.practice.favorite.favorite.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {

  void deleteAllByFolderIdAndMemberId(Long folderId, Long memberId);

  Optional<FavoriteProduct> findByMemberIdAndProductId(Long memberId, Long productId);

  void deleteByMemberIdAndProductId(Long folderId, Long productId);

  Page<FavoriteProduct> findAllByFolderIdAndMemberIdOrderByCreatedAtDesc(Long folderId, Long memberId, Pageable pageable);

  boolean existsByMemberIdAndProductId(Long memberId, Long productId);
}
