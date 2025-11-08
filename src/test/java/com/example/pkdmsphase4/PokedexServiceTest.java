package com.example.pkdmsphase4;

import com.example.pkdmsphase4.repository.PokemonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokedexServiceTest {

    private PokedexService service;
    private PokemonRepository repository;

    @BeforeEach
    void setUp() {
        repository = mock(PokemonRepository.class);
        service = new PokedexService(repository);
    }

    @Test
    void testAddPokemon() {
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "A small turtle Pokémon.", true);
        when(repository.save(squirtle)).thenReturn(squirtle);

        service.addPokemon(squirtle);

        verify(repository, times(1)).save(squirtle);
    }

    @Test
    void testUpdatePokemon() {
        Pokemon eevee = new Pokemon(133, "Eevee", "Kanto", "Normal", "", "Flexible Pokémon.", true);
        when(repository.save(eevee)).thenReturn(eevee);

        service.updatePokemon(eevee);

        verify(repository, times(1)).save(eevee);
    }

    @Test
    void testRemovePokemon() {
        Pokemon charmander = new Pokemon(143, "Charmander", "Kanto", "Fire", "", "Tiny flame lizard.", true);
        when(repository.findByDexNumber(143)).thenReturn(charmander);

        service.deletePokemonByDexNumber(143);

        verify(repository, times(1)).delete(charmander);
    }

    @Test
    void testFindByDexNumber() {
        Pokemon pikachu = new Pokemon(25, "Pikachu", "Kanto", "Electric", "", "Electric mouse", true);
        when(repository.findByDexNumber(25)).thenReturn(pikachu);

        Pokemon result = service.getPokemonByDexNumber(25);
        assertEquals("Pikachu", result.getName());
    }

    @Test
    void testSearchPokemonByName() {
        Pokemon psyduck = new Pokemon(54, "Psyduck", "Kanto", "Water", "", "Always has a headache.", true);
        when(repository.findByNameIgnoreCase("Psyduck")).thenReturn(List.of(psyduck));

        List<Pokemon> found = service.searchPokemon("Psyduck");
        assertEquals(1, found.size());
        assertEquals(54, found.get(0).getDexNumber());
    }
}
