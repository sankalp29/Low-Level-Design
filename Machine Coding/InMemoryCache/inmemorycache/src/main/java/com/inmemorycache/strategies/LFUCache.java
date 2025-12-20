package com.inmemorycache.strategies;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.inmemorycache.CacheStrategy;

public class LFUCache<T, U> implements CacheStrategy<T, U> {
    private final Integer capacity;
    private final Map<Integer, DLL<T,U>> frequencyLists;
    private final Map<T, Node<T,U>> keys;
    private Integer minFrequency;
    private Integer size;
    private final ReentrantLock lock;

    @Override
    public U get(T key) {
        lock.lock();
        try {
            Node<T,U> node = keys.get(key);
            if (node == null) return null;
            if (isExpired(node)) {
                removeNode(node);
                return null;
            }
            Integer frequency = node.frequency;
            node.incrementFrequency();
            updateFrequencyList(node, frequency);
            updateMinFrequency(frequency);
            return node.value;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void put(T key, U value, long ttl) {
        lock.lock();
        try {
            Node<T,U> node = keys.get(key);
            if (node != null) { // Node already exists in the cache
                node.value = value;
                Integer frequency = node.frequency;
                node.incrementFrequency();
                node.setNewTTL(ttl);
                updateFrequencyList(node, frequency);
                updateMinFrequency(frequency);
            } else {
                if (size.equals(capacity)) { // Cache has reached it's size limit
                    DLL<T,U> list = frequencyLists.get(minFrequency);
                    if (list != null && !list.isEmpty()) {
                        Node<T,U> lfuNode = list.removeLast(); // Evict the LFU node
                        keys.remove(lfuNode.key);
                        size--;
                    }
                }
                Node<T,U> newNode = new Node<>(key, value, ttl);
                keys.put(key, newNode);
                size++;
                minFrequency = 1;
                frequencyLists.computeIfAbsent(minFrequency, dll -> new DLL<>());
                frequencyLists.get(minFrequency).addToFront(newNode);
            }
        }
        finally {
            lock.unlock();
        }
    }

    public Integer getCacheSize() {
        lock.lock();
        try {
            return size;    
        } finally {
            lock.unlock();
        }
    }

    private void removeNode(Node<T,U> node) {
        DLL<T,U> list = frequencyLists.get(node.frequency);
        if (list == null || list.isEmpty()) {
            frequencyLists.remove(node.frequency);
        } else list.removeNode(node);
        keys.remove(node.key);
        size--;
    }

    private void updateFrequencyList(Node<T,U> node, Integer frequency) {
        frequencyLists.get(frequency).removeNode(node);
        frequencyLists.computeIfAbsent(frequency + 1, dll -> new DLL<>());
        frequencyLists.get(frequency + 1).addToFront(node);
    }

    private void updateMinFrequency(Integer frequency) {
        DLL<T,U> list = frequencyLists.get(frequency);
        if (list == null) return;
        if (minFrequency.equals(frequency) && list.isEmpty()) minFrequency++;
        if (list.isEmpty()) frequencyLists.remove(frequency);
    }

    private boolean isExpired(Node<T,U> node) {
        return !node.expiryTime.isAfter(LocalDateTime.now());
    }

    public LFUCache(final Integer capacity) {
        this.capacity = capacity;
        this.frequencyLists = new HashMap<>();
        this.keys = new HashMap<>();
        this.minFrequency = 1;
        this.size = 0;
        this.lock = new ReentrantLock();
    }

    private static class DLL<T,U> {
        Node<T,U> head;
        Node<T,U> tail;

        public void addToFront(Node<T,U> node) {
            node.next = head.next;
            head.next.prev = node;
            node.prev = head;
            head.next = node;
        }

        public Node<T,U> removeLast() {
            Node<T,U> last = tail.prev;
            removeNode(last);
            return last;
        }

        public void removeNode(Node<T,U> node) {
            Node<T,U> prev = node.prev;
            Node<T,U> next = node.next;

            prev.next = next;
            next.prev = prev;

            node.next = null;
            node.prev = null;
        }

        public boolean isEmpty() {
            return head.next == tail && tail.prev == head;
        }

        public DLL() {
            this.head = new Node<>(null, null, Integer.MAX_VALUE);
            this.tail = new Node<>(null, null, Integer.MAX_VALUE);
            head.next = tail;
            tail.prev = head;
        }
    }

    private static class Node<T,U> {
        T key;
        U value;
        Node<T,U> next;
        Node<T,U> prev;
        Integer frequency;
        LocalDateTime expiryTime;

        public void incrementFrequency() {
            frequency++;
        }

        public void setNewTTL(long ttl) {
            this.expiryTime = LocalDateTime.now().plusSeconds(ttl);
        }

        public Node(T key, U value, long ttl) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
            this.frequency = 1;
            this.expiryTime = LocalDateTime.now().plusSeconds(ttl);
        }
    }
}