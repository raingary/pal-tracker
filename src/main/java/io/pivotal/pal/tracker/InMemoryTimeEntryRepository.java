package io.pivotal.pal.tracker;

import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {


    Map<String,TimeEntry> timeEntryMap = new HashMap<>();

    static int count;

    public InMemoryTimeEntryRepository(){
        count = 0;
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        count++;

        TimeEntry newTimeEntry = new TimeEntry(count, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());

        timeEntryMap.put(String.valueOf(count),newTimeEntry);

        return newTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntryMap.get(String.valueOf(id));
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry existing = find(id);
        if(null == existing)
            return null;

        existing.setDate(timeEntry.getDate());
        existing.setHours(timeEntry.getHours());
        existing.setProjectId(timeEntry.getProjectId());
        existing.setUserId(timeEntry.getUserId());
        return existing;
    }

    @Override
    public void delete(long id) {
        timeEntryMap.remove(String.valueOf(id));
    }

    @Override
    public List<TimeEntry> list()    {
      return  timeEntryMap.values().stream().collect(Collectors.toList());
    }
}
