/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Aplicativo que visa produzir versão mais compacta da CID-10 e
 * pronta para agilizar a consulta.
 */
public class Main {
    public static void main(String[] args) {
        String file = new Main().getFile("CID-10-CAPITULOS.JSON");

        Gson gson = new Gson();
        Capitulos capitulos = gson.fromJson(file, Capitulos.class);

        System.out.println(capitulos.CATFIM.length);
    }

    private String getFile(String fileName) {

        StringBuilder result = new StringBuilder("");

        //Get file from resources folder
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        try (Scanner scanner = new Scanner(file)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                result.append(line).append("\n");
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();

    }
}
