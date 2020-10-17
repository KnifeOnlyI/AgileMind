package com.knife.agilemind.web.rest;

import com.knife.agilemind.AgileMindApp;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = AgileMindApp.class)
public abstract class AbstractResourceIT {
    private static final List<String> tableRoReset = Arrays.asList("project");

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        AbstractResourceIT.tableRoReset.forEach(this::resetSequence);
    }

    /**
     * Reset the sequence of the specified table
     * (Sequence name MUST be formated like : <table_name>_seq
     *
     * @param table The table name
     */
    protected void resetSequence(String table) {
        String query = String.format("TRUNCATE TABLE %s RESTART IDENTITY;", table);
        String query2 = String.format("ALTER SEQUENCE %s_seq RESTART WITH 1;", table);

        this.jdbcTemplate.execute(query);
        this.jdbcTemplate.execute(query2);
    }
}
