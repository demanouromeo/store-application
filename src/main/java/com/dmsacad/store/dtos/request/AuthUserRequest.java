package com.dmsacad.store.dtos.request;

import com.dmsacad.store.controllers.validation.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data //Summarizes @Getter, @Setter, @ToString and @ToHashcode
public class AuthUserRequest {

    @NotBlank(message = "The email is required")
    @Email(message = "Email is required") //Validates mail. The email must be valid email
    //@Lowercase(message = "Email must be in lowercase")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 25, message = "The password is between [6 and 25]")
    private String password;
}
