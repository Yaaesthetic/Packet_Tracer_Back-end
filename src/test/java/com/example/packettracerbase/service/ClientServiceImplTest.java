package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Client;
import com.example.packettracerbase.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceImplTest {

    private ClientRepository clientRepository;
    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void getAllClients_ShouldReturnListOfClients() {
        List<Client> clients = List.of(new Client());
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.getAllClients();

        assertEquals(1, result.size());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void getClientById_ShouldReturnClient_WhenExists() {
        Client client = new Client();
        client.setCinClient("123");
        when(clientRepository.findById("123")).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClientById("123");

        assertTrue(result.isPresent());
        assertEquals("123", result.get().getCinClient());
        verify(clientRepository, times(1)).findById("123");
    }

    @Test
    void getClientById_ShouldThrowException_WhenNotFound() {
        when(clientRepository.findById("123")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> clientService.getClientById("123"));
    }

    @Test
    void createClient_ShouldSaveAndReturnClient() {
        Client client = new Client();
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.createClient(client);

        assertNotNull(result);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void updateClient_ShouldUpdateAndReturnClient() {
        Client existingClient = new Client();
        existingClient.setCinClient("123");
        existingClient.setUsername("oldName");

        Client updatedClient = new Client();
        updatedClient.setUsername("newName");

        when(clientRepository.findById("123")).thenReturn(Optional.of(existingClient));
        when(clientRepository.save(existingClient)).thenReturn(existingClient);

        Client result = clientService.updateClient("123", updatedClient);

        assertEquals("newName", result.getUsername());
        verify(clientRepository, times(1)).save(existingClient);
    }

    @Test
    void deleteClient_ShouldCallRepository_WhenExists() {
        when(clientRepository.existsById("123")).thenReturn(true);

        clientService.deleteClient("123");

        verify(clientRepository, times(1)).deleteById("123");
    }

    @Test
    void deleteClient_ShouldThrowException_WhenNotFound() {
        when(clientRepository.existsById("123")).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> clientService.deleteClient("123"));
    }
}
