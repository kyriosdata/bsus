/**
 * Pacote que reúne classes empregadas para geração de estrutura de dados
 * para busca de códigos na classificação CID-10.
 *
 * <p>O conteúdo da CID-10 foi primeiramente convertido para arquivos JSON
 * equivalentes. Dessa forma foi possível usar o programa
 * {@link com.github.kyriosdata.bsus.cid10.preprocessor.Agrupador}, por
 * meio de GSON, para reunir todos os códigos e descrições da CID-10 em um
 * único arquivo: cid10.json. Essa tarefa de agrupar os códigos faz uso de
 * várias classes:
 * {@link com.github.kyriosdata.bsus.cid10.preprocessor.json.Capitulos},
 * {@link com.github.kyriosdata.bsus.cid10.preprocessor.json.Categorias} e outras,
 * de fato, uma classe para cada arquivo JSON obtido dos arquivos originais
 * (CSV) da CID-10.
 *
 * <p>O arquivo cid10.json é mantido conforme gerado, pois dele serão
 * retornadas as sentenças encontradas por meio de buscas. Isso porque,
 * com o propósito de tornar mais eficiente a busca, a estrutura de dados
 * empregada para busca é diferente daquela empregada para consulta.
 * Todas as "transformações" são realizadas pela classe
 * {@link com.github.kyriosdata.bsus.cid10.preprocessor.Transformador}.
 */
package com.github.kyriosdata.bsus.cid10.preprocessor;