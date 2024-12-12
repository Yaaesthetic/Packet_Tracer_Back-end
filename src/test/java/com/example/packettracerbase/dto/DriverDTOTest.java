package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DriverDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String cinDriver = "CIN12345";
        String licenseNumber = "LICENSE98765";
        String licensePlate = "PLATE1234";
        String brand = "Toyota";
        Set<Bordoreau> bordoreausDriver = new HashSet<>();
        Bordoreau bordoreau = Bordoreau.builder()
                .bordoreau(1L)
                .date(LocalDateTime.now())
                .status(PacketStatus.IN_TRANSIT)
                .build();
        bordoreausDriver.add(bordoreau);

        Driver driver = Driver.builder()
                .cinDriver(cinDriver)
                .licenseNumber(licenseNumber)
                .licensePlate(licensePlate)
                .brand(brand)
                .bordoreausDriver(bordoreausDriver)
                .username("driverUser")
                .password("password123")
                .isActive(true)
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .build();

        // Assert
        assertThat(driver.getCinDriver()).isEqualTo(cinDriver);
        assertThat(driver.getLicenseNumber()).isEqualTo(licenseNumber);
        assertThat(driver.getLicensePlate()).isEqualTo(licensePlate);
        assertThat(driver.getBrand()).isEqualTo(brand);
        assertThat(driver.getBordoreausDriver()).hasSize(1).contains(bordoreau);
        assertThat(driver.getUsername()).isEqualTo("driverUser");
        assertThat(driver.getPassword()).isEqualTo("password123");
        assertThat(driver.isActive()).isTrue();
        assertThat(driver.getFirstName()).isEqualTo("John");
        assertThat(driver.getLastName()).isEqualTo("Doe");
        assertThat(driver.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(driver.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
    }

    @Test
    void testSetters() {
        // Arrange
        Driver driver = new Driver();
        String cinDriver = "CIN54321";
        String licenseNumber = "LICENSE12345";
        String licensePlate = "PLATE4321";
        String brand = "Honda";

        // Act
        driver.setCinDriver(cinDriver);
        driver.setLicenseNumber(licenseNumber);
        driver.setLicensePlate(licensePlate);
        driver.setBrand(brand);

        // Assert
        assertThat(driver.getCinDriver()).isEqualTo(cinDriver);
        assertThat(driver.getLicenseNumber()).isEqualTo(licenseNumber);
        assertThat(driver.getLicensePlate()).isEqualTo(licensePlate);
        assertThat(driver.getBrand()).isEqualTo(brand);
    }

    @Test
    void testBordoreausDriverRelationship() {
        // Arrange
        Driver driver = new Driver();
        Bordoreau bordoreau1 = Bordoreau.builder()
                .bordoreau(1L)
                .date(LocalDateTime.now())
                .status(PacketStatus.INITIALIZED)
                .build();

        Bordoreau bordoreau2 = Bordoreau.builder()
                .bordoreau(2L)
                .date(LocalDateTime.now())
                .status(PacketStatus.DONE)
                .build();

        Set<Bordoreau> bordoreaus = new HashSet<>();
        bordoreaus.add(bordoreau1);
        bordoreaus.add(bordoreau2);

        // Act
        driver.setBordoreausDriver(bordoreaus);

        // Assert
        assertThat(driver.getBordoreausDriver()).hasSize(2).contains(bordoreau1, bordoreau2);
    }

    @Test
    void testHashCode() {
        // Arrange
        Driver driver1 = Driver.builder().cinDriver("CIN12345").build();
        Driver driver2 = Driver.builder().cinDriver("CIN12345").build();
        Driver driver3 = Driver.builder().cinDriver("CIN54321").build();

        // Act & Assert
        assertThat(driver1.hashCode()).isEqualTo(driver2.hashCode());
        assertThat(driver1.hashCode()).isNotEqualTo(driver3.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        Driver driver = Driver.builder()
                .cinDriver("CIN12345")
                .brand("Toyota")
                .licenseNumber("LICENSE98765")
                .build();

        // Act
        String result = driver.toString();

        // Assert
        assertThat(result).contains("CIN12345", "Toyota", "LICENSE98765");
    }
}
