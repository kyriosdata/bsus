/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Objeto auxiliar que representa CAPÍTULOS da CID-10.
 */
public class Capitulos {
    public int[] numcap;
    public String[] catinic;
    public String[] catfim;
    public String[] descricao;

    private int size;

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
}
