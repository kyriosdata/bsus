/*
 * Copyright (c) 2017
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
 * Processa entrada visando otimizar a consulta (busca).
 */
public class Conversor {

    public static void main(String[] args) throws FileNotFoundException {

        Map<String, Set<String>> indice = montaIndice();
        System.out.println(hash('z', 'z'));

        int size = 0;
        String nomeChaveMaior = "";
        for(String chave : indice.keySet()) {
            final Set<String> conjunto = indice.get(chave);
            if (size < conjunto.size()) {
                size = conjunto.size();
                nomeChaveMaior = chave;
            }
        }

        System.out.println("Chave: " + nomeChaveMaior + " Size: " + size);
    }

    public static Map<String, Set<String>> montaIndice() throws FileNotFoundException {
        String fileName = "cid-10-subcategorias-lower.json";

        Subcategorias cs = Subcategorias.newInstance(fileName);
        System.out.println(cs.toString());

        // Dicionário onde a chave indice os índices das
        // sentenças que contém a chave (palavra).
        Map<String, Set<Integer>> dicionario = cs.montaDicionario();
        System.out.println("Tamanho dicionario: " + dicionario.size());

        // Montagem de índice para reduzir espaço de busca.
        // Para quaisquer duas letras 'x' e 'y', nessa ordem,
        // seguem as palavras do dicionário que contém estas letras
        // nessa ordem.
        Map<String, Set<String>> indice = new TreeMap<>();
        char[] letras = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (char primeira : letras) {
            for (char segunda : letras) {
                indice.put("" + primeira + segunda, new HashSet<String>());
            }
        }

        System.out.println("Tamanho indice: " + indice.size());

        for (String entrada : indice.keySet()) {
            for (String palavra : dicionario.keySet()) {
                if (palavra.contains(entrada)) {
                    indice.get(entrada).add(palavra);
                }
            }
        }
        return indice;
    }

    /**
     * Valor único definido para quaisquer duas letras (a..z).
     *
     * @param primeira Primeira letra.
     * @param segunda A segunda letra.
     *
     * @return Valor único para quaisquer duas letras de 'a' até 'z', de
     * zero até 657, inclusive.
     */
    public static int hash(char primeira, char segunda) {
        // V0
        // int vPrimeira = primeira - 97;
        // int vSegunda = segunda - 97;
        // return vPrimeira * 26 + vSegunda;

        // V1
        // return 26 * (primeira - 97) + segunda - 97

        // V2
        // return 26 * primeira - 97 * 26 + segunda - 97

        // V3
        // return 26 * primeira + segunda - 97 * (26 + 1)

        // V4
        // return 26 * primeira + segunda - 97 * 27

        // V5
        return 26 * primeira + segunda - 2619;
    }
}