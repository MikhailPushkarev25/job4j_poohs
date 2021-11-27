package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrderStoreTest {
    private BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void delete() {
        try (Connection con = pool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DROP TABLE orders")) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrderStore store = new OrderStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenFindByIdOrder() {
        OrderStore store = new OrderStore(pool);
        store.save(Order.of("name2", "description2"));
        Order id = store.findById(1);

        assertThat(id.getId(), is(1));
        assertThat(id.getName(), is("name2"));
        assertThat(id.getDescription(), is("description2"));
    }

    @Test
    public void whenUpdateOrder() {
        OrderStore store = new OrderStore(pool);
        Order order = store.save(Order.of("name3", "description3"));
        order.setDescription("description1000");
        store.updateOrder(order);

        assertThat(store.findById(order.getId()).getDescription(), is("description1000"));
    }

    @Test
    public void whenFindByNameOrder() {
        OrderStore store = new OrderStore(pool);
        store.save(Order.of("name5", "description5"));
        Order order = store.findByName("name5");

        assertThat(order.getId(), is(1));
        assertThat(order.getName(), is("name5"));
        assertThat(order.getDescription(), is("description5"));
    }
}