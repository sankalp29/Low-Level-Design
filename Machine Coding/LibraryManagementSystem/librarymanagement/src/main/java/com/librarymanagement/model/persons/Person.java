package com.librarymanagement.model.persons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public abstract class Person {
    private String name;
    private String email;
    private String phone;
}
