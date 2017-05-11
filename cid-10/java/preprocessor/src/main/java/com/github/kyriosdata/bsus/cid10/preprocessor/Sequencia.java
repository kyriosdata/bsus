/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

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
    private byte[] bytes;

    public Sequencia(byte[] sequencia) {
        bytes = sequencia;
    }

    /**
     * Verifica se uma sequência contém outra. Por exemplo,
     * a sequência 'as' está presente na sequência 'casa'.
     *
     * @param posicao Posição inicial (primeiro byte) da
     *                palavra na sequência.
     *
     * @param sub Subsequência a ser procurada na sequência.
     *
     * @return {@code true} se e somente se a sequência contém a
     * subsequência.
     */
    public boolean contem(int posicao, byte[] sub) {
        int pos = posicao + 1;

        for (byte b : sub) {
            if (b != bytes[pos++]) {
                return false;
            }
        }

        return true;
    }
}
