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

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class SequenciaTest {

    @Test
    public void iguais() {
        Sequencia s = new Sequencia(new byte[] { 1, 1 });
        assertTrue(s.contem(0, new byte[]{ 1 }));
    }

    @Test
    public void diferentes() {
        Sequencia s = new Sequencia(new byte[] { 1, 1 });
        assertFalse(s.contem(0, new byte[]{ 2 }));
    }

    @Test
    public void procuradoMaiorQueSequencia() {
        Sequencia s = new Sequencia(new byte[] { 1, 1 });
        assertFalse(s.contem(0, new byte[]{ 1, 2 }));
    }
}
