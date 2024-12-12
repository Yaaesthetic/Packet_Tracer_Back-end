package com.example.packettracerbase.service;

import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.*;
import com.example.packettracerbase.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BordoreauServiceImplTest {

    @Mock
    private BordoreauRepository bordoreauRepository;

    @Mock
    private BordoreauHelper bordoreauHelper;

    @Mock
    private DriverHelper driverHelper;

    @InjectMocks
    private BordoreauServiceImpl bordoreauService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBordoreaux() {
        when(bordoreauRepository.findAll()).thenReturn(List.of(new Bordoreau()));

        List<Bordoreau> bordereaux = bordoreauService.getAllBordoreaux();

        assertEquals(1, bordereaux.size());
        verify(bordoreauRepository, times(1)).findAll();
    }

    @Test
    void getBordoreauById() {
        Bordoreau bordoreau = new Bordoreau();
        bordoreau.setBordoreau(1L);
        when(bordoreauRepository.findById(1L)).thenReturn(Optional.of(bordoreau));

        Optional<Bordoreau> result = bordoreauService.getBordoreauById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getBordoreau());
    }

    @Test
    void createBordoreau() {
        Bordoreau bordoreau = new Bordoreau();
        when(bordoreauRepository.save(bordoreau)).thenReturn(bordoreau);

        Bordoreau created = bordoreauService.createBordoreau(bordoreau);

        assertNotNull(created);
        verify(bordoreauRepository, times(1)).save(bordoreau);
    }

    @Test
    void updateStringLivreur() {
        Bordoreau bordoreau = new Bordoreau();
        Driver driver = new Driver();
        driver.setCinDriver("123");

        when(driverHelper.findById("123")).thenReturn(driver);
        when(bordoreauRepository.findById(1L)).thenReturn(Optional.of(bordoreau));

        bordoreauService.updateStringLivreur(1L, "123");

        verify(bordoreauRepository, times(1)).save(bordoreau);
        assertEquals(driver, bordoreau.getLivreur());
    }

    @Test
    void deleteBordoreau() {
        when(bordoreauRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bordoreauRepository).deleteById(1L);

        bordoreauService.deleteBordoreau(1L);

        verify(bordoreauRepository, times(1)).deleteById(1L);
    }

    @Test
    void getBordoreauForQR() {
        BordoreauQRDTO qrDTO = new BordoreauQRDTO();
        when(bordoreauHelper.generateBordoreauQR(1L)).thenReturn(qrDTO);

        BordoreauQRDTO result = bordoreauService.getBordoreauForQR(1L);

        assertNotNull(result);
        verify(bordoreauHelper, times(1)).generateBordoreauQR(1L);
    }

    @Test
    void updateBordoreau1() {
        UpdateBordoreauRequest updateRequest = new UpdateBordoreauRequest();
        Bordoreau updatedBordoreau = new Bordoreau();

        when(bordoreauHelper.updateBordoreauWithPackets(1L, updateRequest)).thenReturn(updatedBordoreau);

        Bordoreau result = bordoreauService.updateBordoreau1(1L, updateRequest);

        assertNotNull(result);
        verify(bordoreauHelper, times(1)).updateBordoreauWithPackets(1L, updateRequest);
    }

    @Test
    void processBordoreau() {
        String qrData = "sampleData";
        doNothing().when(bordoreauHelper).processQRData(qrData);

        bordoreauService.processBordoreau(qrData);

        verify(bordoreauHelper, times(1)).processQRData(qrData);
    }

    @Test
    void updateLivreur() {
        Bordoreau bordoreau = new Bordoreau(); // Create test Bordoreau object
        Driver driver = new Driver();         // Create test Driver object

        // Mock behavior of helper and repository
        when(driverHelper.findById("123")).thenReturn(driver);
        when(bordoreauRepository.findById(1L)).thenReturn(Optional.of(bordoreau));
        when(bordoreauRepository.save(any(Bordoreau.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the method under test
        Bordoreau updated = bordoreauService.updateLivreur(1L, "123");

        // Assertions to validate behavior
        assertNotNull(updated, "Updated Bordoreau should not be null");
        assertEquals(driver, updated.getLivreur(), "The livreur should be updated to the new driver");
        verify(bordoreauRepository, times(1)).save(bordoreau);
    }


    @Test
    void getAllBordoreauxAsJson() {
        String json = "{}";
        when(bordoreauHelper.serializeBordereauxToJson()).thenReturn(json);

        String result = bordoreauService.getAllBordoreauxAsJson();

        assertNotNull(result);
        verify(bordoreauHelper, times(1)).serializeBordereauxToJson();
    }

    @Test
    void getBordereauxByDriver() {
        Driver driver = new Driver();
        List<Bordoreau> bordereaux = List.of(new Bordoreau());

        when(bordoreauRepository.findByLivreur(driver)).thenReturn(bordereaux);

        List<Bordoreau> result = bordoreauService.getBordereauxByDriver(driver);

        assertEquals(1, result.size());
        verify(bordoreauRepository, times(1)).findByLivreur(driver);
    }

    @Test
    void updateBordoreauFields() {
        Bordoreau bordoreauDetails = new Bordoreau();
        Bordoreau updatedBordoreau = new Bordoreau();

        when(bordoreauHelper.updateBordoreauFields(1L, bordoreauDetails)).thenReturn(updatedBordoreau);

        Bordoreau result = bordoreauService.updateBordoreau(1L, bordoreauDetails);

        assertNotNull(result);
        verify(bordoreauHelper, times(1)).updateBordoreauFields(1L, bordoreauDetails);
    }

    @Test
    void deleteBordoreau_NotFound() {
        when(bordoreauRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> bordoreauService.deleteBordoreau(1L));

        assertEquals("Bordoreau not found with id: 1", exception.getMessage());
        verify(bordoreauRepository, never()).deleteById(1L);
    }

}
