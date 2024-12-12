package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Sender;
import com.example.packettracerbase.repository.SenderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SenderServiceImplTest {

    @Mock
    private SenderRepository senderRepository;

    @InjectMocks
    private SenderServiceImpl senderService;

    public SenderServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSenderById_ShouldReturnSender_WhenExists() {
        Sender sender = new Sender();
        sender.setUsername("testSender");
        when(senderRepository.findById("123")).thenReturn(Optional.of(sender));

        Optional<Sender> result = senderService.getSenderById("123");

        assertNotNull(result);
        assertEquals("testSender",result.get().getUsername() );
        verify(senderRepository, times(1)).findById("123");
    }

    @Test
    void getSenderById_ShouldThrowException_WhenNotFound() {
        when(senderRepository.findById("123")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> senderService.getSenderById("123"));

        assertEquals("Sender not found with id: 123", exception.getMessage());
        verify(senderRepository, times(1)).findById("123");
    }

    @Test
    void createSender_ShouldSaveSender() {
        Sender sender = new Sender();
        sender.setUsername("testSender");
        when(senderRepository.save(sender)).thenReturn(sender);

        Sender result = senderService.createSender(sender);

        assertNotNull(result);
        assertEquals("testSender", result.getUsername());
        verify(senderRepository, times(1)).save(sender);
    }

    @Test
    void updateSender_ShouldUpdateAndReturnSender() {
        Sender existingSender = new Sender();
        existingSender.setUsername("oldSender");
        when(senderRepository.findById("123")).thenReturn(Optional.of(existingSender));

        Sender updatedSender = new Sender();
        updatedSender.setUsername("newSender");

        when(senderRepository.save(existingSender)).thenReturn(existingSender);

        Sender result = senderService.updateSender("123", updatedSender);

        assertNotNull(result);
        assertEquals("newSender", result.getUsername());
        verify(senderRepository, times(1)).findById("123");
        verify(senderRepository, times(1)).save(existingSender);
    }

    @Test
    void deleteSender_ShouldDeleteSender_WhenExists() {
        when(senderRepository.existsById("123")).thenReturn(true);

        senderService.deleteSender("123");

        verify(senderRepository, times(1)).deleteById("123");
    }

    @Test
    void deleteSender_ShouldThrowException_WhenNotFound() {
        when(senderRepository.existsById("123")).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> senderService.deleteSender("123"));

        assertEquals("Sender not found with id: 123", exception.getMessage());
        verify(senderRepository, never()).deleteById("123");
    }
}
