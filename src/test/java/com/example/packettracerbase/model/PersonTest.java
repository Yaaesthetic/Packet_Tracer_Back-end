package com.example.packettracerbase.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void testPersonFields() {
        Person person = new Person() {
            // Abstract class, so provide an anonymous implementation for testing
        };

        person.setUsername("test_user");
        person.setPassword("securePassword");
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setEmail("test@example.com");
        person.setDateOfBirth(LocalDate.of(1990, 1, 1));
        person.setActive(true);
        person.setRole(Role.Admin);

        assertEquals("test_user", person.getUsername());
        assertEquals("securePassword", person.getPassword());
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("test@example.com", person.getEmail());
        assertEquals(LocalDate.of(1990, 1, 1), person.getDateOfBirth());
        assertTrue(person.isActive());
        assertEquals(Role.Admin, person.getRole());
    }

}
