package platform.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import platform.entities.User;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;

    public static UserDto getInstance(User user){
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        return dto;
    }
}
