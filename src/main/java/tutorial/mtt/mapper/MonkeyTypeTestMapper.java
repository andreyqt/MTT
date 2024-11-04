package tutorial.mtt.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import tutorial.mtt.dto.MonkeyTypeTestDTO;
import tutorial.mtt.entity.MonkeyTypeTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MonkeyTypeTestMapper {
    @Mapping(source = "charStats", target = "chars", qualifiedByName = "mapCharStatsToChars")
    @Mapping(source = "timestamp", target = "dateTime", qualifiedByName = "mapTimestampToLocalDateTime")
    MonkeyTypeTestDTO toDto(MonkeyTypeTest monkeyTypeTest);

    @Named("mapCharStatsToChars")
    default String mapCharStatsToChars(List<String> charStats) {
        if (charStats == null || charStats.isEmpty()) {
            return null;
        }
        return charStats.getFirst();
    }

    @Named("mapTimestampToLocalDateTime")
    default LocalDateTime mapTimestampToLocalDateTime(Long timestamp) {
        if (timestamp == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
    }
}
