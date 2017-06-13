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

import java.io.FileNotFoundException;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class CarregadorTest {

    @Test
    public void totalDeEntradas() throws FileNotFoundException {
        Carregador sub = Carregador.newInstance("cid-10-subcategorias-lower.json");
        assertEquals(12451, sub.subcat.length);
    }
}