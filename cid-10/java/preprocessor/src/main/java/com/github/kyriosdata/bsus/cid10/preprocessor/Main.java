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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Aplicativo que visa produzir versão mais compacta da CID-10 e
 * pronta para agilizar a consulta.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "CID-10-CAPITULOS.JSON";

        Main processador = new Main();
        processador.preprocessa(fileName);
    }

    private void preprocessa(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = getFileFromResourcesFolder(fileName);

        Capitulos cs = gson.fromJson(new FileReader(file), Capitulos.class);
        exibeAnalise(cs);
    }

    private File getFileFromResourcesFolder(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public void exibeAnalise(Capitulos caps) {
        System.out.println("Total entradas: " + caps.CATFIM.length);
    }

    public Map<String, List<Integer>> montaDicionario(String[] sentencas) {
        Map<String, List<Integer>> mapa = new HashMap<>();

        for(int i = 0; i < sentencas.length; i++) {
            String[] palavras = sentencas[i].split(" ");
        }

        return mapa;
    }
}
