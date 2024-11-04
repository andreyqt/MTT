package tutorial.mtt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JsonTestResponseList {
    private String message;
    private List<MonkeyTypeTest> data;
}
