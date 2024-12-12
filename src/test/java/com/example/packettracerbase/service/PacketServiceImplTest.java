package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.PacketDTO;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Client;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.repository.BordoreauRepository;
import com.example.packettracerbase.repository.PacketRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PacketServiceImplTest {

    @Mock
    private PacketRepository packetRepository;

    @Mock
    private BordoreauRepository bordoreauRepository;

    @Mock
    private PacketHelper packetHelper;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private PacketServiceImpl packetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllPackets_ShouldReturnAllPackets() {
        Packet packet1 = new Packet();
        Packet packet2 = new Packet();
        when(packetRepository.findAll()).thenReturn(Arrays.asList(packet1, packet2));

        List<Packet> packets = packetService.getAllPackets();

        assertEquals(2, packets.size());
        verify(packetRepository, times(1)).findAll();
    }

    @Test
    void getPacketById_ShouldReturnPacket_WhenIdExists() {
        Packet packet = new Packet();
        when(packetRepository.findById(1L)).thenReturn(Optional.of(packet));

        Optional<Packet> result = packetService.getPacketById(1L);

        assertTrue(result.isPresent());
        assertEquals(packet, result.get());
        verify(packetRepository, times(1)).findById(1L);
    }

    @Test
    void getPacketById_ShouldReturnEmpty_WhenIdDoesNotExist() {
        when(packetRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Packet> result = packetService.getPacketById(1L);

        assertFalse(result.isPresent());
        verify(packetRepository, times(1)).findById(1L);
    }

    @Test
    void createPacket_ShouldSaveAndReturnPacket() {
        Packet packet = new Packet();
        when(packetRepository.save(packet)).thenReturn(packet);

        Packet result = packetService.createPacket(packet);

        assertEquals(packet, result);
        verify(packetRepository, times(1)).save(packet);
    }

    @Test
    void updatePacket_ShouldUpdateAndReturnPacket_WhenIdExists() {
        Packet existingPacket = new Packet();
        Packet updatedPacket = new Packet();
        Bordoreau bordoreau = new Bordoreau();
        updatedPacket.setBordoreau(bordoreau);

        when(packetHelper.findPacketById(packetRepository, 1L)).thenReturn(existingPacket);
        when(packetRepository.save(existingPacket)).thenReturn(existingPacket);

        Packet result = packetService.updatePacket(1L, updatedPacket);

        verify(packetHelper, times(1)).findPacketById(packetRepository, 1L);
        verify(packetHelper, times(1)).updatePacketDetails(existingPacket, updatedPacket);
        verify(packetHelper, times(1)).updateBordoreauStatusIfNeeded(bordoreau, bordoreauRepository);
        verify(packetRepository, times(1)).save(existingPacket);

        assertEquals(existingPacket, result);
    }

    @Test
    void updatePacket_ShouldThrowEntityNotFoundException_WhenIdDoesNotExist() {
        when(packetHelper.findPacketById(packetRepository, 1L)).thenThrow(new EntityNotFoundException("Packet not found"));

        assertThrows(EntityNotFoundException.class, () -> packetService.updatePacket(1L, new Packet()));

        verify(packetHelper, times(1)).findPacketById(packetRepository, 1L);
        verify(packetRepository, never()).save(any(Packet.class));
    }

    @Test
    void deletePacket_ShouldDeletePacket_WhenIdExists() {
        when(packetRepository.existsById(1L)).thenReturn(true);

        packetService.deletePacket(1L);

        verify(packetRepository, times(1)).existsById(1L);
        verify(packetRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePacket_ShouldThrowEntityNotFoundException_WhenIdDoesNotExist() {
        when(packetRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> packetService.deletePacket(1L));

        verify(packetRepository, times(1)).existsById(1L);
        verify(packetRepository, never()).deleteById(1L);
    }

    @Test
    void getAllPacketsAsJson_ShouldReturnJsonString_WhenConversionSucceeds() throws JsonProcessingException {
        Packet packet = Packet.builder()
                .idPacket(1L)
                .client(new Client())
                .colis(10)
                .sachets(5)
                .status(PacketStatus.IN_TRANSIT) // Assuming PacketStatus has values like IN_TRANSIT
                .bordoreau(new Bordoreau())
                .transferts(new HashSet<>()) // Initialize as empty set for now
                .build();
        when(packetRepository.findAll()).thenReturn(List.of(packet));
        when(objectMapper.writeValueAsString(anyList())).thenReturn("[{\"idPacket\":1}]");

        String json = packetService.getAllPacketsAsJson();

        assertNotNull(json);
        assertEquals("[{\"idPacket\":1}]", json);
        verify(packetRepository, times(1)).findAll();
        verify(objectMapper, times(1)).writeValueAsString(anyList());
    }

    @Test
    void getAllPacketsAsJson_ShouldLogError_WhenConversionFails() throws JsonProcessingException {
        Packet packet = Packet.builder()
                .idPacket(1L)
                .client(Client.builder().cinClient("123")
                        .username("admin1")
                        .password("securePassword")
                        .isActive(true)
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .dateOfBirth(LocalDate.of(1990, 1, 1))
                        .build())
                .colis(10)
                .sachets(5)
                .status(PacketStatus.IN_TRANSIT) // Assuming PacketStatus has values like IN_TRANSIT
                .bordoreau(new Bordoreau())
                .transferts(new HashSet<>()) // Initialize as empty set for now
                .build();
        when(packetRepository.findAll()).thenReturn(List.of(packet));
        when(objectMapper.writeValueAsString(anyList())).thenThrow(JsonProcessingException.class);

        String json = packetService.getAllPacketsAsJson();

        assertNull(json);
        verify(packetRepository, times(1)).findAll();
        verify(objectMapper, times(1)).writeValueAsString(anyList());
        verify(packetHelper, times(1)).logError(eq("Error converting packets to JSON"), any(JsonProcessingException.class));
    }
}
