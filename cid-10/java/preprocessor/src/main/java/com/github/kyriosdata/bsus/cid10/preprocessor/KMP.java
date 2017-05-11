package com.github.kyriosdata.bsus.cid10.preprocessor;

public class KMP {
    private byte[] text;

    private final int R = 256;       // the radix
    private int[][] dfa;       // the KMP automoton

    private byte[] pattern;    // either the character array for the pattern

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
            dfa[pattern[j]][j] = j+1;      // Set match case.
            x = dfa[pattern[j]][x];        // Update restart state.
        }
    }

    /**
     * Returns the index of the first occurrrence of the pattern string
     * in the text string.
     *
     * @return the index of the first occurrence of the pattern string
     *         in the text string; N if no such match
     * @param indice
     */
    public int search(int indice) {

        // simulate operation of DFA on text
        int m = pattern.length;
        int n = text.length;
        int i, j;

        for (i = 0, j = 0; i < n && j < m; i++) {
            j = dfa[text[i]][j];
        }

        if (j == m) return i - m;    // found
        return n;                    // not found
    }


    /**
     * Takes a pattern string and an input string as command-line arguments;
     * searches for the pattern string in the text string; and prints
     * the first occurrence of the pattern string in the text string.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        busca("ic", "texto icenorme");
    }

    private static void busca(String pat, String txt) {

        byte[] pattern = Sequencia.toByteArray(pat.toCharArray());
        byte[] text    = Sequencia.toByteArray(txt.toCharArray());

        KMP kmp = new KMP(text);
        kmp.definePadrao(pattern);
        int offset2 = kmp.search(0);

        // print results
        System.out.println("text:    " + txt);

        System.out.print("pattern: ");
        for (int i = 0; i < offset2; i++)
            System.out.print(" ");
        System.out.println(pat);
    }
}
