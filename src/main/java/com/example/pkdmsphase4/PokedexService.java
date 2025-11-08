package com.example.pkdmsphase4;

import com.example.pkdmsphase4.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokedexService {

    private final PokemonRepository repository;

    public PokedexService(PokemonRepository repository) {
        this.repository = repository;
    }

    // Retrieve all Pokémon
    public List<Pokemon> getAllPokemon() {
        return repository.findAll();
    }

    // Add a new Pokémon
    public boolean addPokemon(Pokemon pokemon) {
        try {
            System.out.println("Adding Pokémon: " + pokemon.getDexNumber() + " - " + pokemon.getName());
            repository.save(pokemon);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding Pokémon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Update an existing Pokémon
    public boolean updatePokemon(Pokemon pokemon) {
        try {
            System.out.println("Updating Pokémon: " + pokemon.getDexNumber() + " - " + pokemon.getName());
            repository.save(pokemon);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating Pokémon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Delete Pokémon by dex number
    public boolean deletePokemonByDexNumber(int dexNumber) {
        try {
            Pokemon existing = repository.findByDexNumber(dexNumber);
            if (existing != null) {
                repository.delete(existing);
                System.out.println("Deleted Pokémon: " + dexNumber);
                return true;
            } else {
                System.out.println("Pokémon not found: " + dexNumber);
                return false;
            }
        } catch (Exception e) {
            System.err.println("Error deleting Pokémon: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Get Pokémon by dex number
    public Pokemon getPokemonByDexNumber(int dexNumber) {
        return repository.findByDexNumber(dexNumber);
    }

    // Search Pokémon by name
    public List<Pokemon> searchPokemon(String name) {
        return repository.findByNameIgnoreCase(name);
    }
}
