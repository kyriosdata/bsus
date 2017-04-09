# CID-10 (JavaScript)

Estão previstos os seguintes módulos para execução no navegador: 

- Dados. Empacotamento dos dados para uso da CID-10 no navegador, sem necessidade de acesso a um servidor remoto. Ou seja, toda a CID-10 é transferida para o cliente. 
- Consulta. Realização de busca na CID-10 por código ou por descrição. 
- Navegação. Visualização da CID-10 como estrutura hierárquica (também auxilia no processo de consulta), o que significa que para um dado nodo pode-se navegar pela hierarquia seguindo seu nodo pai ou um dos nodos descendentes.

## Análise dos requisitos

Segundo o Manual de Certificação da SBIS, o requisito identificado por ESTR.02.03 (Representação e registro de dados hierárquicos) está descrito por dois subtiens: 
- O S-RES deve possibilitar a representação de dados de natureza hierárquica em árvores ou hierarquias, preservando o relacionamento dos nodos pais com os nodos filhos, de tal forma que possibilite a navegação, busca e consulta destes dados em todas as direções e 
- A representação hierárquica deverá ser respeitada minimamente para os seguintes casos: familiograma (caso o S-RES ofereça familiograma); busca e navegação na captura de códigos de terminologias e classificações hierárquicas, como CID e CIAP. Por exemplo, um item de terminologia deve apontar para seu nodo pai, e para seus nodos filhos, se não for nodo terminal, e o usuário, além de poder fazer uma pesquisa por código ou extenso da terminologia, poderá navegar na estrutura hierárquica de alguma maneira.

## Referências
- Manual de Certificação da SBIS ([aqui](http://sbis.org.br/certificacao-sbis)).
