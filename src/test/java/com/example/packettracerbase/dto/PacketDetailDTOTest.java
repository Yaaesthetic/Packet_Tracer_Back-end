package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PacketDetailDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long numeroBL = 1001L;
        String codeClient = "CLIENT123";
        int nbrColis = 2;
        int nbrSachets = 5;
        PacketStatus status = PacketStatus.IN_TRANSIT;

        // Act
        PacketDetailDTO packetDetailDTO = new PacketDetailDTO(numeroBL, codeClient, nbrColis, nbrSachets, status);

        // Assert
        assertThat(packetDetailDTO.getNumeroBL()).isEqualTo(numeroBL);
        assertThat(packetDetailDTO.getCodeClient()).isEqualTo(codeClient);
        assertThat(packetDetailDTO.getNbrColis()).isEqualTo(nbrColis);
        assertThat(packetDetailDTO.getNbrSachets()).isEqualTo(nbrSachets);
        assertThat(packetDetailDTO.getStatus()).isEqualTo(status);
    }

    @Test
    void testSetters() {
        // Arrange
        PacketDetailDTO packetDetailDTO = new PacketDetailDTO();

        // Act
        packetDetailDTO.setNumeroBL(2002L);
        packetDetailDTO.setCodeClient("CLIENT456");
        packetDetailDTO.setNbrColis(3);
        packetDetailDTO.setNbrSachets(7);
        packetDetailDTO.setStatus(PacketStatus.TRANSMITTED);

        // Assert
        assertThat(packetDetailDTO.getNumeroBL()).isEqualTo(2002L);
        assertThat(packetDetailDTO.getCodeClient()).isEqualTo("CLIENT456");
        assertThat(packetDetailDTO.getNbrColis()).isEqualTo(3);
        assertThat(packetDetailDTO.getNbrSachets()).isEqualTo(7);
        assertThat(packetDetailDTO.getStatus()).isEqualTo(PacketStatus.TRANSMITTED);
    }

    @Test
    void testToString() {
        // Arrange
        PacketDetailDTO packetDetailDTO = new PacketDetailDTO(
                3003L,
                "CLIENT789",
                4,
                8,
                PacketStatus.TRANSMITTED
        );

        // Act
        String result = packetDetailDTO.toString();

        // Assert
        assertThat(result).contains("3003", "CLIENT789", "4", "8", "TRANSMITTED");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        PacketDetailDTO packet1 = new PacketDetailDTO(4004L, "CLIENT111", 1, 1, PacketStatus.IN_TRANSIT);
        PacketDetailDTO packet2 = new PacketDetailDTO(4004L, "CLIENT111", 1, 1, PacketStatus.IN_TRANSIT);
        PacketDetailDTO packet3 = new PacketDetailDTO(5005L, "CLIENT222", 2, 2, PacketStatus.TRANSMITTED);

        // Act & Assert
        assertThat(packet1).isEqualTo(packet2); // Same content
        assertThat(packet1).hasSameHashCodeAs(packet2); // Same hash code
        assertThat(packet1).isNotEqualTo(packet3); // Different content
    }
}
