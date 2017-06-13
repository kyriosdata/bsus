package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BuscaTest {

    private static Transformador sc;
    private static Cid c;

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {
        final String fileName = "cid10.json";
        sc = Transformador.newInstance(fileName);

        c = Agrupador.getConteudo("cid10.json", Cid.class);
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

        assertEquals(3, encontradas.size());
    }
}
