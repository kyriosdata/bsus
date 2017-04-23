/*
 * Copyright (c) 2016
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
import java.util.HashSet;
import java.util.Set;

/**
 * Reúne subcategorias da CID-10.
 */
public class Subcategorias {
    public String[] subcat;
    public String[] descricao;

    private int size;

    public static Subcategorias newInstance(String fileName) throws FileNotFoundException {
        File file = FileFromResourcesFolder.get(fileName);

        FileReader fileReader = new FileReader(file);
        Gson gson = new Gson();
        Subcategorias obj = gson.fromJson(fileReader, Subcategorias.class);
        obj.prepare();

        return obj;
    }

    public void prepare() {
        size = descricao.length;
    }

    public Set<Integer> busca(String procurado) {
        Set<Integer> indices = new HashSet<>();

        for (int i = 0; i < size; i++) {
            if (descricao[i].contains(procurado)) {
                indices.add(i);
            }
        }

        return indices;
    }

    public String toString() {
        return "Total de entradas: " + size;
    }

    public void trocaVirgulaPorEspaco() {
        for (int i = 0; i < size; i++) {
            if (descricao[i].contains(",")) {
                descricao[i] = descricao[i].replace(",", " ");
            }
        }
    }

    public void removeColchetes() {
        for (int i = 0; i < size; i++) {
            String sentenca = descricao[i];
            if (sentenca.contains("[")) {
                descricao[i] = descricao[i].replace("[", " ");
            }

            if (sentenca.contains("]")) {
                descricao[i] = descricao[i].replace("]", " ");
            }

        }
    }

    public void pluralComEsse() {
        for (int i = 0; i < size; i++) {
            String sentenca = descricao[i];
            if (sentenca.contains("(s)")) {
                descricao[i] = descricao[i].replace("(s)", "s");
            }
        }
    }
}