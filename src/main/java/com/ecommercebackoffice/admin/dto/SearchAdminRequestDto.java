package com.ecommercebackoffice.admin.dto;


import com.ecommercebackoffice.admin.enums.AdminRole;
import com.ecommercebackoffice.admin.enums.AdminStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SearchAdminRequestDto {
    @Size(max = 50)
    private String name;
    @Email(message = "이메일 형식에 맞게 입력해주세요")
    private String email;
    @Min(1)
    private Integer page = 1; // 기본값 1 설정
    @Max(100)
    private Integer size = 10;// 기본값 10 설정
    private String sortBy = "createdAt"; //정렬기준 가입일 기본값
    private String direction = "desc"; //내림차순 기본값
    private AdminRole role;
    private AdminStatus status;
}
