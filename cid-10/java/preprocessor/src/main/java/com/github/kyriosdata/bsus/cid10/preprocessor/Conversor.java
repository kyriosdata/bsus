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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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

        Map<String, List<Integer>> mapa = cs.montaDicionario();
        System.out.println("Tamanho dicionario: " + mapa.size());

        for (String chave : mapa.keySet()) {
            List<Integer> idx = mapa.get(chave);
//            for (int i : idx) {
//                System.out.print(chave + " => " + cs.descricao[i] + " | ");
//            }
            //System.out.println(chave + " " + idx);
        }

        Map<String, ArrayList<Integer>> indice = new TreeMap<>();
        char[] letras = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char primeira : letras) {
            for (char segunda : letras) {
                indice.put("" + primeira + segunda, new ArrayList<Integer>());
            }
        }

        System.out.println("Tamanho indice: " + indice.size());

        for (String entrada : indice.keySet()) {
            for (String chave : mapa.keySet()) {
                if (chave.contains(entrada)) {

                    List<Integer> idx = mapa.get(chave);
                    for (int i : idx) {
                        System.out.print(chave + " => " + cs.descricao[i] + " | ");
                    }
                    System.out.println(chave + " " + idx);
                }
            }
        }
    }
}