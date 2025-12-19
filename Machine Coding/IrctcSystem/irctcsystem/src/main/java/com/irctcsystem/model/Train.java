package com.irctcsystem.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Train {
    private final String trainId;
    private final String name;
    private final List<Station> route;
    private final List<CoachTemplate> coachTemplate;
}
