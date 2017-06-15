/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 */
public class Gerador {

    public static void main(String[] args) throws IOException {
        Transformador t = Transformador.newInstance("cid10.json");
        List<String> sentencas = t.getSentencas();

        Files.write(Paths.get("./src/main/resources/cid10.ser"), sentencas);
    }
}
