package com.github.kyriosdata.bsus.cid10;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by fabio on 15/06/17.
 */
public class Cid10Test {

    private static Cid10 cid;

    @Test
    public void buscaTrivial() {
        Cid10 c = new Cid10();
        c.load();
    }

    @Test
    public void buscaPorPalavraInexistente() {

        final String[] criterios = {"y112", "intoxicacao", "anti" };

        String[] identificadas = cid.search(criterios);
        assertEquals(1, identificadas.length);

        assertTrue(identificadas[0].contains("[intoxicação]"));
    }
}
