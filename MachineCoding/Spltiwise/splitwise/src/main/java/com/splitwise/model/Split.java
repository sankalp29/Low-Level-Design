package com.splitwise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Split {
    private final String userId;
    private final Double amount;
}