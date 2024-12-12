package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateBordoreauRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long bordoreau = 12345L;
        String date = "2024-12-09T10:00:00";
        String stringLivreur = "Driver001";
        Long codeSecteur = 100L;
        List<UpdatePacketRequest> packets = Arrays.asList(
                new UpdatePacketRequest(1001L, 2001L, 10, 5, PacketStatus.INITIALIZED),
                new UpdatePacketRequest(1002L, 2002L, 15, 7, PacketStatus.TRANSMITTED)
        );
        PacketStatus status = PacketStatus.IN_TRANSIT;

        // Act
        UpdateBordoreauRequest updateBordoreauRequest = new UpdateBordoreauRequest();
        updateBordoreauRequest.setBordoreau(bordoreau);
        updateBordoreauRequest.setDate(date);
        updateBordoreauRequest.setStringLivreur(stringLivreur);
        updateBordoreauRequest.setCodeSecteur(codeSecteur);
        updateBordoreauRequest.setPackets(packets);
        updateBordoreauRequest.setStatus(status);

        // Assert
        assertThat(updateBordoreauRequest.getBordoreau()).isEqualTo(bordoreau);
        assertThat(updateBordoreauRequest.getDate()).isEqualTo(date);
        assertThat(updateBordoreauRequest.getStringLivreur()).isEqualTo(stringLivreur);
        assertThat(updateBordoreauRequest.getCodeSecteur()).isEqualTo(codeSecteur);
        assertThat(updateBordoreauRequest.getPackets()).isEqualTo(packets);
        assertThat(updateBordoreauRequest.getStatus()).isEqualTo(status);
    }

    @Test
    void testSetters() {
        // Arrange
        UpdateBordoreauRequest updateBordoreauRequest = new UpdateBordoreauRequest();

        // Act
        updateBordoreauRequest.setBordoreau(12345L);
        updateBordoreauRequest.setDate("2024-12-09T10:00:00");
        updateBordoreauRequest.setStringLivreur("Driver001");
        updateBordoreauRequest.setCodeSecteur(100L);

        List<UpdatePacketRequest> packets = Arrays.asList(
                new UpdatePacketRequest(1001L, 2001L, 10, 5, PacketStatus.IN_TRANSIT),
                new UpdatePacketRequest(1002L, 2002L, 15, 7, PacketStatus.INITIALIZED)
        );
        updateBordoreauRequest.setPackets(packets);
        updateBordoreauRequest.setStatus(PacketStatus.IN_TRANSIT);

        // Assert
        assertThat(updateBordoreauRequest.getBordoreau()).isEqualTo(12345L);
        assertThat(updateBordoreauRequest.getDate()).isEqualTo("2024-12-09T10:00:00");
        assertThat(updateBordoreauRequest.getStringLivreur()).isEqualTo("Driver001");
        assertThat(updateBordoreauRequest.getCodeSecteur()).isEqualTo(100L);
        assertThat(updateBordoreauRequest.getPackets()).isEqualTo(packets);
        assertThat(updateBordoreauRequest.getStatus()).isEqualTo(PacketStatus.IN_TRANSIT);
    }

    @Test
    void testToString() {
        // Arrange
        List<UpdatePacketRequest> packets = Arrays.asList(
                new UpdatePacketRequest(1001L, 2001L, 10, 5, PacketStatus.IN_TRANSIT),
                new UpdatePacketRequest(1002L, 2002L, 15, 7, PacketStatus.TRANSMITTED)
        );
        UpdateBordoreauRequest updateBordoreauRequest = new UpdateBordoreauRequest(
                12345L,
                "2024-12-09T10:00:00",
                "Driver001",
                100L,
                packets,
                PacketStatus.IN_TRANSIT
        );

        // Act
        String result = updateBordoreauRequest.toString();

        // Assert
        assertThat(result).contains("12345", "2024-12-09T10:00:00", "Driver001", "100", "IN_TRANSIT");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        List<UpdatePacketRequest> packets1 = Arrays.asList(
                new UpdatePacketRequest(1001L, 2001L, 10, 5, PacketStatus.IN_TRANSIT),
                new UpdatePacketRequest(1002L, 2002L, 15, 7, PacketStatus.TRANSMITTED)
        );
        UpdateBordoreauRequest bordoreauRequest1 = new UpdateBordoreauRequest(
                12345L,
                "2024-12-09T10:00:00",
                "Driver001",
                100L,
                packets1,
                PacketStatus.IN_TRANSIT
        );

        List<UpdatePacketRequest> packets2 = Arrays.asList(
                new UpdatePacketRequest(1001L, 2001L, 10, 5, PacketStatus.IN_TRANSIT),
                new UpdatePacketRequest(1002L, 2002L, 15, 7, PacketStatus.TRANSMITTED)
        );
        UpdateBordoreauRequest bordoreauRequest2 = new UpdateBordoreauRequest(
                12345L,
                "2024-12-09T10:00:00",
                "Driver001",
                100L,
                packets2,
                PacketStatus.IN_TRANSIT
        );

        List<UpdatePacketRequest> packets3 = Arrays.asList(
                new UpdatePacketRequest(2001L, 3001L, 12, 6, PacketStatus.IN_TRANSIT)
        );
        UpdateBordoreauRequest bordoreauRequest3 = new UpdateBordoreauRequest(
                67890L,
                "2024-12-10T11:00:00",
                "Driver002",
                101L,
                packets3,
                PacketStatus.IN_TRANSIT
        );

        // Act & Assert
        assertThat(bordoreauRequest1).isEqualTo(bordoreauRequest2);  // Same content
        assertThat(bordoreauRequest1).hasSameHashCodeAs(bordoreauRequest2);  // Same hash code
        assertThat(bordoreauRequest1).isNotEqualTo(bordoreauRequest3);  // Different content
    }
}
