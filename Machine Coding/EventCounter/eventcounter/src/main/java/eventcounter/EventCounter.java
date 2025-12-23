package eventcounter;

import java.util.ArrayList;
import java.util.List;

public class EventCounter {
    private final List<Event> events;

    /**
     * Record an event with given timestamp
     * @param timestamp
     * Time complexity : O(1)
     */
    public void recordEvent(int timestamp) {
        if (timestamp < 0) throw new IllegalArgumentException("Invalid timestamp record attempt.");
        if (events.isEmpty() || events.get(events.size() - 1).getTimestamp() != timestamp) {
            int prefixEventCount = events.isEmpty() ? 0 : events.get(events.size() - 1).getPrefixEventSum();
            Event event = new Event(timestamp, prefixEventCount + 1);        
            events.add(event);
        } else {
            int size = events.size();
            Event event = events.get(size - 1);
            event.incrementEventsCount();
        }
    }

    /**
     * Count events that occurred in the [startTime, endTime] window
     * @param startTime
     * @param endTime
     * @return eventCount
     * Time complexity : O(logN)
     * K = events in the window
     */
    public int getEventCount(int startTime, int endTime) {
        if (startTime < 0 || endTime < 0) throw new IllegalArgumentException("Invalid timestamp passed.");
        if (startTime > endTime) throw new IllegalArgumentException("startTime cannot be > endTime");
        if (events.isEmpty()) return 0;

        Integer start = getFirstEventGreaterThan(startTime);
        Integer end = lastEventSmallerThan(endTime);
        
        if (start == -1 || end == -1) return 0;

        Integer endPrefixSum = events.get(end).getPrefixEventSum();
        Integer startPrefixSum = start - 1 >= 0 ? events.get(start - 1).getPrefixEventSum() : 0;
        return endPrefixSum - startPrefixSum;
    }

    /**
     * Find first event which occurs at >= startTime
     * @param startTime
     * @return targetIndex
     * Time complexity : O(logN)
     */
    private Integer getFirstEventGreaterThan(int startTime) {
        int left = 0, right = events.size() - 1;
        int targetIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events.get(mid).getTimestamp() < startTime) left = mid + 1;
            else {
                targetIndex = mid;
                right = mid - 1;
            }
        }

        return targetIndex;
    }

    /**
     * Find last event which occurs at <= endTime
     * @param endTime
     * @return targetIndex
     * Time complexity : O(logN)
     */
    private Integer lastEventSmallerThan(int endTime) {
        int left = 0, right = events.size() - 1;
        int targetIndex = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (events.get(mid).getTimestamp() > endTime) right = mid - 1;
            else {
                targetIndex = mid;
                left = mid + 1;
            }
        }

        return targetIndex;
    }

    public EventCounter() {
        this.events = new ArrayList<>();
    }
}