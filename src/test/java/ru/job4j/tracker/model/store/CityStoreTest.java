package ru.job4j.tracker.model.store;

import org.junit.Test;
import ru.job4j.tracker.model.City;

import java.util.List;

import static org.junit.Assert.*;

public class CityStoreTest {

    @Test
    public void whenCreate() {
        CityStore cityStore = new CityStore();
        City city = new City("Moskow");
        cityStore.create(city);
        List<City> all = cityStore.findAll();
        assertEquals(city, all.get(0));
    }

    @Test
    public void whenFindAll() {
        CityStore cityStore = new CityStore();
        City city = new City("Moskow");
        City city1 = new City("St. Petersburg");
        cityStore.create(city);
        cityStore.create(city1);
        assertEquals(List.of(city, city1), cityStore.findAll());
    }

}