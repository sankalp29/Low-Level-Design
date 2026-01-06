### **1. Why wait() must be in a loop?**

**Expected answer:**
```
Because of spurious wake-ups and signal races:

1. Spurious wake-ups: The JVM can wake threads without signal() being called
2. Signal race: Between wake-up and reacquiring the lock, another thread 
   might change the condition
3. Multiple waiters: If using signalAll(), multiple threads wake up but 
   only one can proceed

Example:
Thread A: wait() → woken → reacquires lock → checks condition again
Thread B: (already consumed the item before A reacquired lock)
Thread A: condition still false → must wait again

Using 'if' would cause Thread A to proceed incorrectly!
```

---

### **2. Difference between notify() and notifyAll()?**

**Expected answer:**
```
notify():
- Wakes ONE arbitrary waiting thread
- More efficient (less context switching)
- Can cause threads to wait indefinitely if wrong thread is woken

notifyAll():
- Wakes ALL waiting threads
- All threads recheck condition, only one proceeds
- Safer, prevents missed signals
- Slightly less efficient due to "thundering herd"

For this queue:
- signal() works because only one thread can proceed after each state change
- signalAll() is safer in complex scenarios with multiple conditions
```

---

### **3. How to avoid missed signals?**

**Expected answer:**
```
Always follow this pattern:

1. Hold the lock when calling signal()/await()
2. Signal AFTER changing state, before releasing lock
3. Use while loop for condition checks
4. Never signal without holding the lock

Missed signal example (WRONG):
Thread A: checks condition → releases lock
Thread B: changes state → signals
Thread A: calls await() → MISSES the signal! Deadlock!

lock.lock()
try {
    // Change state
    queue.add(value);
    // Signal BEFORE releasing lock
    notEmpty.signal();
} finally {
    lock.unlock();
}
```

---

### **4. What happens if multiple producers wake up together?**

**Expected answer:**
```
If using signalAll():
1. All waiting producers wake from await()
2. They compete to reacquire the lock
3. One wins, reacquires lock
4. Checks while condition - if satisfied, proceeds
5. Others reacquire lock one by one
6. Each checks condition - if not satisfied, await() again

This is why while loop is critical!

With signal():
- Only one producer wakes up
- Less contention, but must ensure correct thread is woken
```

---

### **5. How does this differ from java.util.concurrent.BlockingQueue?**

**Expected answer:**
```
java.util.concurrent.BlockingQueue:
- Interface with multiple implementations (ArrayBlockingQueue, LinkedBlockingQueue)
- Highly optimized, lock-free options available
- Rich API (offer, poll with timeout, drainTo, etc.)
- Production-ready with extensive testing

Our implementation:
- Educational/interview purposes
- Simpler, easier to reason about
- Uses explicit locks (not lock-free)
- Limited API
- Good for understanding fundamentals

In production, always use java.util.concurrent.BlockingQueue!