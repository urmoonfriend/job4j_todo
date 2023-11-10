package kz.job4j.todo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TimeZoneService {
    List<String> getTimeZones();

    LocalDateTime convert(Optional<String> fromTimeZoneId, Optional<String> toTimeZoneId, LocalDateTime giveDate);
}
