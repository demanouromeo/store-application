package com.dmsacad.store.dtos.request;

import lombok.Data;

@Data
public class UpdateUserRequest {
    String name;
    String email;
}
