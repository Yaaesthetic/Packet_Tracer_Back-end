package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Admin;
import com.example.packettracerbase.model.Client;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.model.Sender;
import com.example.packettracerbase.repository.AdminRepository;
import com.example.packettracerbase.repository.ClientRepository;
import com.example.packettracerbase.repository.DriverRepository;
import com.example.packettracerbase.repository.SenderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private SenderRepository senderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticateAdmin_ShouldReturnCin_WhenCredentialsAreValid() {
        Admin admin = new Admin();
        admin.setCinAdmin("ADMIN123");
        when(adminRepository.findByUsernameAndPassword("adminUser", "adminPass"))
                .thenReturn(Optional.of(admin));

        Optional<String> result = authenticationService.authenticateAdmin("adminUser", "adminPass");

        assertTrue(result.isPresent());
        assertEquals("ADMIN123", result.get());
        verify(adminRepository, times(1)).findByUsernameAndPassword("adminUser", "adminPass");
    }

    @Test
    void authenticateAdmin_ShouldReturnEmpty_WhenCredentialsAreInvalid() {
        when(adminRepository.findByUsernameAndPassword("wrongUser", "wrongPass"))
                .thenReturn(Optional.empty());

        Optional<String> result = authenticationService.authenticateAdmin("wrongUser", "wrongPass");

        assertFalse(result.isPresent());
        verify(adminRepository, times(1)).findByUsernameAndPassword("wrongUser", "wrongPass");
    }

    @Test
    void authenticateSender_ShouldReturnCin_WhenCredentialsAreValid() {
        Sender sender = new Sender();
        sender.setCinSender("SENDER123");
        when(senderRepository.findByUsernameAndPassword("senderUser", "senderPass"))
                .thenReturn(Optional.of(sender));

        Optional<String> result = authenticationService.authenticateSender("senderUser", "senderPass");

        assertTrue(result.isPresent());
        assertEquals("SENDER123", result.get());
        verify(senderRepository, times(1)).findByUsernameAndPassword("senderUser", "senderPass");
    }

    @Test
    void authenticateSender_ShouldReturnEmpty_WhenCredentialsAreInvalid() {
        when(senderRepository.findByUsernameAndPassword("wrongUser", "wrongPass"))
                .thenReturn(Optional.empty());

        Optional<String> result = authenticationService.authenticateSender("wrongUser", "wrongPass");

        assertFalse(result.isPresent());
        verify(senderRepository, times(1)).findByUsernameAndPassword("wrongUser", "wrongPass");
    }

    @Test
    void authenticateClient_ShouldReturnCin_WhenCredentialsAreValid() {
        Client client = new Client();
        client.setCinClient("CLIENT123");
        when(clientRepository.findByUsernameAndPassword("clientUser", "clientPass"))
                .thenReturn(Optional.of(client));

        Optional<String> result = authenticationService.authenticateClient("clientUser", "clientPass");

        assertTrue(result.isPresent());
        assertEquals("CLIENT123", result.get());
        verify(clientRepository, times(1)).findByUsernameAndPassword("clientUser", "clientPass");
    }

    @Test
    void authenticateClient_ShouldReturnEmpty_WhenCredentialsAreInvalid() {
        when(clientRepository.findByUsernameAndPassword("wrongUser", "wrongPass"))
                .thenReturn(Optional.empty());

        Optional<String> result = authenticationService.authenticateClient("wrongUser", "wrongPass");

        assertFalse(result.isPresent());
        verify(clientRepository, times(1)).findByUsernameAndPassword("wrongUser", "wrongPass");
    }

    @Test
    void authenticateDriver_ShouldReturnCin_WhenCredentialsAreValid() {
        Driver driver = new Driver();
        driver.setCinDriver("DRIVER123");
        when(driverRepository.findByUsernameAndPassword("driverUser", "driverPass"))
                .thenReturn(Optional.of(driver));

        Optional<String> result = authenticationService.authenticateDriver("driverUser", "driverPass");

        assertTrue(result.isPresent());
        assertEquals("DRIVER123", result.get());
        verify(driverRepository, times(1)).findByUsernameAndPassword("driverUser", "driverPass");
    }

    @Test
    void authenticateDriver_ShouldReturnEmpty_WhenCredentialsAreInvalid() {
        when(driverRepository.findByUsernameAndPassword("wrongUser", "wrongPass"))
                .thenReturn(Optional.empty());

        Optional<String> result = authenticationService.authenticateDriver("wrongUser", "wrongPass");

        assertFalse(result.isPresent());
        verify(driverRepository, times(1)).findByUsernameAndPassword("wrongUser", "wrongPass");
    }
}
