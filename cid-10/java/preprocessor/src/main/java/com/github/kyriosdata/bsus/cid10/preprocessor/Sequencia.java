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
     * Conteúdo da sequência. Cas palavra (String) é
     * iniciada pelo byte que indica o tamanho da palavra.
     * Por exemplo, a primeira palavra se inicia em bytes[0]e
     * o valor bytes[0] indica a quantidade de bytes da
     * primeira palavra.
     *
     * <p>Os bytes seguintes, na quantidade indicada pelo
     * primeiro byte, definem a palavra. Imediatamente após
     * o último byte da palavra seguem 4 bytes (valor inteiro).
     * Esse valor é de uso externo. Imediatamente após o
     * último desses 4 bytes, segue o primeiro byte da palavra
     * seguinte que, conforme mencionado, indica o tamanho
     * dessa palavra e assim por diante.
     */
    public byte[] bytes;

    public Sequencia(byte[] sequencia) {
        bytes = sequencia;
    }

    public Sequencia(List<String> palavras) {
        this(montaSequencia(palavras));
    }

    public static byte[] montaSequencia(List<String> palavras) {
        ByteBuffer bf = ByteBuffer.allocate(100_000);

        for (String palavra : palavras) {
            char[] caracteres = palavra.toCharArray();

            // Tamanho
            bf.put((byte)caracteres.length);

            // String (ASCII)
            for (char caractere : caracteres) {
                bf.put((byte) caractere);
            }

            // Valor para uso futuro
            bf.put(new byte[] { 1, 2, 3, 4});
        }

        bf.flip();
        byte[] payload = new byte[bf.limit()];
        ByteBuffer wrap = ByteBuffer.wrap(payload);
        wrap.put(bf);

        return wrap.array();
    }

    public String getPalavra(int indice) {
        int size = bytes[indice];

        char[] palavra = new char[size];
        for (int i = 0; i < size; i++) {
            palavra[i] = (char)bytes[++indice];
        }

        return new String(palavra);
    }

    /**
     * Retorna índice da palavra que sucede aquela de índice fornecido.
     * @param indice Índice de uma palavra.
     *
     * @return Índice da palavra que sucede aquela cujo índice é fornecido.
     */
    public int proxima(int indice) {
        int candidato = indice + bytes[indice] + 5;
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
            }
        }

        return false;
    }
}
