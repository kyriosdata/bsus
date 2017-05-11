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

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

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
    public void desempenhoComparadoContaisSimples() {
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
}
