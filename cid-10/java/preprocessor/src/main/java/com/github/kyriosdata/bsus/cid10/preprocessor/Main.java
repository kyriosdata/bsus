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
 * Aplicativo que visa produzir versão da CID-10 mais compacta e
 * e "mais rápida" para consulta.
 */
public class Main {

    Map<String, List<Integer>> dicionario;

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "cid-10-subcategorias-lower.json";

        Main objeto = new Main();
        Subcategorias sc = Subcategorias.newInstance(fileName);
        sc.prepare();
        System.out.println(sc.toString());

        objeto.dicionario = objeto.montaDicionario(sc.descricao);

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

        sc.prepare();
        inicio = System.currentTimeMillis();
        total = 0;
        for (int i = 0; i < 1_000; i++) {
            Set<Integer> resposta = sc.busca("ido");
            total += resposta.size();
        }

        System.out.println("Tempo: " + (System.currentTimeMillis() - inicio));
    }

    private Capitulos obtemCapitulos(String fileName) throws FileNotFoundException {
        Gson gson = new Gson();
        File file = FileFromResourcesFolder.get(fileName);

        return gson.fromJson(new FileReader(file), Capitulos.class);
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

    /**
     * Monta para o conjunto de sentenças fornecido, um dicionário cuja chave
     * é uma palavra e o valor é a lista dos índices das sentenças nas quais
     * a palavra está presente.
     *
     * <p>O dicionário retornado contém, seguramente, como chaves, as palavras
     * contidas em todas as sentenças de tal forma que buscar por uma palavra
     * ou por parte de uma palavra na sentença, é o mesmo que buscar por uma
     * palavra ou por parte nas chaves do dicionário retornado.
     *
     * @param sentencas Sentenças nas quais palavras ou partes de palavras
     *                  serão buscadas.
     * @return Dicionário contendo as palavras existentes nas sentenças,
     * após processamento. Para cada palavra (chave), os valores armazenados
     * são os índices das sentenças nas quais a palavra ocorre.
     */
    public Map<String, List<Integer>> montaDicionario(String[] sentencas) {
        Map<String, List<Integer>> mapa = new TreeMap<>();

        for (int i = 0; i < sentencas.length; i++) {
            String sentenca = sentencas[i];

            // Apenas para teste temporário
            final String entreParenteses = "\\(.*\\)";
            Pattern p = Pattern.compile(entreParenteses);
            Matcher m = p.matcher(sentenca);
            if (m.find()) {
                //System.out.println(sentenca);
            }

            // A quebra da sentença nos separadores abaixo pode separar
            // palavras entre aspas, entre parênteses, ...
            // Por exemplo, a sentença "muito importante" é dividida em
            // '"muito' (com aspas no início) e 'importante"' (seguida de aspas).
            // ' ', ',', '[', ']'
            final String separadorDePalavra = "(\\s+|,|\\[|\\])";
            String[] palavras = sentenca.split(separadorDePalavra);
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
     * (a) toLower (realizado via linha de comandos); aspas, colchetes e parênteses
     * eliminados das palavras "stress", "bypass" e "screening".
     * dágua substituído por água; 10a. substituído por 10
     * 37a. => 37
     * (osteo)artrose => osteoartrose
     * não-venenosos => nãovenenosos
     * auto-intoxicação => autointoxicação
     * não-ionizante => nãoionizante
     * não-opiáceos => nãoopiáceos
     * anti-reumáticos => antireumáticos
     * não-controlado => nãocontrolado
     * não-de-trânsito => nãodetrânsito
     * não-motorizado => nãomotorizado
     * recém-nascido => recémnascido
     * não-traumática => nãotraumática
     * pre-existente => preexistente
     * (teno)sinovites => tenosinovites
     * febre de o'nyong-nyong => febre de nyong"
     * removidos: m. e. h. b. c. st.
     * (super)infecção => superinfecção
     * (cardio-)pulmonar => cardiopulmonar
     * tr|aumatismo => traumatismo
     * cat arquivo.json | tr "[A-Z]" "[a-z]"
     * (b) remover 'de', 'da', 'a', 'e', 'as', 'dos', '[', ']', '-', ',' e outros.
     * (c) palavra + "(s)" substituída por: palavra + "s"
     * (d) eliminar acentos
     * hífen removido
     * / removido
     * + removido
     */
    public String trataPalavra(String palavra) {

        if (paraRemover.contains(palavra)) {
            return null;
        }

        // Plural (que inclui singular)
        if (palavra.endsWith("(s)")) {
            palavra = palavra.replace("(s)", "s");
        } else if (palavra.endsWith("(es)")) {
            palavra = palavra.replace("(es)", "es");
        }

        if (palavra.startsWith("\"")) {
            palavra = palavra.substring(1);
        }

        if (palavra.endsWith("\"")) {
            palavra = palavra.substring(0, palavra.length() - 1);
        }

        if (palavra.startsWith("(")) {
            palavra = palavra.replace("(", "");
        }

        if (palavra.endsWith(")")) {
            palavra = palavra.replace(")", "");
        }

        if (palavra.contains("-")) {
            palavra = palavra.replace("-", "");
        }

        if (palavra.contains("/")) {
            palavra = palavra.replace("/", "");
        }

        if (palavra.contains("+")) {
            palavra = palavra.replace("+", "");
        }

        palavra = trocaAcentos(palavra);
        ascii(palavra);

        if (palavra.isEmpty()) {
            return null;
        }

        return palavra;
    }

    public static boolean ascii(String texto) {
        for (char c : texto.toCharArray()) {
            if (c > 122) {
                System.out.println(texto);
                return false;
            }

            if (c < 97 && c > 57) {
                System.out.println(texto);
                return false;
            }

            if (c < 48 && c != 37) {
                System.out.println(texto);
                return false;
            }
        }

        return true;
    }

    /**
     * Elimina os acentos produzindo versão ASCII do texto fornecido.
     *
     * @param texto Texto original, possivelmente com acentos.
     *
     * @return Texto correspondente à entrada, mas com acentos eliminados.
     */
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
