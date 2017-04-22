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
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Aplicativo que visa produzir versão mais compacta da CID-10 e
 * pronta para agilizar a consulta.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "CID-10-CAPITULOS.JSON";

        Gson gson = new Gson();

        //Get file from resources folder
        ClassLoader classLoader = new Main().getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        Capitulos cs = gson.fromJson(new FileReader(file), Capitulos.class);
        System.out.println(cs.CATFIM.length);

    }
}
