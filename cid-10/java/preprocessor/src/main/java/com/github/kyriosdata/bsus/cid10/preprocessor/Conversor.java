/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by kyriosdata on 4/23/17.
 */
public class Conversor {
    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "cid-10-subcategorias-lower.json";

        Subcategorias cs = Subcategorias.newInstance(fileName);
        System.out.println(cs.toString());

        cs.trocaVirgulaPorEspaco();
        cs.removeColchetes();
        cs.pluralSimples();
        cs.trocaTravessaoPorEspaco(); // " - " por " "
        cs.trocaHifenPorEspaco();
        cs.eliminaParenteses();
        cs.removeSinais(); // ç por c, á por a, ...
        cs.removeAspas();

        // Dicionário contendo todas as palavras e onde aparecem
        Map<String, Set<Integer>> mapa = cs.montaDicionario();
        System.out.println("Tamanho dicionario: " + mapa.size());


        // Montagem de índice para reduzir espaço de busca.
        // Por uma letra e por duas letras (experimento)
        Map<String, Integer> indice = new TreeMap<>();
        char[] letras = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char primeira : letras) {
            for (char segunda : letras) {
                indice.put("" + primeira + segunda, 0);
            }
        }

        System.out.println("Tamanho indice: " + indice.size());

        for (String entrada : indice.keySet()) {
            for (String chave : mapa.keySet()) {
                if (chave.contains(entrada)) {
                    int valor = indice.get(entrada);
                    indice.put(entrada, new Integer(valor + 1));
                }
            }
        }

        for (String chave : indice.keySet()) {
            System.out.println(chave + " " + indice.get(chave));
        }
    }
}