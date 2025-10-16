package com.country.services.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.country.services.beans.Country;
import com.country.services.services.CountryService;

@RestController
@RequestMapping("/api") // <-- préfixe commun pour tous les endpoints
public class CountryController {
	
    @Autowired
    private CountryService countryservice;

    // --- ✅ Endpoint d'accueil pour éviter le 404 ---
    @GetMapping("/")
    public String home() {
        return "Bienvenue dans Country Services API 🚀\n"
             + "Essayez les endpoints suivants :\n"
             + "- GET /api/countries\n"
             + "- GET /api/countries/{id}\n"
             + "- GET /api/countries/byname?name=France\n"
             + "- POST /api/countries\n"
             + "- PUT /api/countries/{id}\n"
             + "- DELETE /api/countries/{id}\n";
    }

    // --- GET all countries ---
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getCountries() {
        try {
            List<Country> countries = countryservice.getAllCountries();
            return new ResponseEntity<>(countries, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- GET country by ID ---
    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable int id) {
        try {
            Country country = countryservice.getCountryById(id);
            return new ResponseEntity<>(country, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- GET country by name ---
    @GetMapping("/countries/byname")
    public ResponseEntity<Country> getCountryByName(@RequestParam String name) {
        try {
            Country country = countryservice.getCountryByName(name);
            return new ResponseEntity<>(country, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // --- POST add new country ---
    @PostMapping("/countries")
    public ResponseEntity<Country> addCountry(@RequestBody Country country) {
        try {
            country = countryservice.addCountry(country);
            return new ResponseEntity<>(country, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // --- PUT update existing country ---
    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable int id, @RequestBody Country country) {
        try {
            Country existCountry = countryservice.getCountryById(id);
            existCountry.setName(country.getName());
            existCountry.setCapital(country.getCapital());

            Country updatedCountry = countryservice.updateCountry(existCountry);
            return new ResponseEntity<>(updatedCountry, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    // --- DELETE country ---
    @DeleteMapping("/countries/{id}")
    public ResponseEntity<Country> deleteCountry(@PathVariable int id) {
        try {
            Country country = countryservice.getCountryById(id);
            countryservice.deleteCountry(country);
            return new ResponseEntity<>(country, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
