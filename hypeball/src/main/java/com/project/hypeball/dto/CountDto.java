package com.project.hypeball.dto;

/**
 * NativeQuery 사용
 * : Getter만 사용하는 인터페이스
 */
public interface CountDto {

    Long getStoreId();
    long getCount();
    String getName();
}
