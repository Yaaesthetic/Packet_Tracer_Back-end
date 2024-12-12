package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;

import java.util.List;
import java.util.Optional;

public interface BordoreauService {
    List<Bordoreau> getAllBordoreaux();
    Optional<Bordoreau> getBordoreauById(Long id);
    Bordoreau createBordoreau(Bordoreau bordoreau);
    Bordoreau updateBordoreau(Long id, Bordoreau bordoreauDetails);
    void deleteBordoreau(Long id);
    public BordoreauQRDTO getBordoreauForQR(Long bordoreauId);

    Bordoreau updateBordoreau1(Long id, UpdateBordoreauRequest updateRequest);
    public void processBordoreau(String bordereauData);

    Bordoreau updateLivreur(Long bordoreauId, String newDriverId);

    String getAllBordoreauxAsJson();

    void updateStringLivreur(Long id, String newStringLivreur);

    List<Bordoreau> getBordereauxByDriver(Driver driver);
}
