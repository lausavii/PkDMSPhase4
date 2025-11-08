package com.example.pkdmsphase4;

import jakarta.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {

    @Id
    @Column(name = "dex_number")
    private int dexNumber;  // Primary key

    private String name;
    private String region;
    private String type1;
    private String type2;
    private String description;

    @Column(name = "can_evolve")
    private boolean canEvolve;

    public Pokemon() {}

    public Pokemon(int dexNumber, String name, String region, String type1, String type2, String description, boolean canEvolve) {
        this.dexNumber = dexNumber;
        this.name = name;
        this.region = region;
        this.type1 = type1;
        this.type2 = type2;
        this.description = description;
        this.canEvolve = canEvolve;
    }

    // Getters and setters
    public int getDexNumber() { return dexNumber; }
    public void setDexNumber(int dexNumber) { this.dexNumber = dexNumber; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getType1() { return type1; }
    public void setType1(String type1) { this.type1 = type1; }

    public String getType2() { return type2; }
    public void setType2(String type2) { this.type2 = type2; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCanEvolve() { return canEvolve; }
    public void setCanEvolve(boolean canEvolve) { this.canEvolve = canEvolve; }
}
