package com.practice.favorite.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Pagination {


  private Integer currentPage;

  private Integer lastPage;

  private Integer pageSize;

  private Long totalItemSize;

  private Integer currentItemSize;

}
