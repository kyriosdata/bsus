package com.github.kyriosdata.bsus.cid10.preprocessor;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class IndiceTest {

    @Test
    public void montagemIndice() throws FileNotFoundException {
        final String fileName = "cid-10-subcategorias-lower.json";
        final Subcategorias sc = Subcategorias.newInstance(fileName);

        Map<String, Set<String>> twoLetters = Indice.montaIndice(sc.descricao);
        assertEquals(26 * 26, twoLetters.size());

        Set<String> resposta = busca(new String[] { "zy", "ic", "ab", "ic" }, twoLetters);
        assertEquals(82, resposta.size());
    }

    public Set<String> busca(String[] criterios, Map<String, Set<String>> idx) {

        // Identifique o menor conjunto (menos buscas)
        Set<String> menorConjunto = null;
        int menorTamanho = Integer.MAX_VALUE;
        for (String criterio : criterios) {
            Set<String> corrente = idx.get(criterio);
            if (corrente == null) {
                continue;
            }

            if (menorTamanho > corrente.size()) {
                menorTamanho = corrente.size();
                menorConjunto = corrente;
            }
        }

        return menorConjunto;
    }
}
