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
 * Processa entrada visando otimizar a consulta (busca).
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

        // Estratégia de busca para duas ou mais letras
        // (a) Busca no índice LETRAS pelas duas primeiras letras
        //     a.1 Chave = letra1 * letra2
        // (b) Procurar pelo texto

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

        for (String chave : indice.keySet()) {
            System.out.println(chave + " " + indice.get(chave));
        }

        System.out.println(hash('z', 'z'));
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
        int vPrimeira = primeira - 97;
        int vSegunda = segunda - 97;

        return vPrimeira * 26 + vSegunda;
    }
}