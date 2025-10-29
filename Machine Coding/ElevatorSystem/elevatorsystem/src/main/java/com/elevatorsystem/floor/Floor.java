package com.elevatorsystem.floor;

import com.elevatorsystem.building.Building;
import com.elevatorsystem.building.ElevatorsDisplay;

import lombok.Getter;

@Getter
public class Floor {
    private final Building building;
    private final int floorNumber;
    private final FloorPanel floorPanel;
    private final ElevatorsDisplay elevatorsDisplay;

    public Floor(int floorNumber, Building building, ElevatorsDisplay elevatorsDisplay) {
        this.floorNumber = floorNumber;
        this.building = building;
        this.floorPanel = new FloorPanel();
        this.elevatorsDisplay = elevatorsDisplay;
    }
}
