package com.example.pkdmsphase4.repository;

import com.example.pkdmsphase4.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    // Find a Pokémon by dex number
    Pokemon findByDexNumber(int dexNumber);

    // Search Pokémon by name (case-insensitive)
    List<Pokemon> findByNameIgnoreCase(String name);
}
