package com.github.kyriosdata.bsus.cid10.v2008;

import com.github.kyriosdata.bsus.cid10.Cid;

/**
 * Implementação de operações de acesso aos códigos
 * da CID-10, versão 2008.
 */
public class Cid2008 implements Cid {

    @Override
    public void load() {
    }

    @Override
    public void unload() {

    }

    @Override
    public String[] code(String[] codigo) {
        return new String[0];
    }

    @Override
    public String[] description(String[] textos) {
        return new String[0];
    }

    @Override
    public String[] codeAndDescription(String code, String[] textos) {
        return new String[0];
    }
}
