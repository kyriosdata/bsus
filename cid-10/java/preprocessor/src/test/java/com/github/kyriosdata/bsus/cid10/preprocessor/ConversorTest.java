package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class ConversorTest {

    @Test
    public void montagemIndice() throws FileNotFoundException {
        Map<String, Set<String>> dados = Conversor.montaIndice("cid-10-subcategorias-lower.json");
        assertEquals(26 * 26, dados.size());
    }
}
