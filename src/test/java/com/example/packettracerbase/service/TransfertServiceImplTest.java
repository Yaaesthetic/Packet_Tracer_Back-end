package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.TransfertDesktop;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.model.Transfert;
import com.example.packettracerbase.repository.PacketRepository;
import com.example.packettracerbase.repository.TransfertRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransfertServiceImplTest {

    @Mock
    private TransfertRepository transfertRepository;

    @Mock
    private PacketRepository packetRepository;

    @InjectMocks
    private TransfertServiceImpl transfertService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransferts() {
        // Arrange
        List<Transfert> mockTransferts = Arrays.asList(
                new Transfert(1L, "OldPerson1", "NewPerson1", LocalDateTime.now(), null),
                new Transfert(2L, "OldPerson2", "NewPerson2", LocalDateTime.now(), null)
        );
        when(transfertRepository.findAll()).thenReturn(mockTransferts);

        // Act
        List<Transfert> transferts = transfertService.getAllTransferts();

        // Assert
        assertEquals(2, transferts.size());
        verify(transfertRepository, times(1)).findAll();
    }

    @Test
    void testGetTransfertById_Success() {
        // Arrange
        Transfert mockTransfert = new Transfert(1L, "OldPerson", "NewPerson", LocalDateTime.now(), null);
        when(transfertRepository.findById(1L)).thenReturn(Optional.of(mockTransfert));

        // Act
        Optional<Transfert> transfert = transfertService.getTransfertById(1L);

        // Assert
        assertTrue(transfert.isPresent());
        assertEquals("OldPerson", transfert.get().getOldPerson());
        verify(transfertRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransfertById_NotFound() {
        // Arrange
        when(transfertRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Transfert> transfert = transfertService.getTransfertById(1L);

        // Assert
        assertTrue(transfert.isEmpty());
        verify(transfertRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTransfert() {
        // Arrange
        Transfert transfert = new Transfert(1L, "OldPerson", "NewPerson", LocalDateTime.now(), null);
        when(transfertRepository.save(transfert)).thenReturn(transfert);

        // Act
        Transfert createdTransfert = transfertService.createTransfert(transfert);

        // Assert
        assertEquals("OldPerson", createdTransfert.getOldPerson());
        verify(transfertRepository, times(1)).save(transfert);
    }

    @Test
    void testUpdateTransfert_Success() {
        // Arrange
        Transfert existingTransfert = new Transfert(1L, "OldPerson", "NewPerson", LocalDateTime.now(), null);
        Transfert updatedTransfert = new Transfert(1L, "UpdatedPerson", "NewPerson", LocalDateTime.now(), null);
        when(transfertRepository.findById(1L)).thenReturn(Optional.of(existingTransfert));
        when(transfertRepository.save(existingTransfert)).thenReturn(updatedTransfert);

        // Act
        Transfert result = transfertService.updateTransfert(1L, updatedTransfert);

        // Assert
        assertEquals("UpdatedPerson", result.getOldPerson());
        verify(transfertRepository, times(1)).findById(1L);
        verify(transfertRepository, times(1)).save(existingTransfert);
    }

    @Test
    void testUpdateTransfert_NotFound() {
        // Arrange
        when(transfertRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                transfertService.updateTransfert(1L, new Transfert())
        );
        assertEquals("Transfert not found with id: 1", exception.getMessage());
        verify(transfertRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTransfert_Success() {
        // Arrange
        when(transfertRepository.existsById(1L)).thenReturn(true);

        // Act
        transfertService.deleteTransfert(1L);

        // Assert
        verify(transfertRepository, times(1)).existsById(1L);
        verify(transfertRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTransfert_NotFound() {
        // Arrange
        when(transfertRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                transfertService.deleteTransfert(1L)
        );
        assertEquals("Transfert not found with id: 1", exception.getMessage());
        verify(transfertRepository, times(1)).existsById(1L);
    }

    @Test
    void testCreateTransfertDTO_Success() {
        // Arrange
        Packet mockPacket = Packet.builder()
                .idPacket(10L)
                .colis(5)
                .sachets(10)
                .status(PacketStatus.INITIALIZED) // Replace with an actual enum value if defined
                .build();

        TransfertDesktop transfertDesktop = new TransfertDesktop(1L, "OldPerson", "NewPerson", LocalDateTime.now(), 10L);

        when(packetRepository.findById(10L)).thenReturn(Optional.of(mockPacket));
        when(transfertRepository.save(any(Transfert.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        TransfertDesktop result = transfertService.createTransfert(transfertDesktop);

        // Assert
        assertEquals("OldPerson", result.getOldPerson());
        verify(packetRepository, times(1)).findById(10L);
        verify(transfertRepository, times(1)).save(any(Transfert.class));
    }

    @Test
    void testGetTransfersByPacketId() {
        // Arrange
        Packet packet = Packet.builder()
                .idPacket(10L)
                .colis(5)
                .sachets(10)
                .status(PacketStatus.INITIALIZED) // Replace with an actual enum value if defined
                .build();

        Transfert transfert = new Transfert(1L, "OldPerson", "NewPerson", LocalDateTime.now(), packet);
        when(transfertRepository.findAll()).thenReturn(Arrays.asList(transfert));

        // Act
        List<Transfert> result = transfertService.getTransfersByPacketId(10L);

        // Assert
        assertEquals(1, result.size());
        assertEquals("OldPerson", result.get(0).getOldPerson());
        verify(transfertRepository, times(1)).findAll();
    }

}
