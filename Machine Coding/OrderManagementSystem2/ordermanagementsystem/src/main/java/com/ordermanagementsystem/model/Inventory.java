package com.ordermanagementsystem.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.exceptions.InvalidItemException;

import lombok.Data;

@Data
public class Inventory {
    @Data
    private static class InventoryItem {
        private Item item;
        private AtomicInteger quantity;

        public InventoryItem(Item item, Integer quantity) {
            this.item = item;
            this.quantity = new AtomicInteger(quantity);
        }
    }
    
    private final Map<String, InventoryItem> itemById;
    private final Lock lock;

    public void addNewItem(Item item, Integer quantity) {
        itemById.put(item.getItemId(), new InventoryItem(item, quantity));
    }

    public void addItemToInventory(String itemId, Integer quantity) throws InvalidItemException {
        if (!itemById.containsKey(itemId)) throw new InvalidItemException(ExceptionMessages.INVALID_ITEM_EXCEPTION);
        Item item = itemById.get(itemId).getItem();
        itemById.get(item.getItemId()).getQuantity().addAndGet(quantity);
    }

    public void releaseInventory(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            if (!itemById.containsKey(item.getItemId())) continue;
            itemById.get(item.getItemId()).getQuantity().addAndGet(item.getQuantity());
        }
    }

    public Integer getAvailableQuantity(String itemId) throws InvalidItemException {
        if (!itemById.containsKey(itemId)) throw new InvalidItemException(ExceptionMessages.INVALID_ITEM_EXCEPTION);
        Item item = itemById.get(itemId).getItem();
        return itemById.get(item.getItemId()).getQuantity().get();
    }

    public boolean reserveItems(List<OrderItem> orderItems) {
        lock.lock();
        try {
            for (OrderItem item : orderItems) {
                if (!itemById.containsKey(item.getItemId()) || itemById.get(item.getItemId()).getQuantity().get() < item.getQuantity()) {
                    return false;
                }
            }
            
            for (OrderItem item : orderItems) {
                itemById.get(item.getItemId()).getQuantity().addAndGet(-item.getQuantity());
            }
        } finally {
            lock.unlock();
        }
        
        return true;
    }

    public Inventory() {
        this.itemById = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }
}