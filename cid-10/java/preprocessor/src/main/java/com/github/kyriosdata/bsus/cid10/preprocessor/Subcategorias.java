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
import java.util.*;

/**
 * Reúne subcategorias da CID-10 em um objeto obtido a partir de arquivo
 * JSON. A descrição de cada entrada é "transformada" com o propósito de
 * facilitar a consulta. Por exemplo, "costela(s)" faz parte de uma
 * descrição que só poderá ser encontrada pela encontre "costelas" se os
 * parênteses forem removidos. Vários operações são executadas, além
 * dessa "aplicação de plural".
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
        obj.size = obj.descricao.length;

        // Realiza "transformações" na entrada. O objetivo é eliminar
        // elementos que não serão empregados na consulta.
        obj.trocaVirgulaPorEspaco();
        obj.removeColchetes();
        obj.pluralSimples();
        obj.trocaTravessaoPorEspaco(); // " - " por " "
        obj.trocaHifenPorEspaco();
        obj.eliminaParenteses();
        obj.removeSinais(); // ç por c, á por a, ...
        obj.removeAspas();

        return obj;
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

    public void pluralSimples() {
        for (int i = 0; i < size; i++) {
            String sentenca = descricao[i];
            if (sentenca.contains("(s)")) {
                descricao[i] = descricao[i].replace("(s)", "s");
            }

            if (sentenca.contains("(es)")) {
                descricao[i] = descricao[i].replace("(es)", "es");
            }
        }
    }

    public void eliminaParenteses() {
        int total = 0;
        for (int i = 0; i < size; i++) {
            String sentenca = descricao[i];

            if (sentenca.contains("(")) {
                descricao[i] = descricao[i].replace("(", " ");
            }

            if (sentenca.contains(")")) {
                descricao[i] = descricao[i].replace(")", " ");
            }
        }
    }

    public void trocaTravessaoPorEspaco() {
        for (int i = 0; i < size; i++) {
            if (descricao[i].contains(" - ")) {
                descricao[i] = descricao[i].replace(" - ", " ");
            }
        }
    }

    public void trocaHifenPorEspaco() {
        for (int i = 0; i < size; i++) {
            if (descricao[i].contains("-")) {
                descricao[i] = descricao[i].replace("-", " ");
            }
        }
    }

    /**
     * Assume palavras apenas com letras minúsculas.
     */
    public void removeSinais() {
        String acentos = "äáâàãéêëèíîïìöóôòõüúûùç";
        String semAcentos = "aaaaaeeeeiiiiooooouuuuc";
        final int SIZE = acentos.length();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < SIZE; j++) {
                char acento = acentos.charAt(j);
                char semAcento = semAcentos.charAt(j);
                descricao[i] = descricao[i].replace(acento, semAcento);
            }
        }
    }

    public void removeAspas() {
        for (int i = 0; i < size; i++) {
            if (descricao[i].contains("\"")) {
                descricao[i] = descricao[i].replace("\"", " ");
            }
        }
    }
}
