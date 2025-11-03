package com.example.pkdmsphase3;

import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PokemonRepository {

    private final List<Pokemon> pokemons = new ArrayList<>();

    // --- Save a new Pokémon ---
    public Pokemon save(Pokemon pokemon) {
        pokemons.add(pokemon);
        return pokemon;
    }

    // --- Update existing Pokémon ---
    public Pokemon update(Pokemon pokemon) {
        for (int i = 0; i < pokemons.size(); i++) {
            if (pokemons.get(i).getDexNumber() == pokemon.getDexNumber()) {
                pokemons.set(i, pokemon);
                return pokemon;
            }
        }
        return null;
    }

    // --- Delete Pokémon by Dex Number ---
    public boolean deleteById(int dex) {
        return pokemons.removeIf(p -> p.getDexNumber() == dex);
    }

    // --- Find Pokémon by Dex Number ---
    public Pokemon findById(int dex) {
        for (Pokemon p : pokemons) {
            if (p.getDexNumber() == dex) return p;
        }
        return null;
    }

    // --- Find Pokémon by Name (or all if empty) ---
    public List<Pokemon> findByName(String name) {
        if (name == null || name.isEmpty()) {
            return new ArrayList<>(pokemons);
        }

        List<Pokemon> result = new ArrayList<>();
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                result.add(p);
            }
        }
        return result;
    }

    // --- Return all Pokémon ---
    public List<Pokemon> findAll() {
        return new ArrayList<>(pokemons);
    }

    // --- Load Pokémon from file ---
    public void loadAll(List<Pokemon> listFromFile) {
        pokemons.clear();
        pokemons.addAll(listFromFile);
    }

    // --- Get count (for stats or random feature) ---
    public int count() {
        return pokemons.size();
    }
}
