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

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {
        final String fileName = "cid10.json";
        sc = Transformador.newInstance(fileName);

        c = Agrupador.getConteudo("cid10.json", Cid.class);

        final List<String> codigosList = Arrays.asList(sc.codigo);
        codigos = new Sequencia(codigosList);

        final List<String> descricoesList = Arrays.asList(sc.descricao);
        descricoes = new Sequencia(descricoesList);
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

        descricoes.procurePor(criterios);
        System.out.println(sc.codigo.length);

        assertEquals(3, encontradas.size());
    }
}
