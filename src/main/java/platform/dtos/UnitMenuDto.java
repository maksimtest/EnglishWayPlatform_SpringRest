package platform.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "UnitMenuDto")
public class UnitMenuDto {
    private Long unitId;
    private Long menuItemId;
}
