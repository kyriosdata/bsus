/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.Test;
import org.junit.rules.Stopwatch;

import java.nio.ByteBuffer;
import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class SequenciaTest {

    @Test
    public void iguais() {
        Sequencia s = new Sequencia(new byte[] { 1, 1 });
        assertTrue(s.contem(0, new byte[]{ 1 }));
    }

    @Test
    public void diferentes() {
        Sequencia s = new Sequencia(new byte[] { 1, 1 });
        assertFalse(s.contem(0, new byte[]{ 2 }));
    }

    @Test
    public void procuradoMaiorQueSequencia() {
        Sequencia s = new Sequencia(new byte[] { 1, 1 });
        assertFalse(s.contem(0, new byte[]{ 1, 2 }));
    }

    @Test
    public void sequenciaMaiorQueProcurado() {
        byte[] bytes = {4, 1, 2, 3, 4 };
        Sequencia s = new Sequencia(bytes);
        assertTrue(s.contem(0, new byte[]{ 1 }));
        assertTrue(s.contem(0, new byte[]{ 2 }));
        assertTrue(s.contem(0, new byte[]{ 3 }));
        assertTrue(s.contem(0, new byte[]{ 4 }));
        assertTrue(s.contem(0, new byte[]{ 1, 2 }));
        assertTrue(s.contem(0, new byte[]{ 2, 3 }));
        assertTrue(s.contem(0, new byte[]{ 3, 4 }));
        assertTrue(s.contem(0, new byte[]{ 1, 2, 3 }));
        assertTrue(s.contem(0, new byte[]{ 2, 3, 4 }));
        assertTrue(s.contem(0, new byte[]{ 1, 2, 3, 4 }));

        assertFalse(s.contem(0, new byte[]{ 5 }));
        assertFalse(s.contem(0, new byte[]{ 2, 1 }));
        assertFalse(s.contem(0, new byte[]{ 4, 5 }));
        assertFalse(s.contem(0, new byte[]{ 1, 2, 3, 4, 5 }));
    }

    @Test
    public void desempenhoComparadoContaisSimples() throws Exception {
        Map<String, Set<String>> dados = Conversor.montaIndice();

        long start = System.nanoTime();
        for (int i = 0; i < 1_000_000; i++) {
            if ("casa".contains("as") != true) {
                throw new RuntimeException();
            }
        }

        System.out.println(System.nanoTime() - start);

        start = System.nanoTime();
        Sequencia s = new Sequencia(new byte[] { 4, 99, 97, 115, 97});
        byte[] sub = { 97, 115 };
        for (int i = 0; i < 1_000_000; i++) {
            if (s.contem(0, sub) != true) {
                throw new RuntimeException();
            }
        }

        System.out.println(System.nanoTime() - start);
    }

    @Test
    public void montaSequenciaTest() {
        List<String> palavra = new ArrayList<>();
        palavra.add("casa");
        palavra.add("aaaa");

        byte[] sequencia = montaSequencia(palavra);
        final byte[] casa = {4, 99, 97, 115, 97, 1, 2, 3, 4, 4, 97, 97, 97, 97, 1, 2, 3, 4};
        assertArrayEquals(casa, sequencia);
    }

    public byte[] montaSequencia(List<String> palavras) {
        ByteBuffer bf = ByteBuffer.allocate(10_000);

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
}
