package com.example.pkdmsphase3;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class PokedexService {

    private final PokemonRepository repository = new PokemonRepository();

    // ---------------- Add Pokémon ----------------
    public String addPokemon(Pokemon p) {
        if (repository.findById(p.getDexNumber()) != null) {
            return "Error: Pokémon with that Dex number already exists.";
        }
        repository.save(p);
        return "Pokémon added successfully!";
    }

    // ---------------- Update Pokémon ----------------
    public String updatePokemon(Pokemon p) {
        if (repository.findById(p.getDexNumber()) == null) {
            return "Error: Pokémon not found.";
        }
        repository.update(p);
        return "Pokémon updated successfully!";
    }

    // ---------------- Remove Pokémon ----------------
    public String removePokemon(int dexNumber) {
        if (repository.findById(dexNumber) == null) {
            return "Error: Pokémon not found.";
        }
        repository.deleteById(dexNumber);
        return "Pokémon released successfully!";
    }

    // ---------------- Search Pokémon ----------------
    public List<Pokemon> searchPokemon(String name) {
        return repository.findByName(name);
    }

    // ---------------- Upload from File ----------------
    public int uploadFromFile(MultipartFile file) {
        if (file.isEmpty()) return -1;
        int count = 0;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) continue; // require all 7 fields

                int dexNumber = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                String region = parts[2].trim();
                String type1 = parts[3].trim();
                String type2 = parts[4].trim();
                String description = parts[5].trim();
                boolean canEvolve = Boolean.parseBoolean(parts[6].trim());

                Pokemon p = new Pokemon(dexNumber, name, region, type1, type2, description, canEvolve);
                addPokemon(p); // save to repository
                count++;
            }
        } catch (Exception e) {
            return -1;
        }
        return count;
    }

    // ---------------- Custom Pokémon ----------------
    public CustomPokemonDTO customPokemon() {
        List<Pokemon> all = repository.findByName("");

        if (all.isEmpty()) {
            // Return DTO with null Pokémon but a message, so template can render safely
            return new CustomPokemonDTO(null, "No Pokémon in the Pokédex.");
        }

        // Pick a random Pokémon
        int index = (int) (Math.random() * all.size());
        Pokemon p = all.get(index);

        // Count how many Pokémon share the same type
        long typeCount = repository.findByName("").stream()
                .filter(pok -> pok.getType1().equalsIgnoreCase(p.getType1()) ||
                        (pok.getType2() != null && pok.getType2().equalsIgnoreCase(p.getType1())))
                .count();

        String message = "It's " + p.getName() + "! " +
                "You have caught " + typeCount + " " + p.getType1() + "-type Pokémon. Gotta Catch 'Em All!";

        return new CustomPokemonDTO(p, message);
    }

    // ---------------- Helper Method for Tests ----------------
    public Pokemon getPokemonByDexNumber(int dexNumber) {
        return repository.findById(dexNumber);
    }

    // ---------------- Find Pokémon by Dex Number ----------------
    public Pokemon findByDex(int dexNumber) {
        return repository.findById(dexNumber);
    }

    // ---------------- DTO for Custom Pokémon ----------------
    public static class CustomPokemonDTO {
        private final Pokemon pokemon;
        private final String message;

        public CustomPokemonDTO(Pokemon pokemon, String message) {
            this.pokemon = pokemon;
            this.message = message;
        }

        public Pokemon getPokemon() {
            return pokemon;
        }

        public String getMessage() {
            return message;
        }
    }
}
