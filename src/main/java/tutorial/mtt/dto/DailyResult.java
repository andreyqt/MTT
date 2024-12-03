package tutorial.mtt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyResult {
    private double averageSpeed;
    private double totalTime;
    private int numberOfTests;
    private LocalDate date;
    private Double averageAcc;
}
