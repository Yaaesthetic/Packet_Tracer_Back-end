package com.example.packettracerbase.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void testClientBuilder() {
        Location location = new Location();
        Client client = Client.builder()
                .cinClient("54321")
                .username("client_user")
                .password("securePassword")
                .firstName("Client")
                .lastName("Test")
                .email("client@test.com")
                .Location_Client(location)
                .isActive(true)
                .build();

        assertNotNull(client);
        assertEquals("54321", client.getCinClient());
        assertEquals(Role.Client, client.getRole());
        assertEquals(location, client.getLocation_Client());
    }

    @Test
    void testEqualsAndHashCode() {
        Client client1 = Client.builder().cinClient("54321").build();
        Client client2 = Client.builder().cinClient("54321").build();

        assertEquals(client1, client2);
        assertEquals(client1.hashCode(), client2.hashCode());
    }

    @Test
    void testSuperBuilder() {
        // Using Builder pattern for creating the Client object
        Client client = Client.builder()
                .cinClient("456")
                .username("client1")
                .password("password")
                .isActive(true)
                .build();

        assertNotNull(client);
        assertEquals("456", client.getCinClient());
        assertTrue(client.isActive());
        assertEquals("client1", client.getUsername());
    }

    @Test
    void testEqualsAndHashCode2() {
        Client client1 = Client.builder().cinClient("456").build();
        Client client2 = Client.builder().cinClient("456").build();

        assertEquals(client1, client2);
        assertEquals(client1.hashCode(), client2.hashCode());
    }
}
