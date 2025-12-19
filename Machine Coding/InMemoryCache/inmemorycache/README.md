### Priority Queue background eviction
A priority queue is useful for background eviction of expired cache entries when we can continue evicting till the first non-expired entry.

#### Pros:
1. Eviction process does not take O(N) time.
2. One remove() operation from the priority queue requires O(logN) time. K such removals will require O(KlogN).

#### How it works:

`nodeA` and `nodeB` are added to priority queue. `nodeA` has an expiryTime of 2, `nodeB` has an expiryTime of 3.
`nodeA` expiryTime is updated to 10. Priority queue only rebalances on offer() & remove(), not when the internal state of the objects change. So we need to perform the offer(nodeA) operation again after updating the expiryTime of `nodeA`.

PriorityQueue will look something like: [`nodeB`, `nodeA`, `nodeA`].
Now when the background eviction thread runs, `nodeB` will be at the top of the priority queue with the new expiryTime.

If the current time 6, it is > expiryTime of `nodeB`, so `nodeB` will be removed from the queue.

#### Solution:
Keep removing items from the priority queue till the current time < expiryTime of cache entries.
Time complexity : O(KlogN)

OR

On each background eviction thread run, simply delete entries from the cache map & subsequently from doubly linked list.
This takes O(N) time, but locks the entire cache APIs for the entire O(N) time.