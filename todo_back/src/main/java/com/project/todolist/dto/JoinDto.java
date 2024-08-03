package com.project.todolist.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JoinDto {
    @NotNull
    @Size(max = 12, message = "최대 12자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[가-힣a-z]*$", message = "한글, 영어 소문자만 가능합니다.")
    private String memberId;

    @NotNull
    @Size(max = 12, message = "최대 12자까지 입력할 수 있습니다.")
    @Pattern(regexp = "^[가-힣a-z]*$", message = "한글, 영어 소문자만 가능합니다.")
    private String name;

    @NotNull
    @Size(min = 8, max = 12, message = "8자 이상 12자 이하로 입력해야 합니다.")
    @Pattern(regexp = "^(?=.*[가-힣a-z])[가-힣a-z!@#&()\\-\\[{}\\]:;',?/*~$^+=<>]{8,12}$", message = "한글, 영어 소문자를 포함하고, 특수문자 선택 가능합니다.")
    private String password;

    @NotNull
    @Email
    private String email;
}
