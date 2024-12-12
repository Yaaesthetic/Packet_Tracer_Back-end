package com.example.packettracerbase.service;

import com.example.packettracerbase.model.*;
import com.example.packettracerbase.repository.BordoreauRepository;
import com.example.packettracerbase.repository.PacketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacketHelperTest {

    private PacketHelper packetHelper;

    @Mock
    private PacketRepository packetRepository;

    @Mock
    private BordoreauRepository bordoreauRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        packetHelper = new PacketHelper();
    }

    @Test
    void testFindPacketById_PacketExists() {
        // Arrange
        Packet mockPacket = new Packet();
        mockPacket.setIdPacket(1L);
        when(packetRepository.findById(1L)).thenReturn(Optional.of(mockPacket));

        // Act
        Packet result = packetHelper.findPacketById(packetRepository, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getIdPacket());
        verify(packetRepository, times(1)).findById(1L);
    }

    @Test
    void testFindPacketById_PacketNotFound() {
        // Arrange
        when(packetRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            packetHelper.findPacketById(packetRepository, 1L);
        });
        assertEquals("Packet not found with id: 1", exception.getMessage());
    }

    @Test
    void testUpdatePacketDetails() {
        // Arrange
        Packet existingPacket = new Packet();
        Packet packetDetails = new Packet();
        packetDetails.setColis(5);
        packetDetails.setSachets(10);
        packetDetails.setStatus(PacketStatus.IN_TRANSIT);

        // Act
        packetHelper.updatePacketDetails(existingPacket, packetDetails);

        // Assert
        assertEquals(5, existingPacket.getColis());
        assertEquals(10, existingPacket.getSachets());
        assertEquals(PacketStatus.IN_TRANSIT, existingPacket.getStatus());
    }

    @Test
    void testUpdateBordoreauStatusIfNeeded_StatusUpdated() {
        // Arrange
        Bordoreau bordoreau = new Bordoreau();
        Packet packet = new Packet();
        packet.setStatus(PacketStatus.DONE);
        bordoreau.setPacketsBordoreau(Set.of(packet));

        // Act
        packetHelper.updateBordoreauStatusIfNeeded(bordoreau, bordoreauRepository);

        // Assert
        assertEquals(PacketStatus.DONE, bordoreau.getStatus());
        verify(bordoreauRepository, times(1)).save(bordoreau);
    }

    @Test
    void testUpdateBordoreauStatusIfNeeded_StatusNotUpdated() {
        // Arrange
        Bordoreau bordoreau = new Bordoreau();
        Packet packet = new Packet();
        packet.setStatus(PacketStatus.IN_TRANSIT);
        bordoreau.setPacketsBordoreau(Set.of(packet));

        // Act
        packetHelper.updateBordoreauStatusIfNeeded(bordoreau, bordoreauRepository);

        // Assert
        assertNull(bordoreau.getStatus());
        verify(bordoreauRepository, times(0)).save(bordoreau);
    }

    @Test
    void testLogError() {
        // Arrange
        Exception exception = new RuntimeException("Test Exception");

        // Act
        packetHelper.logError("Error occurred", exception);

        // Assert
        // Since System.err is used, this test will only ensure no exceptions are thrown
        assertDoesNotThrow(() -> packetHelper.logError("Error occurred", exception));
    }
}
