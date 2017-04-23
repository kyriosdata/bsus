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

        for (String sentenca : cs.descricao) {
            if (sentenca.contains("(es)")) {
//                Pattern p = Pattern.compile("\\(.*\\)");
//                Matcher m = p.matcher(sentenca);
//                if (m.find()) {
//                    System.out.print(m.group() + " | ");
//                } else {
//                    System.out.print("@off@ ");
//                }

                System.out.println(sentenca);
            }
        }
    }
}
