package com.example.packettracerbase.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DriverTest {

    @Test
    void testDriverBuilder() {
        Driver driver = Driver.builder()
                .cinDriver("67890")
                .username("driver_user")
                .password("securePassword")
                .firstName("Driver")
                .lastName("Test")
                .email("driver@test.com")
                .licenseNumber("L123456")
                .licensePlate("ABC123")
                .brand("Toyota")
                .isActive(true)
                .build();

        assertNotNull(driver);
        assertEquals("67890", driver.getCinDriver());
        assertEquals(Role.Driver, driver.getRole());
        assertEquals("L123456", driver.getLicenseNumber());
        assertEquals("Toyota", driver.getBrand());
    }

    @Test
    void testEqualsAndHashCode() {
        Driver driver1 = Driver.builder().cinDriver("67890").build();
        Driver driver2 = Driver.builder().cinDriver("67890").build();

        assertEquals(driver1, driver2);
        assertEquals(driver1.hashCode(), driver2.hashCode());
    }

    @Test
    void testSuperBuilder() {
        Driver driver = Driver.builder()
                .cinDriver("123")
                .licenseNumber("L12345")
                .licensePlate("ABC123")
                .brand("Toyota")
                .username("driver1")
                .password("password")
                .isActive(true)
                .build();

        assertNotNull(driver);
        assertEquals("123", driver.getCinDriver());
        assertEquals("L12345", driver.getLicenseNumber());
    }

    @Test
    void testEqualsAndHashCode1() {
        Driver driver1 = Driver.builder().cinDriver("123").build();
        Driver driver2 = Driver.builder().cinDriver("123").build();

        assertEquals(driver1, driver2);
        assertEquals(driver1.hashCode(), driver2.hashCode());
    }

    @Test
    void testSuperBuilder2() {
        // Using Builder pattern for creating the Driver object
        Driver driver = Driver.builder()
                .cinDriver("123")
                .licenseNumber("L12345")
                .licensePlate("ABC123")
                .brand("Toyota")
                .username("driver1")
                .password("password")
                .isActive(true)
                .build();

        assertNotNull(driver);
        assertEquals("123", driver.getCinDriver());
        assertEquals("L12345", driver.getLicenseNumber());
        assertEquals("ABC123", driver.getLicensePlate());
        assertEquals("Toyota", driver.getBrand());
    }

    @Test
    void testEqualsAndHashCode2() {
        Driver driver1 = Driver.builder().cinDriver("123").build();
        Driver driver2 = Driver.builder().cinDriver("123").build();

        assertEquals(driver1, driver2);
        assertEquals(driver1.hashCode(), driver2.hashCode());
    }
}
