package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.util.Arrays;
import java.util.List;

public class KMP {
    // Texto sobre o qual serão feitas "muitas" buscas
    private byte[] text;

    private final int R = 256;       // the radix

    private int[][] dfa;       // the KMP automoton
    private byte[] pattern;    // padrão a ser procurado

    public KMP(byte[] texto) {
        this.text = texto;
    }

    public void definePadrao(byte[] pattern) {
        this.pattern = new byte[pattern.length];
        for (int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];

        // build DFA from pattern
        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];     // Copy mismatch cases.
            dfa[pattern[j]][j] = j + 1;      // Set match case.
            x = dfa[pattern[j]][x];        // Update restart state.
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @param indice
     * @return the index of the first occurrence of the pattern string
     * in the text string; N if no such match
     */
    public int search(int indice) {

        // simulate operation of DFA on text
        int end = text.length;
        int m = pattern.length;
        int n = indice + text[indice] + 1;
        int i, j;

        while (true) {

            for (i = indice + 1, j = 0; i < n && j < m; i++) {
                j = dfa[text[i]][j];
            }

            if (j == m) {
                return indice;    // found
            }

            indice = n;
            if (indice >= end) {
                return -1;
            }

            n = indice + text[indice] + 1;
        }
    }


    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        byte[] padrao = Sequencia.toByteArray("ix".toCharArray());
        List<String> palavras = Arrays.asList("texto", "icnorme");

        KMP kmp = new KMP(Sequencia.montaSequencia(palavras));

        kmp.definePadrao(padrao);

        int indice = kmp.search(0);

        System.out.println(indice);
    }

}
