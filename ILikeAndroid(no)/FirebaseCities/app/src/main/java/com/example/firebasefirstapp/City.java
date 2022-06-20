package com.example.firebasefirstapp;

public class City {
    private Boolean capital;
    private String country;
    private String name;
    private Long population;

    public City(Boolean capital, String country, String name, Long population) {
        this.capital = capital;
        this.country = country;
        this.name = name;
        this.population = population;
    }

    public Boolean getCapital() {
        return capital;
    }

    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public Long getPopulation() {
        return population;
    }
}
