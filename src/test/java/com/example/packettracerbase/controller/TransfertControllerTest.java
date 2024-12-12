package com.example.packettracerbase.controller;

import com.example.packettracerbase.dto.TransfertDesktop;
import com.example.packettracerbase.dto.TransfertRequest;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.model.Transfert;
import com.example.packettracerbase.repository.PacketRepository;
import com.example.packettracerbase.service.TransfertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransfertControllerTest {

    @InjectMocks
    private TransfertController transfertController;

    @Mock
    private TransfertService transfertService;

    @Mock
    private PacketRepository packetRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransferts() {
        List<Transfert> mockTransferts = Arrays.asList(
                new Transfert(1L, "OldPerson1", "NewPerson1", LocalDateTime.now(), null),
                new Transfert(2L, "OldPerson2", "NewPerson2", LocalDateTime.now(), null)
        );

        when(transfertService.getAllTransferts()).thenReturn(mockTransferts);

        ResponseEntity<List<Transfert>> response = transfertController.getAllTransferts();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(transfertService, times(1)).getAllTransferts();
    }

    @Test
    void testGetTransfertById_Success() {
        Transfert mockTransfert = new Transfert(1L, "OldPerson", "NewPerson", LocalDateTime.now(), null);

        when(transfertService.getTransfertById(1L)).thenReturn(Optional.of(mockTransfert));

        ResponseEntity<Transfert> response = transfertController.getTransfertById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockTransfert, response.getBody());
        verify(transfertService, times(1)).getTransfertById(1L);
    }

    @Test
    void testGetTransfertById_NotFound() {
        when(transfertService.getTransfertById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Transfert> response = transfertController.getTransfertById(1L);
        assertEquals(404, response.getStatusCodeValue());
        verify(transfertService, times(1)).getTransfertById(1L);
    }

    @Test
    void testCreateTransfert_Success() {
        Transfert newTransfert = new Transfert(null, "OldPerson", "NewPerson", LocalDateTime.now(), null);
        Transfert createdTransfert = new Transfert(1L, "OldPerson", "NewPerson", LocalDateTime.now(), null);

        when(transfertService.createTransfert(newTransfert)).thenReturn(createdTransfert);

        ResponseEntity<Transfert> response = transfertController.createTransfert(newTransfert);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createdTransfert, response.getBody());
        verify(transfertService, times(1)).createTransfert(newTransfert);
    }

    @Test
    void testUpdateTransfert() {
        Transfert updatedTransfert = new Transfert(1L, "UpdatedOldPerson", "UpdatedNewPerson", LocalDateTime.now(), null);

        when(transfertService.updateTransfert(1L, updatedTransfert)).thenReturn(updatedTransfert);

        ResponseEntity<Transfert> response = transfertController.updateTransfert(1L, updatedTransfert);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedTransfert, response.getBody());
        verify(transfertService, times(1)).updateTransfert(1L, updatedTransfert);
    }

    @Test
    void testDeleteTransfert() {
        doNothing().when(transfertService).deleteTransfert(1L);

        ResponseEntity<Void> response = transfertController.deleteTransfert(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(transfertService, times(1)).deleteTransfert(1L);
    }

    @Test
    void testCreateTransfertFromRequest() {
        Packet packet1 = new Packet(1L, null, 0, 0, PacketStatus.INITIALIZED, null, null);
        Packet packet2 = new Packet(2L, null, 0, 0, PacketStatus.INITIALIZED, null, null);

        TransfertRequest request = new TransfertRequest("OldPerson","NewPerson",Set.of(1L, 2L));


        when(packetRepository.findById(1L)).thenReturn(Optional.of(packet1));
        when(packetRepository.findById(2L)).thenReturn(Optional.of(packet2));

        ResponseEntity<String> response = transfertController.createTransfert(request);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Transfert created successfully", response.getBody());

        verify(packetRepository, times(1)).findById(1L);
        verify(packetRepository, times(1)).findById(2L);
    }

    @Test
    void testCreateTransfertDesktop() {
        TransfertDesktop transfertDesktop = new TransfertDesktop(1L, "OldPerson", "NewPerson", LocalDateTime.now(), 1L);
        TransfertDesktop createdTransfert = new TransfertDesktop(1L, "OldPerson", "NewPerson", LocalDateTime.now(), 1L);

        when(transfertService.createTransfert(transfertDesktop)).thenReturn(createdTransfert);

        ResponseEntity<TransfertDesktop> response = transfertController.createTransfert(transfertDesktop);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createdTransfert, response.getBody());
        verify(transfertService, times(1)).createTransfert(transfertDesktop);
    }

    @Test
    void testGetTransfersByPacketId() {
        List<TransfertDesktop> mockTransfers = Arrays.asList(
                new TransfertDesktop(1L, "OldPerson1", "NewPerson1", LocalDateTime.now(), 1L),
                new TransfertDesktop(2L, "OldPerson2", "NewPerson2", LocalDateTime.now(), 1L)
        );

        when(transfertService.getTransfersDTOByPacketId(1L)).thenReturn(mockTransfers);

        ResponseEntity<List<TransfertDesktop>> response = transfertController.getTransfersByPacketId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(transfertService, times(1)).getTransfersDTOByPacketId(1L);
    }
}
