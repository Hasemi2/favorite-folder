package com.practice.favorite.favorite.service;

import static com.practice.favorite.error.ErrorCode.CONFLICT_FAVORITE_FOLDER_NAME;
import static com.practice.favorite.error.ErrorCode.NOT_FOUND_FAVORITE_FOLDER;

import com.practice.favorite.error.GeneralException;
import com.practice.favorite.favorite.domain.FavoriteFolder;
import com.practice.favorite.favorite.domain.FavoriteFolderRepository;
import com.practice.favorite.favorite.domain.FavoriteProductRepository;
import com.practice.favorite.favorite.dto.FavoriteFolderCreateRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FavoriteFolderService {

  private final FavoriteFolderRepository favoriteFolderRepository;
  private final FavoriteProductRepository favoriteProductRepository;

  @Transactional(readOnly = true)
  public Page<FavoriteFolder> findAllByMemberIdWithPaging(int page, int size, Long memberId) {
    return favoriteFolderRepository.findAllByMemberIdOrderByCreatedAtDesc(memberId,
        PageRequest.of(page, size));

  }

    @Transactional(readOnly = true)
  public FavoriteFolder findByMemberIdAndId(Long folderId, Long memberId) {
    return favoriteFolderRepository.findByMemberIdAndId(memberId, folderId)
        .orElseThrow(() -> new GeneralException(NOT_FOUND_FAVORITE_FOLDER));
  }

  @Transactional(readOnly = true)
  public Optional<FavoriteFolder> findById(Long folderId) {
    return favoriteFolderRepository.findById(folderId);
  }

  @Transactional
  public FavoriteFolder save(FavoriteFolderCreateRequest request, Long memberId) {
    favoriteFolderRepository.findByMemberIdAndName(memberId, request.getName())
        .ifPresent(c -> {
          throw new GeneralException(CONFLICT_FAVORITE_FOLDER_NAME);
        });

    return favoriteFolderRepository.save(
        FavoriteFolder.builder()
            .memberId(memberId)
            .name(request.getName())
            .build()
    );
  }

  @Transactional
  public void delete(Long id, Long memberId) {
    FavoriteFolder folder = favoriteFolderRepository.findByMemberIdAndId(memberId, id)
        .orElseThrow(() -> new GeneralException(NOT_FOUND_FAVORITE_FOLDER));

    favoriteProductRepository.deleteAllByFolderIdAndMemberId(folder.getId(), memberId);
    favoriteFolderRepository.deleteById(id);

  }

}
