package com.dmsacad.store.dtos.response;

import com.dmsacad.store.entities.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private int id;
    private String name;
    //private List<Product> products;//no needed now
}
