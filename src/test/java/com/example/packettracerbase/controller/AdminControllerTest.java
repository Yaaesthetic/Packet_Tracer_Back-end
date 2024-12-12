package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.Admin;
import com.example.packettracerbase.model.AuthenticationRequest;
import com.example.packettracerbase.service.AdminService;
import com.example.packettracerbase.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @MockBean
    private AuthenticationService authenticationService;

    private Admin admin;
    private AuthenticationRequest authRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        admin = Admin.builder()
                .cinAdmin("CIN123")
                .username("adminUser")
                .password("adminPass")
                .isActive(true)
                .firstName("John")
                .lastName("Doe")
                .email("admin@example.com")
                .build();

        authRequest = new AuthenticationRequest("adminUser","adminPass");

        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllAdmins_shouldReturnListOfAdmins() throws Exception {
        when(adminService.getAllAdmins()).thenReturn(Collections.singletonList(admin));

        mockMvc.perform(get("/api/admins"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].cinAdmin").value(admin.getCinAdmin()));
    }

    @Test
    void getAdminById_shouldReturnAdmin() throws Exception {
        when(adminService.getAdminById("CIN123")).thenReturn(Optional.of(admin));

        mockMvc.perform(get("/api/admins/CIN123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinAdmin").value(admin.getCinAdmin()));
    }

    @Test
    void createAdmin_shouldReturnCreatedAdmin() throws Exception {
        when(adminService.createAdmin(any(Admin.class))).thenReturn(admin);

        mockMvc.perform(post("/api/admins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cinAdmin").value(admin.getCinAdmin()));
    }

    @Test
    void updateAdmin_shouldReturnUpdatedAdmin() throws Exception {
        when(adminService.updateAdmin(Mockito.eq("CIN123"), any(Admin.class))).thenReturn(admin);

        mockMvc.perform(put("/api/admins/CIN123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admin)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinAdmin").value(admin.getCinAdmin()));
    }

    @Test
    void deleteAdmin_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(adminService).deleteAdmin("CIN123");

        mockMvc.perform(delete("/api/admins/CIN123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void loginAdmin_shouldReturnSuccessResponse() throws Exception {
        when(authenticationService.authenticateAdmin("adminUser", "adminPass")).thenReturn(Optional.of("CIN123"));

        mockMvc.perform(post("/api/admins/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Admin login successful"))
                .andExpect(jsonPath("$.cinAdmin").value("CIN123"));
    }

    @Test
    void loginAdmin_shouldReturnUnauthorized() throws Exception {
        when(authenticationService.authenticateAdmin("adminUser", "adminPass")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/admins/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Admin login failed"));
    }
}
