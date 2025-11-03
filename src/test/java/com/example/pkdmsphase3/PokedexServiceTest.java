package com.example.pkdmsphase3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PokedexServiceTest {

    private PokedexService service;

    @BeforeEach
    void setUp() {
        // Initialize a fresh PokedexService instance before each test
        service = new PokedexService();
    }

    @Test
    void testAddPokemon() {
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "A small turtle Pokémon.", true);
        String result = service.addPokemon(squirtle);
        assertTrue(result.contains("added successfully"));

        // Verify Pokémon can be retrieved using the helper method
        Pokemon retrieved = service.getPokemonByDexNumber(7);
        assertEquals(squirtle, retrieved, "Squirtle should be saved in repository.");
    }

    @Test
    void testUpdatePokemon() {
        Pokemon eevee = new Pokemon(133, "Eevee", "Kanto", "Normal", "", "Flexible Pokémon.", true);
        service.addPokemon(eevee);

        Pokemon evolved = new Pokemon(133, "Eevee", "Kanto", "Fairy", "", "Type changed.", true);
        String result = service.updatePokemon(evolved);
        assertTrue(result.contains("updated successfully"));

        // Confirm update
        assertEquals("Fairy", service.getPokemonByDexNumber(133).getType1());
        assertEquals("Type changed.", service.getPokemonByDexNumber(133).getDescription());
    }

    @Test
    void testRemovePokemon() {
        Pokemon snorlax = new Pokemon(143, "Snorlax", "Kanto", "Normal", "", "A large, sleepy Pokémon.", false);
        service.addPokemon(snorlax);

        String result = service.removePokemon(143);
        assertTrue(result.contains("released successfully"));

        // Pokémon should no longer exist
        assertNull(service.getPokemonByDexNumber(143));
    }

    @Test
    void testFindPokemonByName() {
        Pokemon psyduck = new Pokemon(54, "Psyduck", "Kanto", "Water", "", "Always has a headache.", true);
        service.addPokemon(psyduck);

        // Search by name
        Pokemon found = service.searchPokemon("Psyduck").get(0);
        assertNotNull(found);
        assertEquals(54, found.getDexNumber());
    }

    @Test
    void testGetPokemonByDexNumberReturnsNullIfNotFound() {
        // Helper method returns null for non-existent Dex number
        assertNull(service.getPokemonByDexNumber(999), "Non-existent Dex number should return null");
    }
}
