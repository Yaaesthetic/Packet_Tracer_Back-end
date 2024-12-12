package com.example.packettracerbase.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    void testAdminBuilder() {
        Admin admin = Admin.builder()
                .cinAdmin("12345")
                .username("admin_user")
                .password("securePassword")
                .firstName("Admin")
                .lastName("Test")
                .email("admin@test.com")
                .isActive(true)
                .build();

        assertNotNull(admin);
        assertEquals("12345", admin.getCinAdmin());
        assertEquals(Role.Admin, admin.getRole());
        assertEquals("admin_user", admin.getUsername());
        assertTrue(admin.isActive());
    }

    @Test
    void testEqualsAndHashCode() {
        Admin admin1 = Admin.builder().cinAdmin("12345").build();
        Admin admin2 = Admin.builder().cinAdmin("12345").build();

        assertEquals(admin1, admin2);
        assertEquals(admin1.hashCode(), admin2.hashCode());
    }

    @Test
    void testSuperBuilder() {
        // Using Builder pattern for creating the Admin object
        Admin admin = Admin.builder()
                .cinAdmin("789")
                .username("admin1")
                .password("password")
                .isActive(true)
                .build();

        assertNotNull(admin);
        assertEquals("789", admin.getCinAdmin());
        assertTrue(admin.isActive());
        assertEquals("admin1", admin.getUsername());
    }

    @Test
    void testEqualsAndHashCode2() {
        Admin admin1 = Admin.builder().cinAdmin("789").build();
        Admin admin2 = Admin.builder().cinAdmin("789").build();

        assertEquals(admin1, admin2);
        assertEquals(admin1.hashCode(), admin2.hashCode());
    }
}
