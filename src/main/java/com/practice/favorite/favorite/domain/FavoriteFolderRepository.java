package com.practice.favorite.favorite.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteFolderRepository extends JpaRepository<FavoriteFolder, Long> {

  Optional<FavoriteFolder> findByMemberIdAndName(Long memberId, String name);

  Optional<FavoriteFolder> findByMemberIdAndId(Long memberId, Long id);

  Page<FavoriteFolder> findAllByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);
}
