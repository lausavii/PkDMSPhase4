package com.example.pkdmsphase4;

import com.example.pkdmsphase4.repository.PokemonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository repository;

    @Test
    void testSaveAndFindById() {
        Pokemon charmander = new Pokemon(4, "Charmander", "Kanto", "Fire", "", "A small fire lizard Pokémon.", true);
        repository.save(charmander);

        Optional<Pokemon> found = repository.findById(4);
        assertTrue(found.isPresent());
        assertEquals("Charmander", found.get().getName());
    }

    @Test
    void testFindByNameIgnoreCase() {
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "A small turtle Pokémon.", true);
        repository.save(squirtle);

        List<Pokemon> found = repository.findByNameIgnoreCase("squirtle");
        assertEquals(1, found.size());
        assertEquals(7, found.get(0).getDexNumber());
    }

    @Test
    void testDeleteById() {
        Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", "Kanto", "Grass", "Poison", "A small dinosaur-like Pokémon.", true);
        repository.save(bulbasaur);

        repository.deleteById(1);
        Optional<Pokemon> found = repository.findById(1);
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindAll() {
        Pokemon p1 = new Pokemon(25, "Pikachu", "Kanto", "Electric", "", "Electric mouse", true);
        Pokemon p2 = new Pokemon(133, "Eevee", "Kanto", "Normal", "", "Flexible Pokémon", true);
        repository.save(p1);
        repository.save(p2);

        List<Pokemon> all = repository.findAll();
        assertEquals(2, all.size());
    }
}
