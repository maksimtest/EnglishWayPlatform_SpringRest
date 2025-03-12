package platform.dtos;

import lombok.Data;

@Data
public class RememberPasswordDto {
    private String password;
    private String code;
}
