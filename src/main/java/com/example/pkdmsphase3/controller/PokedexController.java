package com.example.pkdmsphase3.controller;

import com.example.pkdmsphase3.Pokemon;
import com.example.pkdmsphase3.PokedexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/pokedex")
public class PokedexController {

    private final PokedexService service;

    @Autowired
    public PokedexController(PokedexService service) {
        this.service = service;
    }

    @GetMapping("/index")
    public String index() {
        return "index"; // Thymeleaf page name
    }

    // ---------------- Display All Pokémon ----------------
    @GetMapping("/display")
    public String displayPokemons(Model model) {
        // Fetch all Pokémon (no search term needed)
        model.addAttribute("pokemons", service.searchPokemon(""));
        return "display"; // Thymeleaf page name
    }


    // ---------------- Add Pokémon ----------------
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("pokemon", new Pokemon());
        return "add"; // Thymeleaf page
    }

    @PostMapping("/add")
    public String addSubmit(@ModelAttribute Pokemon pokemon, Model model) {
        String result = service.addPokemon(pokemon);
        model.addAttribute("message", result);
        model.addAttribute("pokemon", new Pokemon()); // reset form
        return "add";
    }

    // ---------------- Delete Pokémon ----------------
    // ---------------- Delete Pokémon ----------------
    @GetMapping("/delete")
    public String deleteForm(Model model) {
        model.addAttribute("message", "Enter the Dex Number of the Pokémon you want to release.");
        return "delete"; // initial delete page
    }

    // Step 1: Search Pokémon by Dex Number
    @GetMapping("/delete/search")
    public String searchPokemonForDelete(@RequestParam int dexNumber, Model model) {
        Pokemon pokemon = service.findByDex(dexNumber);
        if (pokemon == null) {
            model.addAttribute("message", "Error: Pokémon with Dex #" + dexNumber + " not found.");
        }
        model.addAttribute("pokemon", pokemon); // pass to template
        return "delete"; // show confirmation if found
    }

    // Step 2: Process deletion based on confirmation
    @PostMapping("/delete")
    public String deleteSubmit(@RequestParam int dexNumber,
                               @RequestParam boolean confirm,
                               Model model) {
        String result;
        if (confirm) {
            result = service.removePokemon(dexNumber);
        } else {
            result = "Operation canceled.";
        }
        model.addAttribute("message", result);
        return "delete";
    }


    // ---------------- Update Pokémon ----------------
    // Step 1: show form to enter Dex Number
    @GetMapping("/update")
    public String updateForm(Model model) {
        model.addAttribute("message", "Enter the Dex Number of the Pokémon you want to update.");
        return "update";
    }

    // Step 2: search Pokémon by Dex Number
    @GetMapping("/update/search")
    public String searchPokemonForUpdate(@RequestParam int dexNumber, Model model) {
        Pokemon pokemon = service.findByDex(dexNumber);
        if (pokemon == null) {
            model.addAttribute("message", "Error: Pokémon with Dex #" + dexNumber + " not found.");
        } else {
            model.addAttribute("pokemon", pokemon); // only if found
            model.addAttribute("message", "Editing Pokémon: " + pokemon.getName());
        }
        return "update"; // show the form
    }

    // Step 3: submit updated Pokémon
    @PostMapping("/update")
    public String updateSubmit(@ModelAttribute Pokemon pokemon, Model model) {
        String result = service.updatePokemon(pokemon);
        model.addAttribute("message", result);
        model.addAttribute("pokemon", pokemon); // keep it in form after update
        return "update";
    }


    // ---------------- Upload File ----------------
    @GetMapping("/upload")
    public String uploadForm() {
        return "upload"; // Thymeleaf page
    }

    @PostMapping("/upload")
    public String uploadSubmit(@RequestParam("file") MultipartFile file, Model model) {
        try {
            int count = service.uploadFromFile(file);
            if (count == -1) {
                model.addAttribute("message", "Error: File could not be read.");
            } else {
                model.addAttribute("message", count + " Pokémon uploaded successfully!");
            }
        } catch (Exception e) {
            model.addAttribute("message", "Error uploading file.");
        }
        return "upload";
    }

    // ---------------- Who's That Pokémon ----------------
    @GetMapping("/custom")
    public String showCustomPage(Model model) {
        PokedexService.CustomPokemonDTO customDTO = service.customPokemon();

        if (customDTO == null) {
            model.addAttribute("message", "No Pokémon in the Pokédex.");
            return "custom";
        }

        model.addAttribute("pokemon", customDTO.getPokemon());
        model.addAttribute("message", customDTO.getMessage());

        return "custom";
    }
}
