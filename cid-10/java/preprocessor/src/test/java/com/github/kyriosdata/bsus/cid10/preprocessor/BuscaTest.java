package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BuscaTest {

    private static Transformador sc;

    // Dados "originais" sem transformações
    private static Cid c;

    private static Sequencia codigos;
    private static Sequencia descricoes;
    private static List<String> codigosList;
    private static List<String> descricoesList;

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {
        final String fileName = "cid10.json";
        sc = Transformador.newInstance(fileName);

        c = Agrupador.getConteudo("cid10.json", Cid.class);

        codigosList = Arrays.asList(sc.codigo);
        codigos = new Sequencia(codigosList);

        descricoesList = Arrays.asList(sc.descricao);
        descricoes = new Sequencia(descricoesList);
    }

    @Test
    public void montagemCorreta() {
        int idxCode = 0;
        int idxDesc = 0;
        for (int i = 0; i < sc.codigo.length; i++) {
            assertEquals(codigosList.get(i), sc.codigo[i]);
            assertEquals(sc.codigo[i], codigos.toString(idxCode));
            assertEquals(sc.descricao[i], descricoes.toString(idxDesc));
            idxCode = codigos.proxima(idxCode);
            idxDesc = descricoes.proxima(idxDesc);
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
        System.out.println(sc.codigo.length);

        assertEquals(identificadas.size(), encontradas.size());
    }
}
