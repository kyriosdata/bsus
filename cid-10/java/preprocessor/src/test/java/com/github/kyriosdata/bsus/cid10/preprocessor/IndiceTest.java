package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class IndiceTest {

    @Test
    public void montagemIndice() throws FileNotFoundException {
        Map<String, Set<String>> idxWords = Indice.montaIndice(Subcategorias.newInstance("cid-10-subcategorias-lower.json").descricao);
        assertEquals(26 * 26, idxWords.size());
    }
}
