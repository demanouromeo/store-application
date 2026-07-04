package com.dmsacad.store.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(min = 2, max = 30, message = "The category name is between [2 and 40]")
    private String name;
}
