package com.country.services.demo;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.country.services.beans.Country;
import com.country.services.repositories.CountryRepository;
import com.country.services.services.CountryService;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceMockitoTests {

    @Mock
    private CountryRepository countryrep;

    @InjectMocks
    private CountryService countryService;

    @Test
    @Order(1)
    public void test_getAllCountries() {
        List<Country> mycountries = new ArrayList<>();
        mycountries.add(new Country(1, "India", "Delhi"));
        mycountries.add(new Country(2, "USA", "Washington"));

        when(countryrep.findAll()).thenReturn(mycountries);

        List<Country> result = countryService.getAllCountries();
        assertEquals(2, result.size());
    }

    @Test
    @Order(2)
    public void test_getCountryByID() {
        Country country1 = new Country(1, "India", "Delhi");
        Country country2 = new Country(2, "USA", "Washington");
        List<Country> mycountries = List.of(country1, country2);

        when(countryrep.findAll()).thenReturn(mycountries);

        Country result = countryService.getCountryById(1);
        assertNotNull(result);
        assertEquals(1, result.getIdCountry());
    }

    @Test
    @Order(3)
    public void test_getCountryByName() {
        Country country1 = new Country(1, "India", "Delhi");
        Country country2 = new Country(2, "USA", "Washington");
        List<Country> mycountries = List.of(country1, country2);

        when(countryrep.findAll()).thenReturn(mycountries);

        Country result = countryService.getCountryByName("USA");
        assertNotNull(result);
        assertEquals("USA", result.getName());
    }

    @Test
    @Order(4)
    public void test_addCountry() {
        Country country = new Country(3, "France", "Paris");
        when(countryrep.save(country)).thenReturn(country);

        Country result = countryService.addCountry(country);
        assertNotNull(result);
        assertEquals(country, result);
    }

    @Test
    @Order(5)
    public void test_updateCountry() {
        Country country = new Country(3, "Germany", "Berlin");
        when(countryrep.save(country)).thenReturn(country);

        Country result = countryService.updateCountry(country);
        assertNotNull(result);
        assertEquals(country, result);
    }

    @Test
    @Order(6)
    public void test_deleteCountry() {
        Country country = new Country(3, "Germany", "Berlin");

        countryService.deleteCountry(country);
        verify(countryrep, times(1)).delete(country);
    }
}
