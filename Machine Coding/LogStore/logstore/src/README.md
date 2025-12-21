# Tradeoffs of choosing different data structures:

## 1. Simple HashMap
### Pros: put() operation HashMap takes O(1) time. So, log() API will take O(1) to complete.
### Cons: getLogs() API will take O(N) time because the logs in the HashMap are not sorted in order of timestamp

## 2. PriorityQueue
### Pros: The entries in the PriorityQueue will be sorted based on timestamp of the log. put() operation PriorityQueue takes O(logN) time. So, log() API will take O(logN) to complete.
### Cons: getLogs() API will take O(N) because PriorityQueue is a heap & a heap guarantees O(1) time to fetch the peek element & O(logN) time to pop the peek element. To get all the logs between [start, end] time, we will need to iterate over the entire PriorityQueue and select only those logs which lie between [start, end], giving an overall time complexity of O(NlogN)

## 3. TreeMap / TreeSet
### Pros: log() API will fire a put() operation in TreeMap which requires O(logN) time because TreeMap is a sorted map. It inserts elements in sorted order.
### Cons: getLogs() API will require O(logN + K), K = number of books having timestamp between [start, end].


## My choice: I will choose TreeSet<Log> because it provides a good balance between the log() & getLogs() API time complexity, being O(logN) & O(logN + K) respectively, as opposed to other choices where the time complexity is higher for getLogs() API.