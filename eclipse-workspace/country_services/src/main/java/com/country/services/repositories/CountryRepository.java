package com.country.services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.country.services.beans.Country;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    Optional<Country> findByNameIgnoreCase(String name);
}
