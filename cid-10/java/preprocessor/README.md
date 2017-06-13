# Gerador de dados para consulta
O presente projeto visa produzir uma estrutura otimizada 
para busca de códigos na CID-10. O principal atributo a ser
atendido é o desempenho, ou tempo de resposta entre a submissão
de uma consulta e a obtenção dos resultados correspondentes.

### Versão e copyright da CID-10
A versão utilizada encontra-se disponível pelo portal do 
DATASUS 
([CID-10](http://www.datasus.gov.br/cid10/V2008/cid10.htm)). 
Em particular, trata-se da versão de 2008. 

Detalhes da licença estão disponíveis 
[aqui](http://www.datasus.gov.br/cid10/V2008/cid10.htm).

## Estratégia adotada

### Conversão para JSON
Arquivos que formam a base da CID-10 foram convertidos para o 
formato JSON, conforme a tabela abaixo.

| Original                 | Destino                   |
|--------------------------|---------------------------|
| CID-10-CATEGORIAS.CSV    | CID-10-CATEGORIAS.JSON    |
| CID-10-CAPITULOS.CSV     | CID-10-CAPITULOS.JSON     |
| CID-10-GRUPOS.CSV        | CID-10-GRUPOS.JSON        |
| CID-10-SUBCATEGORIAS.CSV | CID-10-SUBCATEGORIAS.JSON |
| CID-O-CATEGORIAS.CSV     | CID-O-CATEGORIAS.JSON     |
| CID-O-GRUPOS.CSV         | CID-O-GRUPOS.JSON                |



