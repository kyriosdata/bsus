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

        System.out.println("Padroes encontrados: " + total);
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

    public Map<String, Set<Integer>> montaDicionario() {
        Map<String, Set<Integer>> mapa = new TreeMap<>();

        for (int i = 0; i < size; i++) {

            String[] palavras = descricao[i].split("\\s+");
            for (String palavra : palavras) {
                palavra = trataPalavra(palavra);
                if (palavra == null) {
                    continue;
                }

                Set<Integer> indices = mapa.get(palavra);
                if (indices == null) {
                    indices = new HashSet<>();
                    mapa.put(palavra, indices);
                }

                if (indices.contains(i)) {
                    continue;
                }

                indices.add(i);
            }
        }

        return mapa;
    }

    private List<String> paraRemover = Arrays.asList(new String[]{
            "de", "da", "das", "do", "dos",
            "a", "as", "e", "o", "os",
            "na", "nas", "no", "nos",
            "para",
            "que", "com", "ou",
            "em"
    });

    /**
     * Operações realizadas:
     * (a) toLower (realizado via linha de comandos)
     * cat arquivo.json | tr "[A-Z]" "[a-z]"
     * (b) remover 'de', 'da', 'a', 'e', 'as', 'dos', '[', ']', '-', ',' e outros.
     * (c) eliminar acentos
     */
    public String trataPalavra(String palavra) {

        if (paraRemover.contains(palavra)) {
            return null;
        }

        if (palavra.isEmpty()) {
            return null;
        }

        return palavra;

    }
}
