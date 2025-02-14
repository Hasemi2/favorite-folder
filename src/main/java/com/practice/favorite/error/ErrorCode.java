package com.practice.favorite.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

  //400 BAD REQUEST
  BAD_REQUEST_SIGN_IN(HttpStatus.BAD_REQUEST, ErrorMessage.BAD_REQUEST_SIGN_IN),

  //401 UNAUTHORIZED
  UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, ErrorMessage.UNAUTHORIZED_USER),

  //404 NOT_FOUND
  NOT_FOUND_FAVORITE_FOLDER(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_FAVORITE_FOLDER),
  NOT_FOUND_PRODUCT(HttpStatus.NOT_FOUND, ErrorMessage.NOT_FOUND_PRODUCT),

  //409 CONFLICT
  CONFLICT_SIGN_UP(HttpStatus.CONFLICT, ErrorMessage.CONFLICT_SIGN_UP),
  CONFLICT_FAVORITE_FOLDER_NAME(HttpStatus.CONFLICT, ErrorMessage.CONFLICT_FAVORITE_FOLDER_NAME),
  CONFLICT_FAVORITE_PRODUCT(HttpStatus.CONFLICT, ErrorMessage.CONFLICT_FAVORITE_PRODUCT);

  private final HttpStatus httpStatus;
  private final String message;


}
