package kz.job4j.todo.service.impl;

import kz.job4j.todo.service.TimeZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeZoneServiceImpl implements TimeZoneService {

    @Override
    public List<String> getTimeZones() {
        return Arrays.stream(TimeZone.getAvailableIDs()).toList();
    }

    @Override
    public LocalDateTime convert(Optional<String> fromTimeZoneId, Optional<String> toTimeZoneId, LocalDateTime giveDate) {
        ZoneId defaultTimeZoneFrom = ZoneId.of(TimeZone.getDefault().getID());
        ZoneId defaultTimeZoneTo = ZoneId.of(TimeZone.getDefault().getID());
        if (fromTimeZoneId.isPresent()) {
            defaultTimeZoneFrom = ZoneId.of(fromTimeZoneId.get());
        }
        if (toTimeZoneId.isPresent()) {
            defaultTimeZoneTo = ZoneId.of(toTimeZoneId.get());
        }
        return giveDate.atZone(defaultTimeZoneFrom)
                .withZoneSameInstant(defaultTimeZoneTo)
                .toLocalDateTime();
    }
}
