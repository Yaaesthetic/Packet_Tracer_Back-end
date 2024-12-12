package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.PacketDetailDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.*;
import com.example.packettracerbase.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BordoreauHelper {

    private final BordoreauRepository bordoreauRepository;
    private final PacketRepository packetRepository;
    private final ClientRepository clientRepository;
    private final SecteurRepository secteurRepository;
    private final DriverRepository driverRepository;

    @Autowired
    public BordoreauHelper(
            BordoreauRepository bordoreauRepository,
            PacketRepository packetRepository,
            ClientRepository clientRepository,
            SecteurRepository secteurRepository,
            DriverRepository driverRepository) {
        this.bordoreauRepository = bordoreauRepository;
        this.packetRepository = packetRepository;
        this.clientRepository = clientRepository;
        this.secteurRepository = secteurRepository;
        this.driverRepository = driverRepository;
    }

    public Bordoreau findById(Long id) {
        return bordoreauRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bordoreau not found with id: " + id));
    }

    public Bordoreau updateBordoreauFields(Long id, Bordoreau updates) {
        Bordoreau existing = findById(id);
        existing.setDate(updates.getDate());
        existing.setLivreur(updates.getLivreur());
        existing.setSecteur(updates.getSecteur());
        existing.setPacketsBordoreau(updates.getPacketsBordoreau());
        existing.setSender(updates.getSender());
        existing.setStatus(updates.getStatus());
        return bordoreauRepository.save(existing);
    }

    public BordoreauQRDTO generateBordoreauQR(Long id) {
        Bordoreau bordoreau = findById(id);
        return mapToQRDTO(bordoreau);
    }

    public Bordoreau updateBordoreauWithPackets(Long id, UpdateBordoreauRequest request) {
        Bordoreau bordoreau = findById(id);
        bordoreau.setStatus(request.getStatus());
        Set<Packet> updatedPackets = request.getPackets().stream()
                .map(packetRequest -> {
                    Packet packet = packetRepository.findById(packetRequest.getIdPacket())
                            .orElseThrow(() -> new RuntimeException("Packet not found with id: " + packetRequest.getIdPacket()));
                    packet.setStatus(packetRequest.getStatus());
                    return packet;
                })
                .collect(Collectors.toSet());
        bordoreau.setPacketsBordoreau(updatedPackets);
        return bordoreauRepository.save(bordoreau);
    }

    public void processQRData(String data) {
        try {
            Bordoreau bordoreau = convertQRcodeToObjects(data);
            if (bordoreau != null) {
                bordoreauRepository.save(bordoreau);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bordoreau updateLivreur(Long bordoreauId, String newDriverId) {
        Bordoreau bordoreau = findById(bordoreauId);
        Driver newDriver = findDriverById(newDriverId);
        bordoreau.setLivreur(newDriver);
        return bordoreauRepository.save(bordoreau);
    }

    public String serializeBordereauxToJson() {
        try {
            return new ObjectMapper().writeValueAsString(bordoreauRepository.findAll());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing bordereaux to JSON", e);
        }
    }

    private Bordoreau convertQRcodeToObjects(String dataString) throws Exception {
        String[] parts = dataString.replaceAll("[{}]", "").split(",");
        if (parts.length < 5) {
            throw new IllegalArgumentException("Input data must have at least 5 parts.");
        }

        Long idBordoreau = Long.parseLong(parts[0].trim());
        String dateStr = parts[1].trim();
        String codeLivreur = parts[2].trim();
        String codeSecteur = parts[3].trim();

        int year = Integer.parseInt(dateStr.substring(4, 6)) + 2000;
        int month = Integer.parseInt(dateStr.substring(2, 4));
        int day = Integer.parseInt(dateStr.substring(0, 2));
        LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0);

        Bordoreau bordoreau = new Bordoreau();
        bordoreau.setBordoreau(idBordoreau);
        bordoreau.setDate(date);

        Optional<Driver> driver = driverRepository.findById(codeLivreur);
        driver.ifPresentOrElse(bordoreau::setLivreur, () -> {
            throw new NoSuchElementException("Driver not found for code: " + codeLivreur);
        });

        Secteur secteur = secteurRepository.findById(Long.parseLong(codeSecteur))
                .orElseGet(() -> secteurRepository.save(new Secteur(Long.parseLong(codeSecteur), null, null)));
        bordoreau.setSecteur(secteur);
        bordoreau.setStatus(PacketStatus.INITIALIZED);
        bordoreau = bordoreauRepository.save(bordoreau);

        List<Packet> packets = new ArrayList<>();
        for (int i = 4; i < parts.length; i += 4) {
            Long idPacket = Long.parseLong(parts[i].trim());
            Long codeClient = Long.parseLong(parts[i + 1].trim());
            int colis = Integer.parseInt(parts[i + 2].trim());
            int sachets = Integer.parseInt(parts[i + 3].trim());

            Packet packet = new Packet();
            packet.setIdPacket(idPacket);
            Client client = clientRepository.findById(codeClient.toString())
                    .orElseGet(() -> clientRepository.save(new Client(Long.toString(codeClient), null, null, null)));
            packet.setClient(client);
            packet.setColis(colis);
            packet.setSachets(sachets);
            packet.setStatus(PacketStatus.INITIALIZED);
            packets.add(packetRepository.save(packet));
        }
        bordoreau.setPacketsBordoreau(new HashSet<>(packets));
        Bordoreau finalBordoreau = bordoreau;
        packets.forEach(packet -> packet.setBordoreau(finalBordoreau));
        return bordoreau;
    }

    private BordoreauQRDTO mapToQRDTO(Bordoreau bordoreau) {
        if (bordoreau == null) throw new IllegalArgumentException("Bordoreau object cannot be null.");
        BordoreauQRDTO qrDTO = new BordoreauQRDTO();
        qrDTO.setNumeroBordoreau(bordoreau.getBordoreau());
        qrDTO.setDate(bordoreau.getDate());
        qrDTO.setStringLivreur(bordoreau.getLivreur().getCinDriver());
        qrDTO.setCodeSecteur(bordoreau.getSecteur().getIdSecteur());
        qrDTO.setStatus(bordoreau.getStatus());
        qrDTO.setPackets(
                bordoreau.getPacketsBordoreau().stream()
                        .map(this::mapPacketToDetailDTO)
                        .collect(Collectors.toList())
        );
        return qrDTO;
    }

    private PacketDetailDTO mapPacketToDetailDTO(Packet packet) {
        PacketDetailDTO detail = new PacketDetailDTO();
        detail.setNumeroBL(packet.getIdPacket());
        detail.setCodeClient(packet.getClient().getCinClient());
        detail.setNbrColis(packet.getColis());
        detail.setNbrSachets(packet.getSachets());
        return detail;
    }

    private Driver findDriverById(String id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Driver not found with id: " + id));
    }
}
