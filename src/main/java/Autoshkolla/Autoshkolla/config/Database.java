package Autoshkolla.Autoshkolla.config;


import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Database implements CommandLineRunner {

    private final DataSource dataSource;

    public Database(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("----------------------------------------");
        System.out.println("Lidhja me DB  " + dataSource.getConnection());
        System.out.println("----------------------------------------");
    }
}
