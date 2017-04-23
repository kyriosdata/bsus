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
import java.util.List;
import java.util.Map;

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

        int total = 0;
        for (String sentenca : cs.descricao) {
            if (sentenca.contains("/")) {
                System.out.println(sentenca);
                total++;
            }
        }

//        for (String chave : mapa.keySet()) {
//            System.out.println(chave);
//        }

        System.out.println(total);
    }
}
