package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.DriverDTOMobile;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.repository.DriverRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DriverServiceImplTest {

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private DriverServiceImpl driverService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllDrivers() {
        // Arrange
        List<Driver> mockDrivers = Arrays.asList(
                Driver.builder()
                        .cinDriver("D123")
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .dateOfBirth(LocalDate.of(1980, 1, 1))
                        .build(),
                Driver.builder()
                        .cinDriver("D124")
                        .firstName("Jane")
                        .lastName("Doe")
                        .email("jane.doe@example.com")
                        .dateOfBirth(LocalDate.of(1985, 2, 2))
                        .build());
        when(driverRepository.findAll()).thenReturn(mockDrivers);

        // Act
        List<Driver> drivers = driverService.getAllDrivers();

        // Assert
        assertEquals(2, drivers.size());
        assertEquals("John", drivers.get(0).getFirstName());
        verify(driverRepository, times(1)).findAll();
    }

    @Test
    void testGetDriverById() {
        // Arrange
        Driver mockDriver = Driver.builder()
                .cinDriver("D123")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .dateOfBirth(LocalDate.of(1980, 1, 1))
                .build();
        when(driverRepository.findById("D123")).thenReturn(Optional.of(mockDriver));

        // Act
        Optional<Driver> driver = driverService.getDriverById("D123");

        // Assert
        assertTrue(driver.isPresent());
        assertEquals("John", driver.get().getFirstName());
        verify(driverRepository, times(1)).findById("D123");
    }

    @Test
    void testCreateDriver() {
        // Arrange
        Driver driver = Driver.builder()
                .cinDriver("D125")
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@example.com")
                .dateOfBirth(LocalDate.of(1990, 3, 3))
                .build();
        when(driverRepository.save(driver)).thenReturn(driver);

        // Act
        Driver savedDriver = driverService.createDriver(driver);

        // Assert
        assertEquals("Alice", savedDriver.getFirstName());
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void testUpdateDriver_Success() {
        // Arrange
        Driver existingDriver = Driver.builder()
                .cinDriver("D126")
                .firstName("Bob")
                .lastName("Brown")
                .email("bob.brown@example.com")
                .dateOfBirth(LocalDate.of(1985, 4, 4))
                .build();

        Driver updatedDetails = Driver.builder()
                .cinDriver("D126")
                .firstName("Robert")
                .lastName("Brown")
                .email("robert.brown@example.com")
                .dateOfBirth(LocalDate.of(1985, 4, 4))
                .build();

        when(driverRepository.findById("D126")).thenReturn(Optional.of(existingDriver));
        when(driverRepository.save(existingDriver)).thenReturn(updatedDetails);

        // Act
        Driver updatedDriver = driverService.updateDriver("D126", updatedDetails);

        // Assert
        assertEquals("Robert", updatedDriver.getFirstName());
        verify(driverRepository, times(1)).findById("D126");
        verify(driverRepository, times(1)).save(existingDriver);
    }

    @Test
    void testUpdateDriver_NotFound() {
        // Arrange
        when(driverRepository.findById("D127")).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                driverService.updateDriver("D127", Driver.builder().build())
        );
        assertEquals("Driver not found with id: D127", exception.getMessage());
        verify(driverRepository, times(1)).findById("D127");
    }

    @Test
    void testDeleteDriver_Success() {
        // Arrange
        when(driverRepository.existsById("D128")).thenReturn(true);

        // Act
        driverService.deleteDriver("D128");

        // Assert
        verify(driverRepository, times(1)).existsById("D128");
        verify(driverRepository, times(1)).deleteById("D128");
    }

    @Test
    void testDeleteDriver_NotFound() {
        // Arrange
        when(driverRepository.existsById("D129")).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                driverService.deleteDriver("D129")
        );
        assertEquals("Driver not found with id: D129", exception.getMessage());
        verify(driverRepository, times(1)).existsById("D129");
    }

    @Test
    void testConvertToDriverDTOMobile_Success() {
        // Arrange
        Driver mockDriver = Driver.builder()
                .cinDriver("D130")
                .username("driverUser")
                .password("securePass")
                .firstName("Driver")
                .lastName("Test")
                .email("driver.test@example.com")
                .dateOfBirth(LocalDate.of(1990, 5, 5))
                .licenseNumber("L12345")
                .licensePlate("PLATE123")
                .brand("Toyota")
                .build();

        Set<Bordoreau> bordoreaus = new HashSet<>(); // Populate as needed
        mockDriver.setBordoreausDriver(bordoreaus);

        when(driverRepository.findById("D130")).thenReturn(Optional.of(mockDriver));

        // Act
        DriverDTOMobile dto = driverService.convertToDriverDTOMoblie("D130");

        // Assert
        assertEquals("driverUser", dto.getUsername());
        assertEquals("Driver", dto.getFirstName());
        verify(driverRepository, times(1)).findById("D130");
    }
}
