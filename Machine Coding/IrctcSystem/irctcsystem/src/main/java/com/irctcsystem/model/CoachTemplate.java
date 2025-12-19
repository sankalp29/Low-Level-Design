package com.irctcsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class CoachTemplate {
    private final String coachId;
    private final Integer capacity;
}
