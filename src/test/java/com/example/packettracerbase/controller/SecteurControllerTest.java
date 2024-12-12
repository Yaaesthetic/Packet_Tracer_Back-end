package com.example.packettracerbase.controller;

import com.example.packettracerbase.model.Secteur;
import com.example.packettracerbase.service.SecteurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecteurControllerTest {

    @InjectMocks
    private SecteurController secteurController;

    @Mock
    private SecteurService secteurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllSecteurs() {
        List<Secteur> mockSecteurs = new ArrayList<>();
        mockSecteurs.add(new Secteur(1L, "CityA", null));
        mockSecteurs.add(new Secteur(2L, "CityB", null));

        when(secteurService.getAllSecteurs()).thenReturn(mockSecteurs);

        ResponseEntity<List<Secteur>> response = secteurController.getAllSecteurs();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(secteurService, times(1)).getAllSecteurs();
    }

    @Test
    void testGetSecteurById_Success() {
        Secteur mockSecteur = new Secteur(1L, "CityA", null);

        when(secteurService.getSecteurById(1L)).thenReturn(Optional.of(mockSecteur));

        ResponseEntity<Secteur> response = secteurController.getSecteurById(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockSecteur, response.getBody());
        verify(secteurService, times(1)).getSecteurById(1L);
    }

    @Test
    void testGetSecteurById_NotFound() {
        when(secteurService.getSecteurById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Secteur> response = secteurController.getSecteurById(1L);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(secteurService, times(1)).getSecteurById(1L);
    }

    @Test
    void testCreateSecteur() {
        Secteur newSecteur = new Secteur(1L, "CityA", null);
        Secteur createdSecteur = new Secteur(1L, "CityA", null);

        when(secteurService.createSecteur(newSecteur)).thenReturn(createdSecteur);

        ResponseEntity<Secteur> response = secteurController.createSecteur(newSecteur);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(createdSecteur, response.getBody());
        verify(secteurService, times(1)).createSecteur(newSecteur);
    }

    @Test
    void testUpdateSecteur() {
        Secteur existingSecteur = new Secteur(1L, "CityA", null);
        Secteur updatedSecteur = new Secteur(1L, "CityB", null);

        when(secteurService.updateSecteur(1L, updatedSecteur)).thenReturn(updatedSecteur);

        ResponseEntity<Secteur> response = secteurController.updateSecteur(1L, updatedSecteur);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(updatedSecteur, response.getBody());
        verify(secteurService, times(1)).updateSecteur(1L, updatedSecteur);
    }

    @Test
    void testDeleteSecteur() {
        doNothing().when(secteurService).deleteSecteur(1L);

        ResponseEntity<Void> response = secteurController.deleteSecteur(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(secteurService, times(1)).deleteSecteur(1L);
    }
}
