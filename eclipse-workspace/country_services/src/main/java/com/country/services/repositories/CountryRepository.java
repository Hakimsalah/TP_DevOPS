package com.country.services.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.country.services.beans.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByName(String name);
}
