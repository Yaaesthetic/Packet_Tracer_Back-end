package com.example.packettracerbase.controller;

import com.example.packettracerbase.dto.BordoreauMapper;
import com.example.packettracerbase.dto.BordoreauQRDTO;
import com.example.packettracerbase.dto.UpdateBordoreauRequest;
import com.example.packettracerbase.model.Bordoreau;
import com.example.packettracerbase.model.Driver;
import com.example.packettracerbase.service.BordoreauService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BordoreauController.class)
class BordoreauControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BordoreauService bordoreauService;

    @MockBean
    private BordoreauMapper bordoreauMapper;

    @MockBean
    private com.example.packettracerbase.repository.DriverRepository driverRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Bordoreau bordoreau;

    @BeforeEach
    void setUp() {
        bordoreau = new Bordoreau();
        bordoreau.setBordoreau(1L);
    }

    @Test
    void testGetAllBordoreaux() throws Exception {
        Mockito.when(bordoreauService.getAllBordoreaux()).thenReturn(Collections.singletonList(bordoreau));

        mockMvc.perform(get("/api/bordoreaux"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].bordoreau").value(bordoreau.getBordoreau()));
    }

//    @Test
//    void testUpdateBordoreauForMobile() throws Exception {
//        mockMvc.perform(put("/api/bordoreaux/1/mobile/transit")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("\"NewDriver\""))
//                .andExpect(status().isOk());
//
//        Mockito.verify(bordoreauService).updateStringLivreur(eq(1L), eq("NewDriver"));
//    }

    @Test
    void testGetBordoreauById() throws Exception {
        Mockito.when(bordoreauService.getBordoreauById(1L)).thenReturn(Optional.of(bordoreau));

        mockMvc.perform(get("/api/bordoreaux/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bordoreau").value(bordoreau.getBordoreau()));
    }

//    @Test
//    void testGetBordoreauxByDriver() throws Exception {
//        Driver driver = new Driver();
//        driver.setCinDriver("D123");
//        BordoreauQRDTO bordoreauQRDTO = new BordoreauQRDTO();
//        bordoreauQRDTO.setNumeroBordoreau(1L);
//
//        Mockito.when(driverRepository.findById("D123")).thenReturn(Optional.of(driver));
//        Mockito.when(bordoreauService.getBordereauxByDriver(driver)).thenReturn(Collections.singletonList(bordoreau));
//        Mockito.when(bordoreauMapper.toBordoreauQRDTO(bordoreau)).thenReturn(bordoreauQRDTO);
//
//        mockMvc.perform(get("/api/bordoreaux/Dashboard/D123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].numeroBordoreau").value(1L));
//    }

    @Test
    void testCreateBordoreau() throws Exception {
        Mockito.when(bordoreauService.createBordoreau(any())).thenReturn(bordoreau);

        mockMvc.perform(post("/api/bordoreaux")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bordoreau)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bordoreau").value(bordoreau.getBordoreau()));
    }

    @Test
    void testUpdateBordoreau() throws Exception {
        Bordoreau updatedBordoreau = new Bordoreau();
        updatedBordoreau.setBordoreau(1L);

        Mockito.when(bordoreauService.updateBordoreau(eq(1L), any())).thenReturn(updatedBordoreau);

        mockMvc.perform(put("/api/bordoreaux/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBordoreau)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bordoreau").value(updatedBordoreau.getBordoreau()));
    }

    @Test
    void testDeleteBordoreau() throws Exception {
        mockMvc.perform(delete("/api/bordoreaux/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(bordoreauService).deleteBordoreau(1L);
    }
}
