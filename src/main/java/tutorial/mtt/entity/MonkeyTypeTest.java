package tutorial.mtt.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonkeyTypeTest {
    private double wpm;
    private double acc;
    private String mode;
    private String mode2;
    private List<Integer> charStats;
    private Long timestamp;
    private double testDuration;
}