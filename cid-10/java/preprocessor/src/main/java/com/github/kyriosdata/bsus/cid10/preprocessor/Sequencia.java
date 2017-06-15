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
     * iniciada pelo byte que indica o getTamanho da palavra.
     * Por exemplo, a primeira palavra se inicia em bytes[0] e
     * o valor bytes[0] indica a quantidade de bytes da
     * primeira palavra.
     * <p>
     * <p>Os bytes seguintes, na quantidade indicada pelo
     * primeiro byte, definem a palavra.
     */
    public byte[] bytes;

    /**
     * Cria uma instância que encapsula o vetor de bytes fornecido.
     *
     * @param sequencia Vetor de bytes correspondente a uma sequência.
     */
    public Sequencia(byte[] sequencia) {
        bytes = sequencia;
    }

    /**
     * Cria uma instância a partir da lista de palavras fornecidas.
     *
     * @param palavras Lista de palavras correspondentes à sequência a ser
     *                 construída. Uma palavra é uma sequência de caracteres
     *                 ASCII com tamanho interior a 256 caracteres (bytes).
     */
    public Sequencia(List<String> palavras) {

        this(new byte[getTamanhoVetor(palavras)]);

        preencheValores(palavras, bytes);
    }

    private void preencheValores(List<String> palavras, byte[] vetor) {
        int pos = 0;
        for (String palavra : palavras) {

            // Obtém bytes correspondentes à palavra
            byte[] palavraAsStr = toByteArray(palavra);
            int length = palavraAsStr.length;

            // Define o getTamanho da palavra (primeiro byte)
            try {
                setTamanho(pos, length);
            } catch(Exception exp) {
                System.out.println(palavra);
            }

            // Posição do primeiro byte correspondente ao
            // primeiro caractere da palavra.
            pos = pos + 1;

            // Efetua cópia para o destino
            System.arraycopy(palavraAsStr, 0, vetor, pos, length);

            // Atualiza posição para a próxima palavra
            pos = pos + length;
        }
    }

    private static int getTamanhoVetor(List<String> palavras) {
        int totalBytes = tamanhoEmBytes(palavras);
        int totalPalavras = palavras.size();

        // Total de bytes mais 1 byte por palavra (getTamanho)
        return totalPalavras + totalBytes;
    }

    /**
     * Identifica getTamanho em bytes necessário para armazenar
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
     * Recupera o vetor de bytes empregado pela instância.
     *
     * @return Vetor de bytes sobre o qual está serializada uma lista de
     * sequências de caracteres (palavras).
     */
    public byte[] toByteArray() {
        return bytes;
    }

    /**
     * Recupera a String armazenada na posição indicada.
     *
     * @param posicao Posição da String.
     * @return A String armazenada na posição indicada.
     */
    public String toString(int posicao) {

        return new String(bytes, posicao + 1, getTamanho(posicao));
    }

    /**
     * Retorna índice da palavra que sucede aquela de índice fornecido.
     *
     * @param indice Índice de uma palavra. Índice deve ser válido.
     * @return Índice da palavra que sucede aquela cujo índice é fornecido.
     */
    public int proxima(int indice) {
        int candidato = indice + getTamanho(indice) + 1;
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
        int proximaPalavra = primeiroByte + getTamanho(indice) + 1;

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
            proximaPalavra = proximaPalavra + getTamanho(proximaPalavra) + 1;
            pos = 0;
        }
    }

    /**
     * Verifica se a palavra (sequência) na posição indicada contém a
     * sequência de bytes indicada.
     *
     * @param posicao Posição da sequência na qual a busca será feita.
     *
     * @param padrao Sequência de bytes que possivelmente faz parte da
     *               sequência na qual a busca será feita.
     *
     * @return {@code true} se e somente se o padrão fornecido encontra-se,
     * está contido na sequência indicada pela posição fornecida.
     */
    public boolean contem(int posicao, byte[] padrao) {
        final int tamanhoPadrao = padrao.length;
        int p = 0;
        int proxima = posicao + getTamanho(posicao) + 1;

        // Padrão maior que a sequência na qual a busca será feita?
        if (getTamanho(posicao) < tamanhoPadrao) {
            return false;
        }

        for (int i = posicao + 1; i < proxima; i++) {
            if (bytes[i] == padrao[p]) {
                // Se todos os bytes da subsequência foram
                // comparados, então subsequência foi encontrada
                if (++p == tamanhoPadrao) {
                    return true;
                }
            } else {
                p = 0;
            }
        }

        return false;
    }

    /**
     * Recupera o tamanho da sequência de caracteres armazenada
     * na posição indicada.
     *
     * @param posicao Posição da sequência de caracteres cujo tamanho
     *                é requisitado.
     * @return Tamanho da sequência de caracteres, em bytes, armazenada na
     * posição indicada.
     */
    public int getTamanho(int posicao) {
        return bytes[posicao] & 0xFF;
    }

    /**
     * Registra o tamanho da sequência de caracteres a ser armazenada na
     * posição indicada.
     *
     * @param indice Posição da sequência de caracteres cujo tamanho deve
     *               ser registrado.
     * @param valor  Quantidade de caracteres da sequência a ser armazenada,
     *               em bytes.
     */
    public void setTamanho(int indice, int valor) {
        if (valor > 255) {
            throw new IllegalArgumentException("indice " + indice + " " + valor);
        }

        bytes[indice] = (byte) valor;
    }

    public List<Integer> procurePor(String[] criterios) {
        byte[][] procuradas = new byte[criterios.length][];
        for (int i = 0; i < criterios.length; i++) {
            procuradas[i] = criterios[i].getBytes();
        }

        return procurePor(procuradas);
    }

    private List<Integer> procurePor(byte[][] procuradas) {
        List<Integer> encontradas = new ArrayList<>();

        // IMPORTANTE: ordem das sentenças empregada para
        // montagem da resposta que não indica a posição
        // no vetor de bytes, mas a ordem da sentença no
        // conjunto de sentenças.
        int ordem = 0;

        // while que percorre todas as entradas
        int indice = 0;
        while (indice != -1) {

            // Procure por todas as entradas que contém os critérios
            // Inicialmente assume-se que todas estão presentes
            boolean presente = true;
            for (byte[] procurada : procuradas) {
                if (!contem(indice, procurada)) {
                    presente = false;
                    break;
                }
            }

            if (presente) {
                encontradas.add(ordem);
            }

            indice = proxima(indice);
            ordem++;
        }

        return encontradas;
    }
}
