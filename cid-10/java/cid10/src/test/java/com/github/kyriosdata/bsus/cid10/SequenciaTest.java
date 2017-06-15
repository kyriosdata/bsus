/*
 * Copyright (c) 2016
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10;

import com.github.kyriosdata.bsus.cid10.util.KMP;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// TODO mover @ignore para classe de teste especifica para desempenho (comparacao)
public class SequenciaTest {

    final int ITERACOES = 10;
    private static List<String> palavras;
    private static Sequencia sequencia;
    private static String searchStr = "asa";
    private static byte[] searchBytes = Sequencia.toByteArray(searchStr);
    private static int expected = 91;

    @Test
    public void iguais() {
        Sequencia s = new Sequencia(new byte[]{1, 1});
        assertEquals(0, s.encontre(0, new byte[]{1}));
    }

    @Test
    public void diferentes() {
        Sequencia s = new Sequencia(new byte[]{1, 1});
        assertEquals(-1, s.encontre(0, new byte[]{2}));
    }

    @Test
    public void procuradoMaiorQueSequencia() {
        Sequencia s = new Sequencia(new byte[]{1, 1});
        assertEquals(-1, s.encontre(0, new byte[]{1, 2}));
    }

    @Test
    public void sequenciaMaiorQueProcurado() {
        byte[] bytes = {4, 1, 2, 3, 4};
        Sequencia s = new Sequencia(bytes);
        assertEquals(0, s.encontre(0, new byte[]{1}));
        assertEquals(0, s.encontre(0, new byte[]{2}));
        assertEquals(0, s.encontre(0, new byte[]{3}));
        assertEquals(0, s.encontre(0, new byte[]{4}));
        assertEquals(0, s.encontre(0, new byte[]{1, 2}));
        assertEquals(0, s.encontre(0, new byte[]{2, 3}));
        assertEquals(0, s.encontre(0, new byte[]{3, 4}));
        assertEquals(0, s.encontre(0, new byte[]{1, 2, 3}));
        assertEquals(0, s.encontre(0, new byte[]{2, 3, 4}));
        assertEquals(0, s.encontre(0, new byte[]{1, 2, 3, 4}));

        assertEquals(-1, s.encontre(0, new byte[]{5}));
        assertEquals(-1, s.encontre(0, new byte[]{2, 1}));
        assertEquals(-1, s.encontre(0, new byte[]{4, 5}));
        assertEquals(-1, s.encontre(0, new byte[]{1, 2, 3, 4, 5}));
    }

    @BeforeClass
    public static void setUpClass() throws FileNotFoundException {
        Map<String, Set<String>> dados = new HashMap<>();

        palavras = new ArrayList<>();
        for (String chave : dados.keySet()) {
            for (String palavra : dados.get(chave)) {
                palavras.add(palavra);
            }
        }

        sequencia = new Sequencia(palavras);
    }

    @Test
    @Ignore
    public void desempenhoStringContains() throws Exception {
        int total = 0;
        for (int c = 0; c < ITERACOES; c++) {
            total = 0;
            for (int i = 0; i < palavras.size(); i++) {
                if (palavras.get(i).contains(searchStr)) {
                    total++;
                }
            }
        }

        assertEquals(expected, total);
    }

    @Test
    @Ignore
    public void desempenhoIndexOf() throws Exception {
        int total = 0;
        for (int c = 0; c < ITERACOES; c++) {
            total = 0;
            for (int i = 0; i < palavras.size(); i++) {
                if ((palavras.get(i).indexOf(searchStr) != -1)) {
                    total++;
                }
            }
        }

        assertEquals(expected, total);
    }

    @Test
    @Ignore
    public void desempenhoKmp() throws Exception {

        KMP kmp = new KMP(sequencia.toByteArray());
        kmp.definePadrao(searchBytes);

        int total = 0;
        for (int i = 0; i < ITERACOES; i++) {
            total = 0;
            int indice = 0;

            while (true) {
                indice = kmp.encontre(indice);
                if (indice == -1) {
                    break;
                }

                total++;
                indice = indice + sequencia.toByteArray()[indice] + 1;
            }
        }

        assertEquals(expected, total);
    }

    @Test
    @Ignore
    public void desempenhoSequencia() throws Exception {
        int totalSequencia = 0;

        for (int i = 0; i < ITERACOES; i++) {
            totalSequencia = 0;
            int indice = 0;

            while (true) {
                indice = sequencia.encontre(indice, searchBytes);
                if (indice == -1) {
                    break;
                }

                totalSequencia++;
                indice = sequencia.proxima(indice);
            }
        }

        assertEquals(expected, totalSequencia);
    }

    @Test
    public void montaCorretamentePalavras() {
        List<String> palavras = Arrays.asList("casa", "sapo", "vitoria");
        Sequencia s = new Sequencia(palavras);

        assertEquals("casa", s.toString(0));
        assertEquals("sapo", s.toString(5));
        assertEquals("vitoria", s.toString(10));
    }

    @Test
    public void montagemVariasPalavras() throws Exception {
        List<String> palavras = new ArrayList<>(10000);
        for (int i = 0; i < 10_000; i++) {
            palavras.add(UUID.randomUUID().toString());
        }

        Sequencia sequencia = new Sequencia(palavras);
        int indice = 0;
        for (int i = 0; i < palavras.size(); i++) {
            assertEquals(palavras.get(i), sequencia.toString(indice));
            indice = sequencia.proxima(indice);
        }

        assertEquals(10_000, palavras.size());
    }

    @Test
    public void tamanhoDeDados() throws UnsupportedEncodingException {
        final byte[] bytes = new String("sala").getBytes("ASCII");
        assertEquals(4, bytes.length);

        assertEquals("sala", new String(bytes, 0, 4, "ASCII"));
    }

    public File get(String fileName) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    @Test
    public void tamanhos() throws IOException {
        List<String> sentencas = Files.readAllLines(get("cid10.ser").toPath());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(sentencas);
        oos.flush();
        oos.close();

        byte[] sentencasBytes = baos.toByteArray();

        Sequencia cid = new Sequencia(sentencas);
        byte[] sequenciaBytes = cid.toByteArray();

        assertTrue(sentencasBytes.length > sequenciaBytes.length);
    }

    @Test
    public void verificaCid() throws IOException {
        List<String> sentencas = Files.readAllLines(get("cid10.ser").toPath());
        Sequencia cid = new Sequencia(sentencas);

        int indice = 0;
        int total = 0;
        while (indice != -1) {
            indice = cid.proxima(indice);
            total++;
        }

        assertEquals(15672, total);
    }
}