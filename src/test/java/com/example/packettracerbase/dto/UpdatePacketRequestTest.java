package com.example.packettracerbase.dto;

import com.example.packettracerbase.dto.UpdatePacketRequest;
import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdatePacketRequestTest {

    @Test
    void testToString() {
        // Arrange
        UpdatePacketRequest request = new UpdatePacketRequest(
                123L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );

        // Act
        String result = request.toString();
        System.out.println(result);

        // Assert
        assertTrue(result.contains("idPacket=123"));
        assertTrue(result.contains("codeClient=456"));
        assertTrue(result.contains("colis=5"));
        assertTrue(result.contains("sachets=10"));
        assertTrue(result.contains("status='IN_TRANSIT'"));
    }

    @Test
    void testEquals() {
        // Arrange
        UpdatePacketRequest request1 = new UpdatePacketRequest(
                123L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );
        UpdatePacketRequest request2 = new UpdatePacketRequest(
                123L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );
        UpdatePacketRequest request3 = new UpdatePacketRequest(
                124L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );

        // Act & Assert
        assertEquals(request1, request2); // Same values, should be equal
        assertNotEquals(request1, request3); // Different idPacket, should not be equal
    }

    @Test
    void testHashCode() {
        // Arrange
        UpdatePacketRequest request1 = new UpdatePacketRequest(
                123L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );
        UpdatePacketRequest request2 = new UpdatePacketRequest(
                123L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );
        UpdatePacketRequest request3 = new UpdatePacketRequest(
                124L,
                456L,
                5,
                10,
                PacketStatus.IN_TRANSIT
        );

        // Act & Assert
        assertEquals(request1.hashCode(), request2.hashCode()); // Same values, should have the same hash code
        assertNotEquals(request1.hashCode(), request3.hashCode()); // Different idPacket, should have different hash codes
    }
}
