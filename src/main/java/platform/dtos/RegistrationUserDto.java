package platform.dtos;

import lombok.Data;

@Data
public class RegistrationUserDto {
    private String username;
    private String name;
    private String password;
    private String email;
    private String code;
}
