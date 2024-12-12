package com.example.packettracerbase.dto;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransfertRequestTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String codeSecteur = "Secteur123";
        String idDriver = "Driver001";
        Set<Long> packets = new HashSet<>();
        packets.add(1001L);
        packets.add(1002L);

        // Act
        TransfertRequest transfertRequest = new TransfertRequest(codeSecteur, idDriver, packets);

        // Assert
        assertThat(transfertRequest.getCodeSecteur()).isEqualTo(codeSecteur);
        assertThat(transfertRequest.getIdDriver()).isEqualTo(idDriver);
        assertThat(transfertRequest.getPackets()).containsExactlyInAnyOrder(1001L, 1002L);
    }

    @Test
    void testSetters() {
        // Arrange
        Set<Long> packets = new HashSet<>();
        packets.add(2001L);
        packets.add(2002L);
        TransfertRequest transfertRequest = new TransfertRequest("Secteur456","Driver002",packets);


        // Assert
        assertThat(transfertRequest.getCodeSecteur()).isEqualTo("Secteur456");
        assertThat(transfertRequest.getIdDriver()).isEqualTo("Driver002");
        assertThat(transfertRequest.getPackets()).containsExactlyInAnyOrder(2001L, 2002L);
    }

    @Test
    void testToString() {
        // Arrange
        Set<Long> packets = new HashSet<>();
        packets.add(1001L);
        packets.add(1002L);
        TransfertRequest transfertRequest = new TransfertRequest("Secteur123", "Driver001", packets);

        // Act
        String result = transfertRequest.toString();

        // Assert
        assertThat(result).contains("Secteur123", "Driver001", "1001", "1002");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Set<Long> packets1 = new HashSet<>();
        packets1.add(1001L);
        packets1.add(1002L);
        TransfertRequest transfert1 = new TransfertRequest("Secteur123", "Driver001", packets1);

        Set<Long> packets2 = new HashSet<>();
        packets2.add(1001L);
        packets2.add(1002L);
        TransfertRequest transfert2 = new TransfertRequest("Secteur123", "Driver001", packets2);

        Set<Long> packets3 = new HashSet<>();
        packets3.add(2001L);
        packets3.add(2002L);
        TransfertRequest transfert3 = new TransfertRequest("Secteur456", "Driver002", packets3);

        // Act & Assert
        assertThat(transfert1).isEqualTo(transfert2);  // Same content
        assertThat(transfert1).hasSameHashCodeAs(transfert2);  // Same hash code
        assertThat(transfert1).isNotEqualTo(transfert3);  // Different content
    }
}
