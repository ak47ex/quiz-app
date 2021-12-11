package ru.pyatkinmv.controllers.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterDto {
    @JsonProperty(value = "username", required = true)
    private String username;
    @JsonProperty(value = "password", required = true)
    private String password;
    @JsonProperty(value = "token", required = false)
    private String token;

    public boolean isValid() {
        return username != null && !username.isBlank() && password != null && !password.isBlank();
    }
}
