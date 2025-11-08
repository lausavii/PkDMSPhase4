package com.example.pkdmsphase4.controller;

import com.example.pkdmsphase4.Pokemon;
import com.example.pkdmsphase4.PokedexService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokedexService service;

    public PokedexController(PokedexService service) {
        this.service = service;
    }

    // --- Main Menu ---
    @GetMapping("/index")
    public String index() {
        return "index"; //
    }

    // --- Display all ---
    @GetMapping("/display")
    public String displayAll(Model model) {
        model.addAttribute("pokemons", service.getAllPokemon());
        return "display";
    }

    // --- Add Pokémon ---
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("pokemon", new Pokemon());
        model.addAttribute("types", List.of("Fire","Water","Grass","Electric","Psychic","Ice","Dragon","Dark","Fairy","Normal","Fighting","Flying","Poison","Ground","Rock","Bug","Ghost","Steel"));
        return "add";
    }

    @PostMapping("/add")
    public String addPokemon(@ModelAttribute Pokemon pokemon, Model model) {
        boolean success = service.addPokemon(pokemon);
        if(success) {
            model.addAttribute("message", pokemon.getName() + " added successfully!");
        } else {
            model.addAttribute("message", "Failed to add " + pokemon.getName() + ". Check console for details.");
        }
        model.addAttribute("types", List.of("Fire","Water","Grass","Electric","Psychic","Ice","Dragon","Dark","Fairy","Normal","Fighting","Flying","Poison","Ground","Rock","Bug","Ghost","Steel"));
        return "add";
    }

    // --- Delete Pokémon ---
    @GetMapping("/delete")
    public String showDelete(Model model) {
        model.addAttribute("pokemons", service.getAllPokemon());
        return "delete"; // delete.html should list all Pokémon
    }

    @PostMapping("/delete/{dexNumber}")
    public String deletePokemon(@PathVariable int dexNumber, Model model) {
        boolean success = service.deletePokemonByDexNumber(dexNumber);
        if (success) {
            model.addAttribute("message", "Pokémon released successfully!");
        } else {
            model.addAttribute("message", "Failed to delete Pokémon. Check console for details.");
        }
        model.addAttribute("pokemons", service.getAllPokemon());
        return "delete";
    }

    // --- Show Update Selection Page ---
    @GetMapping("/update")
    public String showUpdate(Model model) {
        model.addAttribute("pokemons", service.getAllPokemon());
        return "update"; // update.html should list all Pokémon to select
    }

    // --- Select a Pokémon to Update ---
    @GetMapping("/update/select/{dexNumber}")
    public String selectUpdate(@PathVariable int dexNumber, Model model) {
        Pokemon pokemon = service.getPokemonByDexNumber(dexNumber);
        if (pokemon == null) {
            model.addAttribute("message", "Pokémon not found!");
            model.addAttribute("pokemons", service.getAllPokemon());
            return "update";
        }
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("types", List.of(
                "Fire","Water","Grass","Electric","Psychic","Ice","Dragon","Dark","Fairy",
                "Normal","Fighting","Flying","Poison","Ground","Rock","Bug","Ghost","Steel"));
        return "add"; // reuse add.html for editing
    }

    // --- Process the Update Form ---
    @PostMapping("/update")
    public String updatePokemon(@ModelAttribute Pokemon pokemon, Model model) {
        boolean success = service.updatePokemon(pokemon);
        if (success) {
            model.addAttribute("message", pokemon.getName() + " updated successfully!");
        } else {
            model.addAttribute("message", "Failed to update " + pokemon.getName() + ". Check console for details.");
        }
        model.addAttribute("types", List.of(
                "Fire","Water","Grass","Electric","Psychic","Ice","Dragon","Dark","Fairy",
                "Normal","Fighting","Flying","Poison","Ground","Rock","Bug","Ghost","Steel"));
        return "add"; // show the add/edit page again
    }


    // --- Custom "Who's That Pokémon?" ---
    @GetMapping("/custom")
    public String showRandomPokemon(Model model) {
        List<Pokemon> all = service.getAllPokemon();
        if (all.isEmpty()) {
            model.addAttribute("pokemon", null);
            model.addAttribute("message", "No Pokémon found!");
        } else {
            Random r = new Random();
            Pokemon random = all.get(r.nextInt(all.size()));
            long countSameType = all.stream().filter(p -> p.getType1().equalsIgnoreCase(random.getType1())
                            || (p.getType2() != null && p.getType2().equalsIgnoreCase(random.getType1())))
                    .count();
            model.addAttribute("pokemon", random);
            model.addAttribute("message", "You have " + countSameType + " Pokémon of this type! Gotta Catch 'Em All!");
        }
        return "custom";
    }
}
