package kz.job4j.todo.service.impl;

import kz.job4j.todo.service.TimeZoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeZoneServiceImpl implements TimeZoneService {

    @Override
    public List<TimeZone> getTimeZones() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }
}
