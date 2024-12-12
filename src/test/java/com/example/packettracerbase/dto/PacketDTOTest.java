package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PacketDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long idPacket = 1001L;
        String clientCin = "CLIENT123";
        int colis = 2;
        int sachets = 5;
        PacketStatus status = PacketStatus.IN_TRANSIT;
        Long bordoreau = 2002L;

        // Act
        PacketDTO packetDTO = new PacketDTO(idPacket, clientCin, colis, sachets, status, bordoreau);

        // Assert
        assertThat(packetDTO.getIdPacket()).isEqualTo(idPacket);
        assertThat(packetDTO.getClientCin()).isEqualTo(clientCin);
        assertThat(packetDTO.getColis()).isEqualTo(colis);
        assertThat(packetDTO.getSachets()).isEqualTo(sachets);
        assertThat(packetDTO.getStatus()).isEqualTo(status);
        assertThat(packetDTO.getBordoreau()).isEqualTo(bordoreau);
    }

    @Test
    void testSetters() {
        // Arrange
        PacketDTO packetDTO = new PacketDTO();

        // Act
        packetDTO.setIdPacket(2002L);
        packetDTO.setClientCin("CLIENT456");
        packetDTO.setColis(3);
        packetDTO.setSachets(7);
        packetDTO.setStatus(PacketStatus.TRANSMITTED);
        packetDTO.setBordoreau(3003L);

        // Assert
        assertThat(packetDTO.getIdPacket()).isEqualTo(2002L);
        assertThat(packetDTO.getClientCin()).isEqualTo("CLIENT456");
        assertThat(packetDTO.getColis()).isEqualTo(3);
        assertThat(packetDTO.getSachets()).isEqualTo(7);
        assertThat(packetDTO.getStatus()).isEqualTo(PacketStatus.TRANSMITTED);
        assertThat(packetDTO.getBordoreau()).isEqualTo(3003L);
    }

    @Test
    void testToString() {
        // Arrange
        PacketDTO packetDTO = new PacketDTO(
                3003L,
                "CLIENT789",
                4,
                8,
                PacketStatus.TRANSMITTED,
                4004L
        );

        // Act
        String result = packetDTO.toString();

        // Assert
        assertThat(result).contains("3003", "CLIENT789", "4", "8", "TRANSMITTED", "4004");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        PacketDTO packet1 = new PacketDTO(5005L, "CLIENT111", 1, 1, PacketStatus.IN_TRANSIT, 6006L);
        PacketDTO packet2 = new PacketDTO(5005L, "CLIENT111", 1, 1, PacketStatus.IN_TRANSIT, 6006L);
        PacketDTO packet3 = new PacketDTO(7007L, "CLIENT222", 2, 2, PacketStatus.TRANSMITTED, 8008L);

        // Act & Assert
        assertThat(packet1).isEqualTo(packet2); // Same content
        assertThat(packet1).hasSameHashCodeAs(packet2); // Same hash code
        assertThat(packet1).isNotEqualTo(packet3); // Different content
    }
}
