package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BordoreauMapperTest {

    private BordoreauMapper bordoreauMapper;

    @BeforeEach
    void setUp() {
        bordoreauMapper = new BordoreauMapper();
    }

    @Test
    void testToBordoreauQRDTO() {
        // Setup test data
        Driver driver = Driver.builder()
                .cinDriver("CIN123")
                .firstName("John")
                .lastName("Doe")
                .build();

        Secteur secteur = Secteur.builder()
                .idSecteur(1L)
                .build();

        Client client = Client.builder()
                .cinClient("CIN456")
                .build();

        Packet packet = Packet.builder()
                .idPacket(101L)
                .client(client)
                .colis(3)
                .sachets(5)
                .status(PacketStatus.IN_TRANSIT)
                .build();

        Set<Packet> packets = new HashSet<>();
        packets.add(packet);

        Bordoreau bordoreau = Bordoreau.builder()
                .bordoreau(1001L)
                .date(LocalDateTime.now())
                .status(PacketStatus.IN_TRANSIT)
                .livreur(driver)
                .secteur(secteur)
                .packetsBordoreau(packets)
                .build();

        // Act
        BordoreauQRDTO bordoreauQRDTO = bordoreauMapper.toBordoreauQRDTO(bordoreau);

        // Assert
        assertNotNull(bordoreauQRDTO);
        assertEquals(bordoreau.getBordoreau(), bordoreauQRDTO.getNumeroBordoreau());
        assertEquals(bordoreau.getDate(), bordoreauQRDTO.getDate());
        assertEquals(bordoreau.getLivreur().getCinDriver(), bordoreauQRDTO.getStringLivreur());
        assertEquals(bordoreau.getSecteur().getIdSecteur(), bordoreauQRDTO.getCodeSecteur());
        assertEquals(bordoreau.getStatus(), bordoreauQRDTO.getStatus());
        assertNotNull(bordoreauQRDTO.getPackets());
        assertEquals(1, bordoreauQRDTO.getPackets().size());

        PacketDetailDTO packetDetailDTO = bordoreauQRDTO.getPackets().get(0);
        assertEquals(packet.getIdPacket(), packetDetailDTO.getNumeroBL());
        assertEquals(packet.getClient().getCinClient(), packetDetailDTO.getCodeClient());
        assertEquals(packet.getColis(), packetDetailDTO.getNbrColis());
        assertEquals(packet.getSachets(), packetDetailDTO.getNbrSachets());
        assertEquals(packet.getStatus(), packetDetailDTO.getStatus());
    }
}
