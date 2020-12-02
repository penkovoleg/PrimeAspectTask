package com.company;

import java.io.File;

/**
 * Папка InputFiles хранит входные файлы, в строке 14 путь
 * В папке OutputFiles создаются файлы с названиями заголовков
 * В консоль выводятся имена потоков и значения
 */

public class Main {

    public static void main(String[] args) {
        File[] files = new File("./InputFiles/").listFiles();
        for (File file : files) {
            new HandlerFiles(file).start();
        }
    }
}
