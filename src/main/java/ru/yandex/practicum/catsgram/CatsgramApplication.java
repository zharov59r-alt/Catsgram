package ru.yandex.practicum.catsgram;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Scanner;

public class CatsgramApplication {


    private static final Logger log = LoggerFactory.getLogger(CatsgramApplication.class);

    public static void main(String[] args) {
        final Gson gson = new Gson();
        final Scanner scanner = new Scanner(System.in);

        log.info("Hello");

        System.out.print("Введите JSON => ");
        final String input = scanner.nextLine();
        try {
            gson.fromJson(input, Map.class);
            System.out.println("Был введён корректный JSON");
        } catch (JsonSyntaxException exception) {
            System.out.println("Был введён некорректный JSON");
        }
    }
}