package com.project.todolist.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectListCheckboxDto {
    private Long id;

    @JsonProperty("isChecked")
    private boolean isChecked;
}
