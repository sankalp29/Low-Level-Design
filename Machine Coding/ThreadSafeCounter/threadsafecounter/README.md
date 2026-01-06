## Synchronized Counter
1. Synchronized counter provides lower throughput for low-moderate contention because it performs actual locking.
2. For high contention, synchronized performs better.
3. Synchronized works on the implicit object monitor.

## Atomic Counter
1. Atomic counter provides higher throughput for low-moderate contention because it uses compare-and-set functionality internally.
2. For high contention, synchronized performs better since compare-and-set will repeatedly fail leading to multiple retries thus giving higher latency and more CPU cycles.
3. AtomicCounter is a separate instance. Does not work on the counter's implicit monitor.


### Why is count++ not thread-safe?
Because count++ is not an atomic operation. count++ is made up up 3 steps:
1. getting the current value of count
2. Adding 1 to that value
3. Setting the updated value to count.

Race conditions occur because 2 threads executing count++ simultaneously may interweave with each other betwwen these operations.


### Would volatile alone be enough?
No, because volatile only makes sure that the counter value is updated in the main memory and each thread does not create it's copy of the counter in the thread's cache. Volatile does not solve for atomicity. Race conditions will still occur even if counter value is read from main memory if the operations are not synchronized.


### How would this scale under high contention?
Under high contention, `synchronized` keyword would work better than AtomicInteger because AtomicInteger performs a compare-and-swap operation internally. Compare operation will fail more often under high contention because many threads are trying to perform the compare-and-swap operation simultaneously. More retries will be required which reduces throughput.

### Is get() required to be synchronized?
If we synchronize get(), then that hinders parallel increment() & decrement() operations execution, but makes sure get() returns the most recent value of the counter. 
Even if we do not synchronize get(), it does not return a stale value because simply getting the counter value is an atomic operation in itself. So get() need not be synchronized.