package ru.netology.data;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGeneration {
    private DataGeneration() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));

    public static class Registration {
        private Registration() {
        }

        public static String generateDate(int shift) {
            String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return date;
        }

        public static String generateCity(String locale) {
            String city = faker.address().city();
            return city;
        }

        public static String generateName(String locale) {
            String name = faker.name().fullName();
            return name;
        }

        public static String generatePhone(String locale) {
            String phone = faker.phoneNumber().phoneNumber();
            return phone;
        }
    }
}




