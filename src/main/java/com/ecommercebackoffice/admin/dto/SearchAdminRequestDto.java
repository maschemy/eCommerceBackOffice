package com.ecommercebackoffice.admin.dto;


import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.admin.enums.AdminStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;

@Getter
public class SearchAdminRequestDto {
    private String name;
    private String email;
    @Min(1)
    private Integer page = 1; // 기본값 1 설정
    @Max(100)
    private Integer size = 10;// 기본값 10 설정
    private String sortBy = "createdAt"; //정렬기준 : 가입일
    private String direction = "desc"; //내림차순
    private AdminRole role;
    private AdminStatus status;
}
