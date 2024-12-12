package com.example.packettracerbase.controller;

import com.example.packettracerbase.dto.DriverDTO;
import com.example.packettracerbase.dto.DriverDTOMobile;
import com.example.packettracerbase.model.AuthenticationRequest;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.model.Role;
import com.example.packettracerbase.service.AuthenticationService;
import com.example.packettracerbase.service.DriverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DriverController.class)
class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverService driverService;

    @MockBean
    private AuthenticationService authenticationService;

    private Driver driver;

    @BeforeEach
    void setUp() {
        driver = Driver.builder()
                .cinDriver("D123")
                .username("driver1")
                .password("password1")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .isActive(true)
                .role(Role.Driver)
                .licenseNumber("LIC12345")
                .licensePlate("XYZ-9876")
                .brand("Toyota")
                .build();
    }

    @Test
    void testGetAllDrivers() throws Exception {
        when(driverService.getAllDrivers()).thenReturn(Arrays.asList(driver));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/drivers")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cinDriver").value(driver.getCinDriver()));
    }

    @Test
    void testGetDriverById() throws Exception {
        when(driverService.getDriverById("D123")).thenReturn(Optional.of(driver));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/drivers/D123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinDriver").value(driver.getCinDriver()));
    }

    @Test
    void testCreateDriver() throws Exception {
        when(driverService.createDriver(Mockito.any(Driver.class))).thenReturn(driver);

        String driverJson = """
                {
                    "cinDriver": "D123",
                    "username": "driver1",
                    "password": "password1",
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "isActive": true,
                    "role": "Driver",
                    "licenseNumber": "LIC12345",
                    "licensePlate": "XYZ-9876",
                    "brand": "Toyota"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(driverJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cinDriver").value(driver.getCinDriver()));
    }

    @Test
    void testUpdateDriver() throws Exception {
        when(driverService.updateDriver(Mockito.eq("D123"), Mockito.any(Driver.class))).thenReturn(driver);

        String updatedDriverJson = """
                {
                    "cinDriver": "D123",
                    "username": "updatedDriver",
                    "password": "newPassword",
                    "firstName": "Updated",
                    "lastName": "Name",
                    "email": "updated.name@example.com",
                    "isActive": true,
                    "role": "Driver",
                    "licenseNumber": "NEW12345",
                    "licensePlate": "NEW-9876",
                    "brand": "Honda"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/drivers/D123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedDriverJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinDriver").value(driver.getCinDriver()));
    }

    @Test
    void testDeleteDriver() throws Exception {
        Mockito.doNothing().when(driverService).deleteDriver("D123");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/drivers/D123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testLoginDriver_Success() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("driver1", "password1");
        when(authenticationService.authenticateDriver(request.getUsername(), request.getPassword()))
                .thenReturn(Optional.of(driver.getCinDriver()));

        String loginRequestJson = """
                {
                    "username": "driver1",
                    "password": "password1"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/drivers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinDriver").value(driver.getCinDriver()));
    }

    @Test
    void testLoginDriver_Failure() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("driver1", "wrongPassword");
        when(authenticationService.authenticateDriver(request.getUsername(), request.getPassword()))
                .thenReturn(Optional.empty());

        String loginRequestJson = """
                {
                    "username": "driver1",
                    "password": "wrongPassword"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/drivers/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testCreateDriverWithDTO() throws Exception {
        DriverDTO driverDTO = DriverDTO.builder()
                .cinDriver("D123")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .licenseNumber("LIC12345")
                .licensePlate("XYZ-9876")
                .brand("Toyota")
                .username("driver1")
                .password("password1")
                .build();

        when(driverService.addDriver(Mockito.any(DriverDTO.class))).thenReturn(driverDTO);

        String driverDTOJson = """
            {
                "cinDriver": "D123",
                "firstName": "John",
                "lastName": "Doe",
                "email": "john.doe@example.com",
                "dateOfBirth": "1990-01-01",
                "licenseNumber": "LIC12345",
                "licensePlate": "XYZ-9876",
                "brand": "Toyota",
                "username": "driver1",
                "password": "password1"
            }
            """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/drivers/json")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(driverDTOJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cinDriver").value("D123"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.dateOfBirth").value("1990-01-01"))
                .andExpect(jsonPath("$.licenseNumber").value("LIC12345"))
                .andExpect(jsonPath("$.licensePlate").value("XYZ-9876"))
                .andExpect(jsonPath("$.brand").value("Toyota"))
                .andExpect(jsonPath("$.username").value("driver1"))
                .andExpect(jsonPath("$.password").value("password1"));
    }
}
