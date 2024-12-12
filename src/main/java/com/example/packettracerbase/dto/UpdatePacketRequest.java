package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePacketRequest {

    @JsonProperty("numeroBL")
    private Long idPacket;

    @JsonProperty("codeClient")
    private Long codeClient;

    @JsonProperty("nbrColis")
    private int colis;

    @JsonProperty("nbrSachets")
    private int sachets;

    @JsonProperty("status")
    private PacketStatus status;

    @Override
    public String toString() {
        return "UpdatePacketRequest{" +
                "idPacket=" + idPacket +
                ", codeClient=" + codeClient +
                ", colis=" + colis +
                ", sachets=" + sachets +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdatePacketRequest that = (UpdatePacketRequest) o;
        return colis == that.colis &&
                sachets == that.sachets &&
                Objects.equals(idPacket, that.idPacket) &&
                Objects.equals(codeClient, that.codeClient) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPacket, codeClient, colis, sachets, status);
    }
}
