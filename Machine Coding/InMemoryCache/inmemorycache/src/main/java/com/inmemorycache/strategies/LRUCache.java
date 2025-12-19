package com.inmemorycache.strategies;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import com.inmemorycache.CacheStrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class LRUCache<T, U> implements CacheStrategy<T, U> {
    private Integer capacity;
    private final DLL dll;
    private final Map<T, Node<T,U>> cache;
    private final PriorityQueue<Node<T,U>> expire;
    private ReentrantLock lock;
    private ScheduledExecutorService scheduler;

    @Override
    public U get(T key) {
        lock.lock();
        try {
            if (!cache.containsKey(key)) return null;
            Node<T,U> node = cache.get(key);
            if (isExpired(node)) {
                cache.remove(key);
                dll.removeNode(node);
                return null;
            }
            dll.addToFront(node);
            return node.value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void put(T key, U value, long ttl) {
        Node<T,U> node = cache.get(key);
        lock.lock();
        try {
            if (node != null) { // node already exists in the cache
                node.setValue(value);
                node.setExpiresAt(LocalDateTime.now().plusSeconds(ttl));
                dll.removeNode(node);
                dll.addToFront(node);
                expire.add(node);
            } else {
                if (cache.size() == capacity) {
                    Node<T,U> last = dll.removeLast();
                    cache.remove(last.key);
                }
                Node<T,U> newNode = new Node<>(key, value, LocalDateTime.now().plusSeconds(ttl));
                dll.addToFront(newNode);
                cache.put(key, newNode);
                expire.add(newNode);
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    private boolean isExpired(Node<T,U> node) {
        return !node.expiresAt.isAfter(LocalDateTime.now());
    }

    private void evictExpiredEntries() {
        lock.lock();
        try {
            while (!expire.isEmpty() && isExpired(expire.peek())) {
                Node<T,U> node = expire.poll();                
                Node<T,U> current = cache.get(node.key);
                if (current == node && isExpired(node)) {
                    cache.remove(node.key);
                    dll.removeNode(node);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void shutdownScheduler() {
        scheduler.shutdown();
    }

    public LRUCache(Integer capacity) {
        this.capacity = capacity;
        this.dll = new DLL();
        this.cache = new HashMap<>();
        this.expire = new PriorityQueue<>((node1, node2) -> node1.expiresAt.isBefore(node2.expiresAt) ? -1 : 1);
        this.lock = new ReentrantLock();
        this.scheduler = Executors.newScheduledThreadPool(1, 
            new ThreadFactory() {
                @Override
                public Thread newThread(Runnable r) {
                    Thread thread = new Thread(r);
                    thread.setName("ttl-expired-evictor");
                    thread.setDaemon(false);

                    return thread;
                }
            }
        );
        scheduler.scheduleAtFixedRate(this::evictExpiredEntries, 0, 5, TimeUnit.SECONDS);
    }

    private class DLL {
        final Node<T,U> head;
        final Node<T,U> tail;

        public DLL() {
            head = new Node<>(null, null, LocalDateTime.now());
            tail = new Node<>(null, null, LocalDateTime.now());
            head.next = tail;
            tail.prev = head;
        }

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
        }
    }

    @AllArgsConstructor @Setter @Getter @ToString(exclude={"next", "prev"})
    private static class Node<T, U> {
        T key;
        U value;
        Node<T,U> next;
        Node<T,U> prev;
        LocalDateTime expiresAt;
        
        public Node(T key, U value, LocalDateTime expiresAt) {
            this.key = key;
            this.value = value;
            this.expiresAt = expiresAt;
            this.next = null;
            this.prev = null; 
        }
    }
}