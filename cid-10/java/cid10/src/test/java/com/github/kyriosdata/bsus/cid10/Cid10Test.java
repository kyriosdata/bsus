package com.github.kyriosdata.bsus.cid10;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Cid10Test {

    private static Cid10 cid;

    @BeforeClass
    public static void setUp() {
        cid = new Cid10();
        cid.load();
    }

    @Test
    public void buscaPorPalavraInexistente() {

        final String[] criterios = {"y112", "intoxicacao", "anti" };

        String[] identificadas = cid.search(criterios);
        assertEquals(1, identificadas.length);

        assertTrue(identificadas[0].contains("[intoxicação]"));
    }
}
