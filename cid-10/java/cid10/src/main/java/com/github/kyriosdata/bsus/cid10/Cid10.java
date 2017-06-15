package com.github.kyriosdata.bsus.cid10;

/**
 * Created by fabio on 15/06/17.
 */
public class Cid10 {

    /**
     * Permite que a instância da classe seja "preparada"
     * antes do uso.
     */
    public void load() {
    }

    /**
     * Permite que a instância libere recursos que não mais
     * serão empregados.
     */
    public void unload() {
    }

    /**
     * Retorna as entradas que contêm todos os critérios fornecidos.
     *
     * @param criterios Palavras ou trecho de palavras e dos códigos
     *                  das doenças contidas na CID-10.
     *
     * @return {@code null} se nenhuma entrada da CID-10 contém os
     * critérios fornecidos ou, caso contrário, uma entrada (código
     * seguido da descrição) para cada entrada da CID-10 que satisfaz
     * os critérios.
     */
    String[] search(String[] criterios) {
        return null;
    }
}
