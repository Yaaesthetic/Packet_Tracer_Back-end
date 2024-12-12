package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class DriverDTOMobileTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String username = "driverUser";
        String password = "securePassword";
        String firstName = "John";
        String lastName = "Doe";
        String email = "johndoe@example.com";
        String dateOfBirth = "1990-01-01";
        String licenseNumber = "LICENSE98765";
        String licensePlate = "PLATE1234";
        String brand = "Toyota";

        PacketDetailDTO packetDetail = new PacketDetailDTO(1001L, "CLIENT123", 2, 5, PacketStatus.IN_TRANSIT);
        BordoreauQRDTO bordoreauQR = new BordoreauQRDTO(
                1L,
                LocalDateTime.now(),
                "Driver CIN",
                101L,
                List.of(packetDetail),
                PacketStatus.IN_TRANSIT
        );
        Set<BordoreauQRDTO> bordoreauQRDTOS = Set.of(bordoreauQR);

        DriverDTOMobile driverDTOMobile = new DriverDTOMobile(
                username, password, firstName, lastName, email, dateOfBirth,
                licenseNumber, licensePlate, brand, bordoreauQRDTOS
        );

        // Assert
        assertThat(driverDTOMobile.getUsername()).isEqualTo(username);
        assertThat(driverDTOMobile.getPassword()).isEqualTo(password);
        assertThat(driverDTOMobile.getFirstName()).isEqualTo(firstName);
        assertThat(driverDTOMobile.getLastName()).isEqualTo(lastName);
        assertThat(driverDTOMobile.getEmail()).isEqualTo(email);
        assertThat(driverDTOMobile.getDateOfBirth()).isEqualTo(dateOfBirth);
        assertThat(driverDTOMobile.getLicenseNumber()).isEqualTo(licenseNumber);
        assertThat(driverDTOMobile.getLicensePlate()).isEqualTo(licensePlate);
        assertThat(driverDTOMobile.getBrand()).isEqualTo(brand);
        assertThat(driverDTOMobile.getBordoreauQRDTOS()).contains(bordoreauQR);
    }

    @Test
    void testSetters() {
        // Arrange
        DriverDTOMobile driverDTOMobile = new DriverDTOMobile();

        // Act
        driverDTOMobile.setUsername("driverUser");
        driverDTOMobile.setPassword("securePassword");
        driverDTOMobile.setFirstName("John");
        driverDTOMobile.setLastName("Doe");
        driverDTOMobile.setEmail("johndoe@example.com");
        driverDTOMobile.setDateOfBirth("1990-01-01");
        driverDTOMobile.setLicenseNumber("LICENSE98765");
        driverDTOMobile.setLicensePlate("PLATE1234");
        driverDTOMobile.setBrand("Toyota");

        PacketDetailDTO packetDetail = new PacketDetailDTO(1002L, "CLIENT456", 3, 6, PacketStatus.TRANSMITTED);
        BordoreauQRDTO bordoreauQR = new BordoreauQRDTO(
                2L,
                LocalDateTime.now(),
                "Driver CIN 2",
                102L,
                List.of(packetDetail),
                PacketStatus.TRANSMITTED
        );
        driverDTOMobile.setBordoreauQRDTOS(Set.of(bordoreauQR));

        // Assert
        assertThat(driverDTOMobile.getUsername()).isEqualTo("driverUser");
        assertThat(driverDTOMobile.getPassword()).isEqualTo("securePassword");
        assertThat(driverDTOMobile.getFirstName()).isEqualTo("John");
        assertThat(driverDTOMobile.getLastName()).isEqualTo("Doe");
        assertThat(driverDTOMobile.getEmail()).isEqualTo("johndoe@example.com");
        assertThat(driverDTOMobile.getDateOfBirth()).isEqualTo("1990-01-01");
        assertThat(driverDTOMobile.getLicenseNumber()).isEqualTo("LICENSE98765");
        assertThat(driverDTOMobile.getLicensePlate()).isEqualTo("PLATE1234");
        assertThat(driverDTOMobile.getBrand()).isEqualTo("Toyota");
        assertThat(driverDTOMobile.getBordoreauQRDTOS()).contains(bordoreauQR);
    }

    @Test
    void testToString() {
        // Arrange
        DriverDTOMobile driverDTOMobile = new DriverDTOMobile(
                "driverUser",
                "securePassword",
                "John",
                "Doe",
                "johndoe@example.com",
                "1990-01-01",
                "LICENSE98765",
                "PLATE1234",
                "Toyota",
                Set.of()
        );

        // Act
        String result = driverDTOMobile.toString();

        // Assert
        assertThat(result).contains("driverUser", "John", "Doe", "LICENSE98765", "Toyota");
    }
}
