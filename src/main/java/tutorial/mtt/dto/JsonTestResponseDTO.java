package tutorial.mtt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonTestResponseDTO {
    private String message;
    private MonkeyTypeTestDTO dto;
}
