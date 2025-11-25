package com.ordermanagementsystem.model;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.exceptions.InvalidItemException;

import lombok.Data;

@Data
public class Inventory {
    private final Map<String, Item> itemIdToItem;
    private final Map<String, Integer> itemQuantity;
    private final Lock lock;

    public void addNewItem(Item item, Integer quantity) {
        itemIdToItem.put(item.getItemId(), item);
        itemQuantity.put(item.getItemId(), quantity);
    }

    public void addItemToInventory(String itemId, Integer quantity) throws InvalidItemException {
        if (!itemIdToItem.containsKey(itemId)) throw new InvalidItemException(ExceptionMessages.INVALID_ITEM_EXCEPTION);
        Item item = itemIdToItem.get(itemId);
        itemQuantity.put(item.getItemId(), itemQuantity.getOrDefault(item.getItemId(), 0) + quantity);
    }

    public void releaseInventory(String itemId) {
        if (!itemIdToItem.containsKey(itemId)) return;
        Item item = itemIdToItem.get(itemId);
        itemQuantity.put(item.getItemId(), itemQuantity.get(item.getItemId()) + 1);
    }

    public Integer getAvailableQuantity(String itemId) throws InvalidItemException {
        if (!itemIdToItem.containsKey(itemId)) throw new InvalidItemException(ExceptionMessages.INVALID_ITEM_EXCEPTION);
        Item item = itemIdToItem.get(itemId);
        return itemQuantity.get(item.getItemId());
    }

    public boolean reserveItems(List<Item> orderItems) {
        lock.lock();
        try {
            boolean unavailableItems = false;
            for (Item item : orderItems) {
                if (!itemQuantity.containsKey(item.getItemId()) || itemQuantity.get(item.getItemId()) <= 0) {
                    unavailableItems = true;
                }
            }
            
            if (unavailableItems) return false;
            
            for (Item item : orderItems) {
                itemQuantity.put(item.getItemId(), itemQuantity.get(item.getItemId()) - 1);
            }
        } finally {
            lock.unlock();
        }
        
        return true;
    }

    public void releaseItems(List<Item> orderItems) {
        lock.lock();
        try {
            for (Item item : orderItems) {
                itemQuantity.put(item.getItemId(), itemQuantity.get(item.getItemId()) + 1);
            }
        } finally {
            lock.unlock();
        }
    }

    public Inventory() {
        this.itemIdToItem = new ConcurrentHashMap<>();
        this.itemQuantity = new ConcurrentHashMap<>();
        this.lock = new ReentrantLock();
    }
}