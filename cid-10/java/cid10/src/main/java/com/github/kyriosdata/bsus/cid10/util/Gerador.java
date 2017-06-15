package com.github.kyriosdata.bsus.cid10.util;
/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

import com.github.kyriosdata.bsus.cid10.Sequencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Gera vetor de bytes utilizado para busca.
 */
public class Gerador {

    public File get(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public static void main(String[] args) throws IOException {
        File f = new Gerador().get("cid10.ser");

        List<String> sentencas = Files.readAllLines(f.toPath());

        Sequencia s = new Sequencia(sentencas);

        Files.write(Paths.get("./src/main/resources/cid10.dat"), s.toByteArray());
    }
}
