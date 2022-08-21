package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        return date;
    }

    public static String generateCity(String locale) {
        Random random = new Random();
        String[] city = new String[]{"Москва", "Казань", "Самара", "Санкт-Петербург", "Краснодар", "Уфа"};
        return city[random.nextInt(city.length)];
    }

    public static String generateName(String locale) {
        //Faker faker = new Faker();
        Faker faker = new Faker(new Locale("ru"));
        String name = faker.name().lastName() + " " + faker.name().firstName();
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        String phone = faker.phoneNumber().phoneNumber();
        return phone;
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale)
            );
            return user;

        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;

    }
}