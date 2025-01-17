package tutorial.mtt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonkeyTypeTestDTO {
    private double wpm;
    private double acc;
    private String mode;
    private String mode2;
    private int chars;
    private LocalDateTime dateTime;
    private double testDuration;
}