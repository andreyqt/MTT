package tutorial.mtt.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import tutorial.mtt.dto.DailyResult;

@Setter
@Getter
@Service
public class DailyResultCache {

    private DailyResult today;

}