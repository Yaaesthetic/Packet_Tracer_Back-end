package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.service.PacketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacketControllerTest {

    @InjectMocks
    private PacketController packetController;

    @Mock
    private PacketService packetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPackets() {
        List<Packet> mockPackets = new ArrayList<>();
        mockPackets.add(new Packet(1L, null, 2, 5, PacketStatus.IN_TRANSIT, null, null));
        mockPackets.add(new Packet(2L, null, 1, 3, PacketStatus.TRANSMITTED, null, null));

        when(packetService.getAllPackets()).thenReturn(mockPackets);

        ResponseEntity<List<Packet>> response = packetController.getAllPackets();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(packetService, times(1)).getAllPackets();
    }

    @Test
    void testGetPacketById_Success() {
        Packet mockPacket = new Packet(1L, null, 2, 5, PacketStatus.IN_TRANSIT, null, null);

        when(packetService.getPacketById(1L)).thenReturn(Optional.of(mockPacket));

        ResponseEntity<Packet> response = packetController.getPacketById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockPacket, response.getBody());
        verify(packetService, times(1)).getPacketById(1L);
    }

    @Test
    void testGetPacketById_NotFound() {
        when(packetService.getPacketById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> packetController.getPacketById(1L));
        assertEquals("Packet not found with id: 1", exception.getMessage());
        verify(packetService, times(1)).getPacketById(1L);
    }

    @Test
    void testCreatePacket() {
        Packet mockPacket = new Packet(1L, null, 2, 5, PacketStatus.IN_TRANSIT, null, null);
        when(packetService.createPacket(mockPacket)).thenReturn(mockPacket);

        ResponseEntity<Packet> response = packetController.createPacket(mockPacket);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(mockPacket, response.getBody());
        verify(packetService, times(1)).createPacket(mockPacket);
    }

    @Test
    void testUpdatePacket() {
        Packet existingPacket = new Packet(1L, null, 2, 5, PacketStatus.IN_TRANSIT, null, null);
        Packet updatedPacket = new Packet(1L, null, 3, 6, PacketStatus.TRANSMITTED, null, null);

        when(packetService.updatePacket(1L, updatedPacket)).thenReturn(updatedPacket);

        ResponseEntity<Packet> response = packetController.updatePacket(1L, updatedPacket);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedPacket, response.getBody());
        verify(packetService, times(1)).updatePacket(1L, updatedPacket);
    }

    @Test
    void testDeletePacket() {
        doNothing().when(packetService).deletePacket(1L);

        ResponseEntity<Void> response = packetController.deletePacket(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(packetService, times(1)).deletePacket(1L);
    }

    @Test
    void testGetAllPacketsAsJson_Success() {
        String mockJson = "[{\"idPacket\":1,\"colis\":2,\"sachets\":5,\"status\":\"IN_TRANSIT\"}]";

        when(packetService.getAllPacketsAsJson()).thenReturn(mockJson);

        ResponseEntity<String> response = packetController.getAllPacketsAsJson();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockJson, response.getBody());
        verify(packetService, times(1)).getAllPacketsAsJson();
    }

    @Test
    void testGetAllPacketsAsJson_Error() {
        when(packetService.getAllPacketsAsJson()).thenReturn(null);

        ResponseEntity<String> response = packetController.getAllPacketsAsJson();
        assertEquals(500, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(packetService, times(1)).getAllPacketsAsJson();
    }

//    @Test
//    void testUpdatePacketStatus() {
//        Packet existingPacket = new Packet(1L, null, 2, 5, PacketStatus.IN_TRANSIT, null, null);
//        Packet updatedPacket = new Packet(1L, null, 2, 5, PacketStatus.TRANSMITTED, null, null);
//
//        // Mock the repository or service method call
//        when(packetService.updatePacket(1L, updatedPacket)).thenReturn(updatedPacket);
//        // Ensure you mock the retrieval of the existing packet if necessary
//        when(packetService.getPacketById(1L)).thenReturn(Optional.of(existingPacket));
//
//        // Call the controller method
//        ResponseEntity<Void> response = packetController.updatePacketStatus(1L, PacketStatus.TRANSMITTED);
//
//        // Assert the status code and verify the service method call
//        assertEquals(200, response.getStatusCodeValue());
//        verify(packetService, times(1)).updatePacket(1L, updatedPacket);
//    }

}
