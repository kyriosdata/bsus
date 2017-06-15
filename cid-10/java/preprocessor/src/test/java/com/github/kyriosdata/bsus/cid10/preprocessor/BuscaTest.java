package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.github.kyriosdata.bsus.cid10.preprocessor.json.Cid;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuscaTest {

    private static Transformador sc;

    // Dados "originais" sem transformações
    private static Cid c;

    private static Sequencia codigos;
    private static Sequencia descricoes;
    private static List<String> codigosList;
    private static List<String> descricoesList;
    private static List<String> sentencas;

    private static Sequencia cid;

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {
        final String fileName = "cid10.json";
        sc = Transformador.newInstance(fileName);

        c = FileFromResourcesFolder.getConteudo("cid10.json", Cid.class);

        codigosList = Arrays.asList(sc.codigo);
        descricoesList = Arrays.asList(sc.descricao);

        codigos = new Sequencia(codigosList);
        descricoes = new Sequencia(descricoesList);

        sentencas = new ArrayList<>();
        for (int i = 0; i < sc.codigo.length; i++) {
            sentencas.add(sc.codigo[i] + " " + sc.descricao[i]);
        }

        cid = new Sequencia(sentencas);
    }

    @Test
    public void analisaSentencas() {
        int maxSize = 0;
        for (String sentenca : sentencas) {
            if (sentenca.length() > maxSize) {
                maxSize = sentenca.length();
            }
        }

        assertTrue(maxSize < 256);
    }

    @Test
    public void verificaCid() {
        int indice = 0;
        int total = 0;
        while (indice != -1) {
            System.out.println(cid.toString(indice));
            indice = cid.proxima(indice);
            total++;
        }

        System.out.println("Size in bytes: " + cid.toByteArray().length + " Total: " + total);
    }

    @Test
    public void montagemCorreta() {
        int idxCode = 0;
        int idxDesc = 0;
        int idxCid = 0;
        for (int i = 0; i < sc.codigo.length; i++) {
            assertEquals(codigosList.get(i), sc.codigo[i]);
            assertEquals(sc.codigo[i], codigos.toString(idxCode));
            assertEquals(sc.descricao[i], descricoes.toString(idxDesc));
            assertTrue(cid.toString(idxCid), cid.toString(idxCid).contains(sc.codigo[i]));
            assertTrue(cid.toString(idxCid).contains(sc.descricao[i]));
            idxCode = codigos.proxima(idxCode);
            idxDesc = descricoes.proxima(idxDesc);
            idxCid = cid.proxima(idxCid);
        }
    }

    @Test
    public void buscaPorPalavraInexistente() {
        Busca idx = new Busca(sc.descricao);

        final String[] criterios = {"ic", "costelas" };
        List<Integer> encontradas = idx.encontre(criterios);
        for (int encontrada : encontradas) {
            System.out.println(sc.codigo[encontrada] + " " + sc.descricao[encontrada]);
        }

        for (int encontrada : encontradas) {
            System.out.println(c.codigo.get(encontrada) + " " + c.descricao.get(encontrada));
        }

        List<Integer> identificadas = descricoes.procurePor(criterios);
        for (int i : identificadas) {
            System.out.println(descricoes.toString(i));
        }

        assertEquals(identificadas.size(), encontradas.size());
    }
}
