/*
 * Copyright (c) 2017
 *
 * Fábio Nogueira de Lucena
 * Fábrica de Software - Instituto de Informática (UFG)
 *
 * Creative Commons Attribution 4.0 International License.
 */

package com.github.kyriosdata.bsus.cid10.preprocessor;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Aplicativo que visa produzir versão mais compacta da CID-10 e
 * pronta para agilizar a consulta.
 */
public class Main {

    Map<String, List<Integer>> dicionario;

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "cid-10-subcategorias-lower.json";

        Main objeto = new Main();
        Subcategorias cs = objeto.obtemSubcategorias(fileName);
        cs.prepare();
        System.out.println(cs.toString());

        objeto.dicionario = objeto.montaDicionario(cs.descricao);

        List<String> chaves = new ArrayList<>();
        chaves.addAll(objeto.dicionario.keySet());
        String[] tipo = {};
        String[] valores = chaves.toArray(tipo);
        System.out.println("Total de chaves: " + valores.length);

        Pattern p = Pattern.compile("\\(.*\\)");
        //Matcher m = p.matcher("(\\W)");
        // m.find()

//        for (String valor : valores) {
//            if (valor.contains("(")) {
//                List<Integer> idx = objeto.dicionario.get(valor);
//                for (int i : idx) {
//                    System.out.print(valor + " => " + cs.descricao[i] + " | ");
//                }
//                System.out.println();
//            }
//        }


//        Set<Integer> answer = objeto.busca(valores, objeto.dicionario, "ido");

//        System.out.println("Search: ido");
//        for(int indice : answer) {
//            System.out.println(indice + " " + cs.descricao[indice]);
//        }

        long inicio = System.currentTimeMillis();
        long total = 0;
        for (int i = 0; i < 1_000; i++) {
            Set<Integer> resposta = objeto.busca(valores, objeto.dicionario, "ido");
            total += resposta.size();
        }

        System.out.println("Tempo: " + (System.currentTimeMillis() - inicio));

        cs.prepare();
        inicio = System.currentTimeMillis();
        total = 0;
        for (int i = 0; i < 1_000; i++) {
            Set<Integer> resposta = cs.busca("ido");
            total += resposta.size();
        }

        System.out.println("Tempo: " + (System.currentTimeMillis() - inicio));
    }

    private Capitulos obtemCapitulos(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = FileFromResourcesFolder.get(fileName);

        return gson.fromJson(new FileReader(file), Capitulos.class);
    }


    private Subcategorias obtemSubcategorias(String fileName) throws FileNotFoundException {
        File file = FileFromResourcesFolder.get(fileName);

        return new Gson().fromJson(new FileReader(file), Subcategorias.class);
    }

    public void exibeCapitulos(Capitulos caps) {
        System.out.println("Total entradas: " + caps.catfim.length);

        Map<String, List<Integer>> mapa = montaDicionario(caps.descricao);

        dicionario = mapa;

        System.out.println("Total palavras: " + mapa.size());

        for (String chave : mapa.keySet()) {
            System.out.println(chave + " " + mapa.get(chave));
        }
    }

    public Map<String, List<Integer>> montaDicionario(String[] sentencas) {
        Map<String, List<Integer>> mapa = new TreeMap<>();

        for (int i = 0; i < sentencas.length; i++) {
            String sentenca = sentencas[i];
            Pattern p = Pattern.compile("\\(.*\\)");
            Matcher m = p.matcher(sentenca);
            if (m.find()) {
                System.out.println(sentenca);
            }

            String[] palavras = sentenca.split("(\\s+|,|\\[|\\])");
            for (String palavra : palavras) {
                palavra = trataPalavra(palavra);
                if (palavra == null) {
                    continue;
                }

                List<Integer> indices = mapa.get(palavra);
                if (indices == null) {
                    indices = new ArrayList<>();
                    mapa.put(palavra, indices);
                }

                if (indices.contains(i)) {
                    continue;
                }

                indices.add(i);
            }
        }

        return mapa;
    }

    private List<String> paraRemover = Arrays.asList(new String[]{
            "de", "da", "das", "do", "dos",
            "a", "as", "e", "o", "os",
            "na", "nas", "no", "nos",
            "para",
            "que", "com",
            "em",
            "-"
    });

    /**
     * Operações realizadas:
     * (a) toLower (realizado via linha de comandos)
     * cat arquivo.json | tr "[A-Z]" "[a-z]"
     * (b) remover 'de', 'da', 'a', 'e', 'as', 'dos', '[', ']', '-', ',' e outros.
     * (c) eliminar acentos
     */
    public String trataPalavra(String palavra) {

        if (paraRemover.contains(palavra)) {
            return null;
        }

        if (palavra.isEmpty()) {
            return null;
        }

        // Plural (que inclui singular)
        if (palavra.endsWith("(s)")) {
            palavra = palavra.replace("(s)", "s");
        } else if (palavra.endsWith("(es)")) {
            palavra = palavra.replace("(es)", "es");
        } else if (palavra.equals("(\"bypass\")")) {
            palavra = "bypass";
        } else if (palavra.equals("(\"stress\")")) {
            palavra = "stress";
        } else if (palavra.startsWith("(") && palavra.endsWith(")")) {
            palavra = palavra.replace("(", "");
            palavra = palavra.replace(")", "");
        }

        return trocaAcentos(palavra);

    }

    public static String trocaAcentos(String texto) {
        String comAcentos = "äáâàãéêëèíîïìöóôòõüúûùç";
        String semAcentos = "aaaaaeeeeiiiiooooouuuuc";
        final int SIZE = comAcentos.length();

        for (int i = 0; i < SIZE; i++) {
            texto = texto.replace(comAcentos.charAt(i), semAcentos.charAt(i));
        }

        return texto;
    }

    /**
     * Recupera índices de doenças cujas descrições contém a palavra
     * procurada.
     */
    public Set<Integer> busca(String[] chaves, Map<String, List<Integer>> mapa, String procurado) {
        Set<Integer> indices = new HashSet<>();

        for (int i = 0; i < chaves.length; i++) {
            if (chaves[i].contains(procurado)) {
               indices.addAll(mapa.get(chaves[i]));
            }
        }

        return indices;
    }
}
