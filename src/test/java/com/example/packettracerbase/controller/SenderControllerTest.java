package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.AuthenticationRequest;
import com.example.packettracerbase.model.Sender;
import com.example.packettracerbase.service.AuthenticationService;
import com.example.packettracerbase.service.SenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SenderControllerTest {

    @InjectMocks
    private SenderController senderController;

    @Mock
    private SenderService senderService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSenders() {
        List<Sender> mockSenders = Arrays.asList(
                new Sender("123A", null, null),
                new Sender("456B", null, null)
        );

        when(senderService.getAllSenders()).thenReturn(mockSenders);

        ResponseEntity<List<Sender>> response = senderController.getAllSenders();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(senderService, times(1)).getAllSenders();
    }

    @Test
    void testGetSenderById_Success() {
        Sender mockSender = new Sender("123A", null, null);

        when(senderService.getSenderById("123A")).thenReturn(Optional.of(mockSender));

        ResponseEntity<Sender> response = senderController.getSenderById("123A");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockSender, response.getBody());
        verify(senderService, times(1)).getSenderById("123A");
    }

    @Test
    void testGetSenderById_NotFound() {
        when(senderService.getSenderById("123A")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> senderController.getSenderById("123A"));
        assertEquals("Sender not found with id: 123A", exception.getMessage());
        verify(senderService, times(1)).getSenderById("123A");
    }

    @Test
    void testCreateSender() {
        Sender newSender = new Sender("123A", null, null);
        Sender createdSender = new Sender("123A", null, null);

        when(senderService.createSender(newSender)).thenReturn(createdSender);

        ResponseEntity<Sender> response = senderController.createSender(newSender);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createdSender, response.getBody());
        verify(senderService, times(1)).createSender(newSender);
    }

    @Test
    void testUpdateSender() {
        Sender existingSender = new Sender("123A", null, null);
        Sender updatedSender = new Sender("123A", null, null);

        when(senderService.updateSender("123A", updatedSender)).thenReturn(updatedSender);

        ResponseEntity<Sender> response = senderController.updateSender("123A", updatedSender);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedSender, response.getBody());
        verify(senderService, times(1)).updateSender("123A", updatedSender);
    }

    @Test
    void testDeleteSender() {
        doNothing().when(senderService).deleteSender("123A");

        ResponseEntity<Void> response = senderController.deleteSender("123A");
        assertEquals(204, response.getStatusCodeValue());
        verify(senderService, times(1)).deleteSender("123A");
    }

    @Test
    void testLoginSender_Success() {
        AuthenticationRequest request = new AuthenticationRequest("username", "password");
        when(authenticationService.authenticateSender("username", "password")).thenReturn(Optional.of("123A"));

        ResponseEntity<?> response = senderController.loginSender(request);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Map);

        Map<?, ?> responseBody = (Map<?, ?>) response.getBody();
        assertEquals("Sender login successful", responseBody.get("message"));
        assertEquals("123A", responseBody.get("cinSender"));

        verify(authenticationService, times(1)).authenticateSender("username", "password");
    }

    @Test
    void testLoginSender_Failure() {
        AuthenticationRequest request = new AuthenticationRequest("username", "wrongpassword");
        when(authenticationService.authenticateSender("username", "wrongpassword")).thenReturn(Optional.empty());

        ResponseEntity<?> response = senderController.loginSender(request);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Sender login failed", response.getBody());
        verify(authenticationService, times(1)).authenticateSender("username", "wrongpassword");
    }
}
