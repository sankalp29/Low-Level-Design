# Event Counter (Time-based)

## Task
Design an `EventCounter` that tracks events over time and supports querying how many events occurred in a recent time window.

## Requirements
Implement a class `EventCounter` with the following methods:
`void recordEvent(int timestamp);`
`int getEventCount(int startTime, int endTime);`

## Behavior
• `recordEvent(timestamp)`
• Records an event that occurred at the given timestamp (in seconds).
• Timestamps are non-decreasing (events arrive in order).
• `getEventCount(startTime, endTime)`
• Returns the number of events whose timestamps lie in the inclusive range
[startTime, endTime].

⸻

## Constraints
• Time complexity should be efficient:
• recordEvent → O(1) or O(log N)
• getEventCount → O(log N) or O(log N + K)
• Assume the system runs in memory.
• No persistence required.
• Single-threaded unless specified otherwise.

## Example:
`recordEvent(10)`
`recordEvent(20)`
`recordEvent(20)`
`recordEvent(30)`

`getEventCount(10, 20)` → 3
`getEventCount(21, 30)` → 1