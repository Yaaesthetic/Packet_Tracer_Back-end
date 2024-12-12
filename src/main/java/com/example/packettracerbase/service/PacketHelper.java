package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Packet;
import com.example.packettracerbase.model.PacketStatus;
import com.example.packettracerbase.repository.BordoreauRepository;
import com.example.packettracerbase.repository.PacketRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PacketHelper {

    public Packet findPacketById(PacketRepository packetRepository, Long id) {
        return packetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found with id: " + id));
    }

    public void updatePacketDetails(Packet existingPacket, Packet packetDetails) {
        existingPacket.setClient(packetDetails.getClient());
        existingPacket.setColis(packetDetails.getColis());
        existingPacket.setSachets(packetDetails.getSachets());
        existingPacket.setStatus(packetDetails.getStatus());
        existingPacket.setBordoreau(packetDetails.getBordoreau());
        existingPacket.setTransferts(packetDetails.getTransferts());
    }

    public void updateBordoreauStatusIfNeeded(Bordoreau bordoreau, BordoreauRepository bordoreauRepository) {
        if (bordoreau.getPacketsBordoreau() != null) {
            boolean hasPendingPackets = bordoreau.getPacketsBordoreau()
                    .stream()
                    .anyMatch(packet -> packet.getStatus() != PacketStatus.DONE);

            if (!hasPendingPackets) {
                bordoreau.setStatus(PacketStatus.DONE);
                bordoreauRepository.save(bordoreau);
            }
        } else {
            System.out.println("Bordoreau has no packets.");
        }
    }

    public void logError(String message, Exception e) {
        // Replace this with proper logging
        System.err.println(message);
        e.printStackTrace();
    }
}
