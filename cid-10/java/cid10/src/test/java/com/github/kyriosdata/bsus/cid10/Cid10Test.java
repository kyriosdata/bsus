package com.github.kyriosdata.bsus.cid10;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class Cid10Test {

    private static Cid10 cid;

    @BeforeClass
    public static void setUp() {
        cid = new Cid10();
        cid.load();
    }

    @Test
    public void testeEntradaExistente() {

        final String[] criterios = {"y112", "intoxicacao", "anti" };

        String[] identificadas = cid.search(criterios);
        assertEquals(1, identificadas.length);

        assertTrue(identificadas[0].contains("[intoxicação]"));
    }

    @Test
    public void entradaInexistenteNaoPodeSerEncontrada() {
        final String[] criterio = { "abcdefg" };
        assertNull(cid.search(criterio));
    }

    @Test
    public void algumasEntradasConhecidas() {
        final String[] criterio = { "xxii" };
        assertTrue(cid.search(criterio)[0].contains("XXII"));
    }

    @Test
    public void outraBusca() {
        final String[] criterio = { "al", "as", "cci", "os", "ar", "it", "um" };
        String[] encontradas = cid.search(criterio);
        assertEquals(1, encontradas.length);
        assertTrue(encontradas[0].contains("A00-B99"));
    }
}
