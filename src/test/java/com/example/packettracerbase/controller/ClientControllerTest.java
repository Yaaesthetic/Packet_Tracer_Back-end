package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.AuthenticationRequest;
import com.example.packettracerbase.model.Client;
import com.example.packettracerbase.model.Role;
import com.example.packettracerbase.service.AuthenticationService;
import com.example.packettracerbase.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private AuthenticationService authenticationService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = Client.builder()
                .cinClient("CIN456")
                .username("client1")
                .password("password1")
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .isActive(true)
                .role(Role.Client)
                .build();
    }

    @Test
    void testGetAllClients() throws Exception {
        when(clientService.getAllClients()).thenReturn(Arrays.asList(client));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cinClient").value(client.getCinClient()));
    }

    @Test
    void testGetClientById() throws Exception {
        when(clientService.getClientById("CIN456")).thenReturn(Optional.of(client));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/clients/CIN456")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinClient").value(client.getCinClient()));
    }

    @Test
    void testCreateClient() throws Exception {
        when(clientService.createClient(Mockito.any(Client.class))).thenReturn(client);

        String clientJson = """
                {
                    "cinClient": "CIN456",
                    "username": "client1",
                    "password": "password1",
                    "firstName": "Jane",
                    "lastName": "Doe",
                    "email": "jane.doe@example.com",
                    "isActive": true,
                    "role": "Client"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cinClient").value(client.getCinClient()));
    }

    @Test
    void testUpdateClient() throws Exception {
        when(clientService.updateClient(Mockito.eq("CIN456"), Mockito.any(Client.class))).thenReturn(client);

        String updatedClientJson = """
                {
                    "cinClient": "CIN456",
                    "username": "clientUpdated",
                    "password": "newPassword",
                    "firstName": "UpdatedName",
                    "lastName": "Doe",
                    "email": "updated.doe@example.com",
                    "isActive": true,
                    "role": "Client"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/api/clients/CIN456")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedClientJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinClient").value(client.getCinClient()));
    }

    @Test
    void testDeleteClient() throws Exception {
        Mockito.doNothing().when(clientService).deleteClient("CIN456");

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/clients/CIN456"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testLoginClient_Success() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("client1", "password1");
        when(authenticationService.authenticateClient(request.getUsername(), request.getPassword()))
                .thenReturn(Optional.of(client.getCinClient()));

        String loginRequestJson = """
                {
                    "username": "client1",
                    "password": "password1"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cinClient").value(client.getCinClient()));
    }

    @Test
    void testLoginClient_Failure() throws Exception {
        AuthenticationRequest request = new AuthenticationRequest("client1", "wrongPassword");
        when(authenticationService.authenticateClient(request.getUsername(), request.getPassword()))
                .thenReturn(Optional.empty());

        String loginRequestJson = """
                {
                    "username": "client1",
                    "password": "wrongPassword"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/clients/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestJson))
                .andExpect(status().isUnauthorized());
    }
}
