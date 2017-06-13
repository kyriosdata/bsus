package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BuscaTest {

    private static Carregador sc;

    @BeforeClass
    public static void montagemIndice() throws FileNotFoundException {
        final String fileName = "cid-10-subcategorias-lower.json";
        sc = Carregador.newInstance(fileName);
    }

    @Test
    public void buscaPorPalavraInexistente() {
        Busca idx = new Busca(sc.descricao);

        final String[] criterios = {"ic", "costelas" };
        Set<String> encontradas = idx.encontre(criterios);
        assertEquals(2, encontradas.size());
    }
}
