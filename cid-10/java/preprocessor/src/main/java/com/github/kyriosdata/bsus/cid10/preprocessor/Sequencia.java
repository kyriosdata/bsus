/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Representa um conjunto de sequências de bytes, cada uma
 * delas obtida de uma palavra (String) formada apenas por
 * caracters ASCII.
 *
 * <p>O objetivo dessa classe é permitir uma busca eficiente
 * pelas palavras que contém uma determinada sequência de
 * bytes (ou sequência de caracteres).
 *
 */
public class Sequencia {

    /**
     * Conteúdo da sequência. Cada palavra (String) é
     * iniciada pelo byte que indica o tamanho da palavra.
     * Por exemplo, a primeira palavra se inicia em bytes[0] e
     * o valor bytes[0] indica a quantidade de bytes da
     * primeira palavra.
     *
     * <p>Os bytes seguintes, na quantidade indicada pelo
     * primeiro byte, definem a palavra.
     */
    public byte[] bytes;

    public Sequencia(byte[] sequencia) {
        bytes = sequencia;
    }

    public Sequencia(List<String> palavras) {
        this(montaSequencia(palavras));
    }

    public static byte[] montaSequencia(List<String> palavras) {
        ByteBuffer bf = ByteBuffer.allocate(1_000_000);

        for (String palavra : palavras) {

            char[] caracteres = palavra.toCharArray();

            // Tamanho
            bf.put((byte)caracteres.length);

            // String (ASCII)
            for (char caractere : caracteres) {
                bf.put((byte) caractere);
            }
        }

        bf.flip();

        // Vetor apenas com a quantidade de bytes utilizada
        byte[] payload = new byte[bf.limit()];

        ByteBuffer wrap = ByteBuffer.wrap(payload);
        wrap.put(bf);

        return payload;
    }

    public static byte[] toByteArray(char[] ascii) {
        byte[] asciiBytes = new byte[ascii.length];
        for (int i = 0; i < ascii.length; i++) {
            asciiBytes[i] = (byte) ascii[i];
        }

        return asciiBytes;
    }

    /**
     * Recupera a String armazenada na posição indicada.
     *
     * @param posicao Posição da String.
     *
     * @return A String armazenada na posição indicada.
     */
    public String toString(int posicao) {

        return new String(bytes, posicao + 1, (int) bytes[posicao]);
    }

    /**
     * Retorna índice da palavra que sucede aquela de índice fornecido.
     * @param indice Índice de uma palavra.
     *
     * @return Índice da palavra que sucede aquela cujo índice é fornecido.
     */
    public int proxima(int indice) {
        int candidato = indice + bytes[indice] + 1;
        return (candidato < bytes.length) ? candidato : -1;
    }

    /**
     * Verifica se uma sequência contém outra. Por exemplo,
     * a sequência 'as' está presente na sequência 'casa'.
     *
     * @param indice Posição inicial (primeiro byte) da
     *                palavra na sequência.
     *
     * @param sub Subsequência a ser procurada na sequência.
     *
     * @return {@code true} se e somente se a sequência contém a
     * subsequência.
     */
    public boolean contem(int indice, byte[] sub) {
        final int primeiro = indice + 1;
        final int ultimo = indice + bytes[indice];
        final int tamanhoSubSequencia = sub.length;

        // Indica posição do byte da subsequência a ser comparado.
        int pos = 0;

        // Possivelmente percorre todos os bytes da palavra
        for (int i = primeiro; i <= ultimo; i++) {
            if (bytes[i] == sub[pos]) {
                // Se todos os bytes da subsequência foram
                // comparados, então subsequência foi encontrada
                if (++pos == tamanhoSubSequencia) {
                    return true;
                }
            } else {
                pos = 0;
            }
        }

        return false;
    }
}
