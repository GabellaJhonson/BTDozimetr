package com.example.btdozimetr;

import java.util.Random;

public class TestDataGenerator {
    static double nextValue(double value){


        // Генерируем случайное число: true для прибавления, false для вычитания
        Random random = new Random();
        int randomFactor = random.nextInt(1000) + 1;
        boolean add = random.nextBoolean();
        double percentage = randomFactor * 0.1;

        // Возвращаем либо value + , либо value -
        if (add) {
            return value + percentage;
        } else {
            return Math.abs(value - percentage / 50);
        }
    }
}
