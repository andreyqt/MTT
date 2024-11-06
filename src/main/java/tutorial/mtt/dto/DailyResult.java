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
    private double averageTime;
    private int numberOfTests;
    private LocalDate date;

    @Override
    public String toString() {
        return "[averageSpeed = " + averageSpeed + ", averageTime = " + averageTime + ", number of tests = " + numberOfTests + ", date = " + date + "]";
    }
}
