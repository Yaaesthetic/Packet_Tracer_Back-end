package com.example.packettracerbase.service;


import com.example.packettracerbase.model.Admin;
import com.example.packettracerbase.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class AdminServiceImplTest {
    private AdminRepository adminRepository;
    private AdminServiceImpl adminService;

    @BeforeEach
    void setUp() {
        adminRepository = mock(AdminRepository.class);
        adminService = new AdminServiceImpl(adminRepository);
    }

    @Test
    void getAllAdmins_ShouldReturnAdminList_WhenAdminsExist() {
        Admin admin1 = Admin.builder().cinAdmin("123").username("admin1").build();
        Admin admin2 = Admin.builder().cinAdmin("456").username("admin2").build();
        when(adminRepository.findAll()).thenReturn(Arrays.asList(admin1, admin2));

        List<Admin> result = adminService.getAllAdmins();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAllAdmins_ShouldThrowException_WhenNoAdminsExist() {
        when(adminRepository.findAll()).thenReturn(List.of());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, adminService::getAllAdmins);

        assertEquals("No admins found in the database", exception.getMessage());
        verify(adminRepository, times(1)).findAll();
    }

    @Test
    void getAdminById_ShouldReturnAdmin_WhenAdminExists() {
        Admin admin = Admin.builder().cinAdmin("123").username("admin1").build();
        when(adminRepository.findById("123")).thenReturn(Optional.of(admin));

        Optional<Admin> result = adminService.getAdminById("123");

        assertTrue(result.isPresent());
        assertEquals("admin1", result.get().getUsername());
        verify(adminRepository, times(1)).findById("123");
    }

    @Test
    void getAdminById_ShouldThrowException_WhenAdminDoesNotExist() {
        when(adminRepository.findById("123")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> adminService.getAdminById("123"));

        assertEquals("Admin not found with id: 123", exception.getMessage());
        verify(adminRepository, times(1)).findById("123");
    }

    @Test
    void createAdmin_ShouldSaveAdmin() {
        Admin admin = Admin.builder().cinAdmin("123").username("admin1").build();
        when(adminRepository.save(admin)).thenReturn(admin);

        Admin result = adminService.createAdmin(admin);

        assertNotNull(result);
        assertEquals("admin1", result.getUsername());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void updateAdmin_ShouldUpdateAndReturnAdmin_WhenAdminExists() {
        Admin existingAdmin = Admin.builder().cinAdmin("123").username("oldAdmin").build();
        Admin updatedDetails = Admin.builder().cinAdmin("123").username("newAdmin").build();
        when(adminRepository.findById("123")).thenReturn(Optional.of(existingAdmin));
        when(adminRepository.save(existingAdmin)).thenReturn(updatedDetails);

        Admin result = adminService.updateAdmin("123", updatedDetails);

        assertNotNull(result);
        assertEquals("newAdmin", result.getUsername());
        verify(adminRepository, times(1)).findById("123");
        verify(adminRepository, times(1)).save(existingAdmin);
    }

    @Test
    void updateAdmin_ShouldThrowException_WhenAdminDoesNotExist() {
        Admin updatedDetails = Admin.builder().cinAdmin("123").username("newAdmin").build();
        when(adminRepository.findById("123")).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> adminService.updateAdmin("123", updatedDetails));

        assertEquals("Admin not found with id: 123", exception.getMessage());
        verify(adminRepository, times(1)).findById("123");
    }

    @Test
    void deleteAdmin_ShouldDeleteAdmin_WhenAdminExists() {
        when(adminRepository.existsById("123")).thenReturn(true);

        adminService.deleteAdmin("123");

        verify(adminRepository, times(1)).existsById("123");
        verify(adminRepository, times(1)).deleteById("123");
    }

    @Test
    void deleteAdmin_ShouldThrowException_WhenAdminDoesNotExist() {
        when(adminRepository.existsById("123")).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> adminService.deleteAdmin("123"));

        assertEquals("Admin not found with id: 123", exception.getMessage());
        verify(adminRepository, times(1)).existsById("123");
    }
}