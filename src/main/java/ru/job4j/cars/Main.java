package ru.job4j.cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.job4j.cars.util.Timezone;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class Main {
    @PostConstruct
    void initTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(Timezone.DEFAULT_TIMEZONE));
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}