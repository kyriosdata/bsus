/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertArrayEquals;

public class SequenciaTest {

    final int ITERACOES = 100;
    private static List<String> palavras;

    @Test
    public void iguais() {
        Sequencia s = new Sequencia(new byte[]{1, 1});
        assertTrue(s.contem(0, new byte[]{1}));
    }

    @Test
    public void diferentes() {
        Sequencia s = new Sequencia(new byte[]{1, 1});
        assertFalse(s.contem(0, new byte[]{2}));
    }

    @Test
    public void procuradoMaiorQueSequencia() {
        Sequencia s = new Sequencia(new byte[]{1, 1});
        assertFalse(s.contem(0, new byte[]{1, 2}));
    }

    @Test
    public void sequenciaMaiorQueProcurado() {
        byte[] bytes = {4, 1, 2, 3, 4};
        Sequencia s = new Sequencia(bytes);
        assertTrue(s.contem(0, new byte[]{1}));
        assertTrue(s.contem(0, new byte[]{2}));
        assertTrue(s.contem(0, new byte[]{3}));
        assertTrue(s.contem(0, new byte[]{4}));
        assertTrue(s.contem(0, new byte[]{1, 2}));
        assertTrue(s.contem(0, new byte[]{2, 3}));
        assertTrue(s.contem(0, new byte[]{3, 4}));
        assertTrue(s.contem(0, new byte[]{1, 2, 3}));
        assertTrue(s.contem(0, new byte[]{2, 3, 4}));
        assertTrue(s.contem(0, new byte[]{1, 2, 3, 4}));

        assertFalse(s.contem(0, new byte[]{5}));
        assertFalse(s.contem(0, new byte[]{2, 1}));
        assertFalse(s.contem(0, new byte[]{4, 5}));
        assertFalse(s.contem(0, new byte[]{1, 2, 3, 4, 5}));
    }

    @BeforeClass
    public static void setUp() throws FileNotFoundException {
        Map<String, Set<String>> dados = Conversor.montaIndice();

        palavras = new ArrayList<>();
        for (String chave : dados.keySet()) {
            for (String palavra : dados.get(chave)) {
                palavras.add(palavra);
            }
        }
    }

    @Test
    public void desempenhoStringContains() throws Exception {
        int total = 0;
        boolean verdade = true;
        while (verdade) {
            for (String palavra : palavras) {
                if (palavra.contains("asa")) {
                    total++;
                }
            }
        }

        System.out.println(total);
    }

    @Test
    public void desempenhoKmp() throws Exception {
        Sequencia sequencia = new Sequencia(palavras);
        byte[] sub = {97, 115, 97};

        KMP kmp = new KMP(sequencia.bytes);
        kmp.definePadrao(sub);

        while (true) {
            int indice = 0;

            while (true) {
                indice = kmp.search(indice);
                if (indice == -1) {
                    break;
                }

                indice = indice + sequencia.bytes[indice] + 1;
            }
        }
    }

    @Test
    public void desempenhoSequencia() throws Exception {
        Sequencia sequencia = new Sequencia(palavras);
        byte[] sub = {97, 115, 97};

        int totalSequencia = 0;
        boolean verdade = true;

        while (verdade) {
            totalSequencia = 0;
            int indice = 0;

            while (indice != -1) {
                if (sequencia.contem(indice, sub)) {
                    totalSequencia++;
                }

                indice = sequencia.proxima(indice);
            }
        }

        System.out.println(totalSequencia);
    }

    @Test
    public void montaSequenciaTest() {
        List<String> palavra = new ArrayList<>();
        palavra.add("casa");
        palavra.add("aaaa");

        byte[] sequencia = Sequencia.montaSequencia(palavra);
        final byte[] casa = {4, 99, 97, 115, 97, 4, 97, 97, 97, 97};
        assertArrayEquals(casa, sequencia);
    }

    @Test
    public void montaCorretamentePalavras() {
        List<String> palavras = Arrays.asList("casa", "sapo", "vitoria");
        Sequencia s = new Sequencia(palavras);

        assertEquals("casa", s.getPalavra(0));
        assertEquals("sapo", s.getPalavra(5));
        assertEquals("vitoria", s.getPalavra(10));
    }

    @Test
    public void montagemVariasPalavras() throws Exception {
        Map<String, Set<String>> dados = Conversor.montaIndice();

        List<String> palavras = new ArrayList<>();
        for (String chave : dados.keySet()) {
            for (String palavra : dados.get(chave)) {
                palavras.add(palavra);
            }
        }

        Sequencia sequencia = new Sequencia(palavras);
        int indice = 0;
        for (int i = 0; i < palavras.size(); i++) {
            assertEquals(palavras.get(i), sequencia.getPalavra(indice));
            indice = sequencia.proxima(indice);
        }

        int i = 0;
        indice = 0;
        while (indice != -1) {
            assertEquals(palavras.get(i++), sequencia.getPalavra(indice));
            indice = sequencia.proxima(indice);
        }
    }

    @Test
    public void tamanhoDeDados() throws UnsupportedEncodingException {
        final byte[] bytes = new String("açaí").getBytes("UTF-8");
        assertEquals(6, bytes.length);
    }
}