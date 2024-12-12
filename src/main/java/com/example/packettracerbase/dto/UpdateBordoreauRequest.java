package com.example.packettracerbase.dto;

import com.example.packettracerbase.model.PacketStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBordoreauRequest {

    @JsonProperty("numeroBordoreau")
    private Long bordoreau;

    @JsonProperty("date")
    private String date;

    @JsonProperty("stringLivreur")
    private String stringLivreur;

    @JsonProperty("codeSecteur")
    private Long codeSecteur;

    @JsonProperty("packets")
    private List<UpdatePacketRequest> packets;

    @JsonProperty("status")
    private PacketStatus status;

    @Override
    public String toString() {
        return "UpdateBordoreauRequest{" +
                "bordoreau=" + bordoreau +
                ", date='" + date + '\'' +
                ", stringLivreur='" + stringLivreur + '\'' +
                ", codeSecteur=" + codeSecteur +
                ", packets=" + packets.stream().map(UpdatePacketRequest::toString).collect(Collectors.joining(", ")) +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateBordoreauRequest that = (UpdateBordoreauRequest) o;
        return Objects.equals(bordoreau, that.bordoreau) &&
                Objects.equals(date, that.date) &&
                Objects.equals(stringLivreur, that.stringLivreur) &&
                Objects.equals(codeSecteur, that.codeSecteur) &&
                Objects.equals(packets, that.packets) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bordoreau, date, stringLivreur, codeSecteur, packets, status);
    }
}
