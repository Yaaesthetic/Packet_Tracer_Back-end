package com.example.packettracerbase.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

class TransfertDesktopTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long idTransfert = 1L;
        String oldPerson = "John Doe";
        String newPerson = "Jane Smith";
        LocalDateTime time = LocalDateTime.now();
        Long packet = 1001L;

        // Act
        TransfertDesktop transfertDesktop = new TransfertDesktop(idTransfert, oldPerson, newPerson, time, packet);

        // Assert
        assertThat(transfertDesktop.getIdTransfert()).isEqualTo(idTransfert);
        assertThat(transfertDesktop.getOldPerson()).isEqualTo(oldPerson);
        assertThat(transfertDesktop.getNewPerson()).isEqualTo(newPerson);
        assertThat(transfertDesktop.getTime()).isEqualTo(time);
        assertThat(transfertDesktop.getPacket()).isEqualTo(packet);
    }

    @Test
    void testSetters() {
        // Arrange
        TransfertDesktop transfertDesktop = new TransfertDesktop();

        // Act
        transfertDesktop.setIdTransfert(2L);
        transfertDesktop.setOldPerson("Alice Johnson");
        transfertDesktop.setNewPerson("Bob Brown");
        transfertDesktop.setTime(LocalDateTime.now().minusDays(1));
        transfertDesktop.setPacket(2002L);

        // Assert
        assertThat(transfertDesktop.getIdTransfert()).isEqualTo(2L);
        assertThat(transfertDesktop.getOldPerson()).isEqualTo("Alice Johnson");
        assertThat(transfertDesktop.getNewPerson()).isEqualTo("Bob Brown");
        assertThat(transfertDesktop.getTime()).isNotNull();  // Time should be set
        assertThat(transfertDesktop.getPacket()).isEqualTo(2002L);
    }

    @Test
    void testToString() {
        // Arrange
        LocalDateTime time = LocalDateTime.of(2024, 12, 9, 10, 30, 0, 0);
        TransfertDesktop transfertDesktop = new TransfertDesktop(1L, "Alice", "Bob", time, 3003L);

        // Act
        String result = transfertDesktop.toString();

        // Assert
        assertThat(result).contains("1", "Alice", "Bob", time.toString(), "3003");
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        TransfertDesktop transfert1 = new TransfertDesktop(1L, "Alice", "Bob", LocalDateTime.now(), 4004L);
        TransfertDesktop transfert2 = new TransfertDesktop(1L, "Alice", "Bob", LocalDateTime.now(), 4004L);
        TransfertDesktop transfert3 = new TransfertDesktop(2L, "Charlie", "David", LocalDateTime.now(), 5005L);

        // Act & Assert
        assertThat(transfert1).isEqualTo(transfert2);  // Same content
        assertThat(transfert1).hasSameHashCodeAs(transfert2);  // Same hash code
        assertThat(transfert1).isNotEqualTo(transfert3);  // Different content
    }
}
