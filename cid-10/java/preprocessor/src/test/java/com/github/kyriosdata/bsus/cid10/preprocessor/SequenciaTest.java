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

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class SequenciaTest {

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

    @Test
    public void desempenhoComparadoContaisSimples() throws Exception {
        Map<String, Set<String>> dados = Conversor.montaIndice();

        List<String> palavras = new ArrayList<>();
        for (String palavra : dados.get("ic")) {
            palavras.add(palavra);
        }

        Sequencia sequencia = new Sequencia(palavras);
        byte[] sub = {97, 115};

        long start = System.nanoTime();
        int totalContains = 0;
        for (int i = 0; i < 10_000; i++) {
            totalContains = 0;
            for (String palavra : palavras) {
                if (palavra.contains("as")) {
                    totalContains++;
                }
            }
        }

        System.out.println("Contains : " + (System.nanoTime() - start) + " Encontrados: " + totalContains);
        //encontradas.forEach(w -> System.out.println(w));

        start = System.nanoTime();
        int totalSequencia = 0;
        for (int i = 0; i < 10_000; i++) {
            totalSequencia = 0;
            int indice = 0;

            while (indice != -1) {
                if (sequencia.contem(indice, sub)) {
                    totalSequencia++;
                }

                indice = sequencia.proxima(indice);
            }
        }

        System.out.println("Sequencia: " + (System.nanoTime() - start) + " Encontrados: " + totalSequencia);
    }

    @Test
    public void montaSequenciaTest() {
        List<String> palavra = new ArrayList<>();
        palavra.add("casa");
        palavra.add("aaaa");

        byte[] sequencia = Sequencia.montaSequencia(palavra);
        final byte[] casa = {4, 99, 97, 115, 97, 1, 2, 3, 4, 4, 97, 97, 97, 97, 1, 2, 3, 4};
        assertArrayEquals(casa, sequencia);
    }

    @Test
    public void montaCorretamentePalavras() {
        List<String> palavras = Arrays.asList("casa", "sapo", "vitoria");
        Sequencia s = new Sequencia(palavras);

        assertEquals("casa", s.getPalavra(0));
        assertEquals("sapo", s.getPalavra(9));
        assertEquals("vitoria", s.getPalavra(18));
    }

    @Test
    public void montagemVariasPalavras() throws Exception {
        Map<String, Set<String>> dados = Conversor.montaIndice();

        List<String> palavras = new ArrayList<>();
        for (String palavra : dados.get("ic")) {
            palavras.add(palavra);
        }

        Sequencia sequencia = new Sequencia(palavras);
        int indice = 0;
        for (int i = 0; i < palavras.size(); i++) {
            assertEquals(palavras.get(i), sequencia.getPalavra(indice));
            indice = sequencia.proxima(indice);
        }
    }
}