package com.github.kyriosdata.bsus.cid10.util;

/**
 * Implementação do Algoritmo KMP usando vetores de bytes.
 * Emprego exclusivamente para experimentação de desempenho.
 */
public class KMP {
    // Texto sobre o qual serão feitas "muitas" buscas
    private byte[] text;

    private final int R = 256;

    private int[][] dfa;
    private byte[] pattern;    // padrão a ser procurado

    public KMP(byte[] texto) {
        this.text = texto;
    }

    public void definePadrao(byte[] pattern) {
        this.pattern = new byte[pattern.length];
        for (int j = 0; j < pattern.length; j++)
            this.pattern[j] = pattern[j];

        int m = pattern.length;
        dfa = new int[R][m];
        dfa[pattern[0]][0] = 1;
        for (int x = 0, j = 1; j < m; j++) {
            for (int c = 0; c < R; c++)
                dfa[c][j] = dfa[c][x];
            dfa[pattern[j]][j] = j + 1;
            x = dfa[pattern[j]][x];
        }
    }

    public int encontre(int indice) {

        int end = text.length;
        int m = pattern.length;
        int n = indice + text[indice] + 1;
        int i, j;

        while (true) {

            for (i = indice + 1, j = 0; i < n && j < m; i++) {
                j = dfa[text[i]][j];
            }

            if (j == m) {
                return indice;
            }

            if (n >= end) {
                return -1;
            }

            indice = n;
            n = indice + text[indice] + 1;
        }
    }
}
