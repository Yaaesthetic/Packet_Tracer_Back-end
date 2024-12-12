package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BordoreauQRDTOTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long numeroBordoreau = 123L;
        LocalDateTime date = LocalDateTime.now();
        String stringLivreur = "CIN12345";
        Long codeSecteur = 456L;
        List<PacketDetailDTO> packets = new ArrayList<>();
        packets.add(new PacketDetailDTO(1L, "CL123", 10, 5, PacketStatus.INITIALIZED));
        PacketStatus status = PacketStatus.IN_TRANSIT;

        // Act
        BordoreauQRDTO dto = new BordoreauQRDTO(numeroBordoreau, date, stringLivreur, codeSecteur, packets, status);

        // Assert
        assertThat(dto.getNumeroBordoreau()).isEqualTo(numeroBordoreau);
        assertThat(dto.getDate()).isEqualTo(date);
        assertThat(dto.getStringLivreur()).isEqualTo(stringLivreur);
        assertThat(dto.getCodeSecteur()).isEqualTo(codeSecteur);
        assertThat(dto.getPackets()).isEqualTo(packets);
        assertThat(dto.getStatus()).isEqualTo(status);
    }

    @Test
    void testSetters() {
        // Arrange
        BordoreauQRDTO dto = new BordoreauQRDTO();

        Long numeroBordoreau = 123L;
        LocalDateTime date = LocalDateTime.now();
        String stringLivreur = "CIN12345";
        Long codeSecteur = 456L;
        List<PacketDetailDTO> packets = new ArrayList<>();
        packets.add(new PacketDetailDTO(1L, "CL123", 10, 5, PacketStatus.INITIALIZED));
        PacketStatus status = PacketStatus.DONE;

        // Act
        dto.setNumeroBordoreau(numeroBordoreau);
        dto.setDate(date);
        dto.setStringLivreur(stringLivreur);
        dto.setCodeSecteur(codeSecteur);
        dto.setPackets(packets);
        dto.setStatus(status);

        // Assert
        assertThat(dto.getNumeroBordoreau()).isEqualTo(numeroBordoreau);
        assertThat(dto.getDate()).isEqualTo(date);
        assertThat(dto.getStringLivreur()).isEqualTo(stringLivreur);
        assertThat(dto.getCodeSecteur()).isEqualTo(codeSecteur);
        assertThat(dto.getPackets()).isEqualTo(packets);
        assertThat(dto.getStatus()).isEqualTo(status);
    }

//    @Test
//    void testToString() {
//        // Arrange
//        BordoreauQRDTO dto = new BordoreauQRDTO(
//                123L,
//                LocalDateTime.of(2024, 12, 1, 10, 0),
//                "CIN12345",
//                456L,
//                List.of(new PacketDetailDTO(1L, "CL123", 10, 5, PacketStatus.INITIALIZED)),
//                PacketStatus.DONE
//        );
//
//        // Act
//        String result = dto.toString();
//
//        // Assert
//        assertThat(result).contains("123L", "CIN12345", "456L", "INITIALIZED", "DONE");
//    }
}
