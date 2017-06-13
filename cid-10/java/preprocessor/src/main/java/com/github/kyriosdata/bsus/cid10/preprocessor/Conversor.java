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
 * Monta índice para busca eficiente em sequências de caracteres (sentenças
 * formadas por várias palavras). O índice faz uso de dois níveis. No
 * primeiro nível tem-se o mapeamento de todas as palavras do conjunto
 * de sentenças para as sentenças nas quais elas estão presentes. Ou seja,
 * dada uma palavra qualquer, sabemos todas as sentenças nas quais está
 * presente, se for o caso. No segundo nível tem-se o mapeamento de quaisquer
 * combinações de duas letras (576 possibilidades) para as palavras
 * identificadas no primeiro nível. Ou seja, dada uma sequência qualquer de
 * duas letras, tem-se o conjunto de palavras nas quais tal sequência está
 * presente.
 *
 * <p>Dada a estrutura identificada acima, a busca por sentenças nas quais
 * estão presentes as palavras ou partes de palavras formadas por
 * "gu" e "den", por exemplo, primeiro é identificado o conjunto de palavras
 * que contém tais sequências, digamos C1 e C2, respectivamente. Identificamos
 * o conjunto com menor quantidade de palavras, digamos C1, sem perda de
 * generalidade. Nesse caso, nesse conjunto "reduzido", iremos procurar
 * pela ocorrência da outra sequência. Aquelas palavras onde a outra
 * sequência ocorre é acumulada ao conjunto de palavras da resposta parcial.
 *
 * <p>A resposta parcial é utilizada para obter a resposta final, formada
 * pelas sentenças onde ocorre as palavras contidas na resposta parcial.
 */
public class Conversor {

    public static List<String> paraRemover = Arrays.asList(new String[]{
            "de", "da", "das", "do", "dos",
            "a", "as", "e", "o", "os",
            "na", "nas", "no", "nos",
            "para",
            "que", "com", "ou",
            "em"
    });

    /**
     * Monta índice formado pela combinação de duas letras (a..z) para
     * todas as palavras de um dado conjunto.
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static Map<String, Set<String>> montaIndice(String fileName) throws FileNotFoundException {

        Subcategorias cs = Subcategorias.newInstance(fileName);

        // Índice das descrições por palavra.
        Map<String, Set<Integer>> dicionario = montaDicionario(cs.descricao);

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
     * Valor único definido para quaisquer duas letras (a..z). Os valores
     * ASCII correspondentes são 97 até 122, inclusive. Observe que o valor
     * gerado só se aplica, portanto, a letras minúsculas (sem acentuação).
     *
     * @param primeira Primeira letra.
     * @param segunda A segunda letra.
     *
     * @return Valor único para quaisquer duas letras de 'a' até 'z', de
     * zero até 675 (26*26=676), inclusive.
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

    /**
     * Operações realizadas:
     * (a) toLower (realizado via linha de comandos)
     * cat arquivo.json | tr "[A-Z]" "[a-z]"
     * (b) remover 'de', 'da', 'a', 'e', 'as', 'dos', '[', ']', '-', ',' e outros.
     * (c) eliminar acentos
     */
    public static String trataPalavra(String palavra) {

        if (paraRemover.contains(palavra)) {
            return null;
        }

        if (palavra.isEmpty()) {
            return null;
        }

        return palavra;

    }

    /**
     * Monta dicionário (índice) onde a chave é uma palavra de uma descrição
     * e o valor é o conjunto de índices das subcategorias na qual a palavra
     * está presente. Noutras palavras, o índice permite identificar todas
     * as palavras das descrições de subcategorias da CID-10 e, para cada
     * uma delas, quais as subcategorias que as contém.
     * @return
     * @param descricao
     */
    public static Map<String, Set<Integer>> montaDicionario(String[] descricao) {
        Map<String, Set<Integer>> mapa = new TreeMap<>();

        for (int i = 0; i < descricao.length; i++) {

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
}