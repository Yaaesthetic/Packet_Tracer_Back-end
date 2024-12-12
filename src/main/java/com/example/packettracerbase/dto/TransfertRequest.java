package com.example.packettracerbase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
@AllArgsConstructor
@Data
public class TransfertRequest {
    private String codeSecteur;
    private String idDriver;
    private Set<Long> packets;

}
