/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um conjunto de sequências de bytes, cada uma
 * delas obtida de uma palavra (String) formada apenas por
 * caracters ASCII.
 * <p>
 * <p>O objetivo dessa classe é permitir uma encontre eficiente
 * pelas palavras que contém uma determinada sequência de
 * bytes (ou sequência de caracteres).
 */
public class Sequencia {

    /**
     * Conteúdo da sequência. Cada palavra (String) é
     * iniciada pelo byte que indica o tamanho da palavra.
     * Por exemplo, a primeira palavra se inicia em bytes[0] e
     * o valor bytes[0] indica a quantidade de bytes da
     * primeira palavra.
     * <p>
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

        int totalBytes = tamanhoEmBytes(palavras);
        int totalPalavras = palavras.size();

        // Total de bytes mais 1 byte por palavra (tamanho)
        int tamanhoVetor = totalPalavras + totalBytes;

        byte[] vetor = new byte[tamanhoVetor];

        int pos = 0;
        for (String palavra : palavras) {

            // Obtém bytes correspondentes à palavra
            byte[] palavraAsStr = toByteArray(palavra);
            int length = palavraAsStr.length;

            // Define o tamanho da palavra (primeiro byte)
            vetor[pos] = (byte) length;

            // Posição do primeiro byte correspondente ao
            // primeiro caractere da palavra.
            pos = pos + 1;

            // Efetua cópia para o destino
            System.arraycopy(palavraAsStr, 0, vetor, pos, length);

            // Atualiza posição para a próxima palavra
            pos = pos + length;
        }

        return vetor;
    }

    /**
     * Identifica tamanho em bytes necessário para armazenar
     * os caracteres (ASCII) de todas as palavras da lista
     * fornecida.
     *
     * @param palavras Lista de palavras.
     * @return Total de bytes necessário para armazenar todas
     * as palavras da lista.
     */
    public static int tamanhoEmBytes(List<String> palavras) {
        int totalBytes = 0;
        for (String palavra : palavras) {
            totalBytes += palavra.length();
        }

        return totalBytes;
    }

    /**
     * Produz a sequência de bytes (ASCII) correspondente à
     * sequência de caracteres fornecida.
     *
     * @param ascii A String (ASCII) cuja sequência de bytes é produzida.
     *
     * @return A sequência de bytes para a String conforme a codificação ASCII.
     */
    public static byte[] toByteArray(String ascii) {
        try {
            return ascii.getBytes("ASCII");
        } catch (UnsupportedEncodingException exp) {
            return null;
        }
    }

    /**
     * Recupera a String armazenada na posição indicada.
     *
     * @param posicao Posição da String.
     * @return A String armazenada na posição indicada.
     */
    public String toString(int posicao) {

        return new String(bytes, posicao + 1, tamanho(posicao));
    }

    /**
     * Retorna índice da palavra que sucede aquela de índice fornecido.
     *
     * @param indice Índice de uma palavra. Índice deve ser válido.
     * @return Índice da palavra que sucede aquela cujo índice é fornecido.
     */
    public int proxima(int indice) {
        int candidato = indice + tamanho(indice) + 1;
        return (candidato < bytes.length) ? candidato : -1;
    }

    /**
     * Verifica se uma sequência contém outra. Por exemplo,
     * a sequência 'as' está presente na sequência 'casa'.
     *
     * @param indice Posição inicial (primeiro byte) da
     *               palavra a partir da qual o padrão será
     *               procurado na sequência.
     * @param padrao Subsequência a ser procurada na sequência.
     * @return {@code true} se e somente se a sequência contém a
     * subsequência.
     */
    public int encontre(int indice, byte[] padrao) {
        final int tamanhoSubSequencia = padrao.length;
        int primeiroByte = indice;
        int proximaPalavra = primeiroByte + tamanho(indice) + 1;

        // Indica posição do byte da subsequência a ser comparado.
        int pos = 0;

        while (true) {

            // Possivelmente percorre todos os bytes da palavra
            for (int i = indice + 1; i < proximaPalavra; i++) {
                if (bytes[i] == padrao[pos]) {
                    // Se todos os bytes da subsequência foram
                    // comparados, então subsequência foi encontrada
                    if (++pos == tamanhoSubSequencia) {
                        return indice;
                    }
                } else {
                    pos = 0;
                }
            }

            if (proximaPalavra >= bytes.length) {
                return -1;
            }

            indice = proximaPalavra;
            proximaPalavra = proximaPalavra + tamanho(proximaPalavra) + 1;
            pos = 0;
        }
    }

    public int tamanho(int indice) {
        return bytes[indice] & 0xFF;
    }

    public List<Integer> procurePor(String[] criterios) {
        byte[][] procuradas = new byte[criterios.length][];
        for (int i = 0; i < criterios.length; i++) {
            procuradas[i] = criterios[i].getBytes();
        }

        List<Integer> encontradas = new ArrayList<>();

        int total = 0;

        // while que percorre todas as entradas
        int indice = 0;
        while (indice != -1) {

            total++;

//            // Itere por todas as palavras que devem estar presentes
//            boolean encontrada = true;
//            for (byte[] procurada : procuradas) {
//                int resultado = encontre(indice, procurada);
//                if (resultado == -1) {
//                    encontrada = false;
//                    break;
//                }
//            }
//
//            if (encontrada) {
//                encontradas.add(indice);
//            }

            System.out.println(indice + " " + toString(indice));
            indice = proxima(indice);
        }

        System.out.println(total);

        return encontradas;
    }
}
