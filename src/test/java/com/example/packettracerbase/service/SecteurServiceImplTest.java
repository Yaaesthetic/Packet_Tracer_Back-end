package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Secteur;
import com.example.packettracerbase.repository.SecteurRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SecteurServiceImplTest {

    private SecteurRepository secteurRepository;
    private SecteurServiceImpl secteurService;

    @BeforeEach
    void setUp() {
        secteurRepository = mock(SecteurRepository.class);
        secteurService = new SecteurServiceImpl(secteurRepository);
    }

    @Test
    void getAllSecteurs_ShouldReturnListOfSecteurs() {
        List<Secteur> secteurs = List.of(new Secteur());
        when(secteurRepository.findAll()).thenReturn(secteurs);

        List<Secteur> result = secteurService.getAllSecteurs();

        assertEquals(1, result.size());
        verify(secteurRepository, times(1)).findAll();
    }

    @Test
    void getSecteurById_ShouldReturnSecteur_WhenExists() {
        Secteur secteur = new Secteur();
        secteur.setIdSecteur(1L);
        when(secteurRepository.findById(1L)).thenReturn(Optional.of(secteur));

        Optional<Secteur> result = secteurService.getSecteurById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdSecteur());
        verify(secteurRepository, times(1)).findById(1L);
    }

    @Test
    void getSecteurById_ShouldThrowException_WhenNotFound() {
        when(secteurRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> secteurService.getSecteurById(1L));
    }

    @Test
    void createSecteur_ShouldSaveAndReturnSecteur() {
        Secteur secteur = new Secteur();
        when(secteurRepository.save(secteur)).thenReturn(secteur);

        Secteur result = secteurService.createSecteur(secteur);

        assertNotNull(result);
        verify(secteurRepository, times(1)).save(secteur);
    }

    @Test
    void updateSecteur_ShouldUpdateAndReturnSecteur() {
        Secteur existingSecteur = new Secteur();
        existingSecteur.setIdSecteur(1L);
        existingSecteur.setCity("Old City");

        Secteur updatedSecteur = new Secteur();
        updatedSecteur.setCity("New City");

        when(secteurRepository.findById(1L)).thenReturn(Optional.of(existingSecteur));
        when(secteurRepository.save(existingSecteur)).thenReturn(existingSecteur);

        Secteur result = secteurService.updateSecteur(1L, updatedSecteur);

        assertEquals("New City", result.getCity());
        verify(secteurRepository, times(1)).save(existingSecteur);
    }

    @Test
    void deleteSecteur_ShouldCallRepository_WhenExists() {
        when(secteurRepository.existsById(1L)).thenReturn(true);

        secteurService.deleteSecteur(1L);

        verify(secteurRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteSecteur_ShouldThrowException_WhenNotFound() {
        when(secteurRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> secteurService.deleteSecteur(1L));
    }
}
