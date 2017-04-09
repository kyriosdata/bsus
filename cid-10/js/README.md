# CID-10 no Browser

Estão previstos vários módulos: 

- Dados. Empacotamento dos dados, possivelmente JSON, para consumo da CID-10 sem acesso a servidor.
- Consulta. Realiza busca na CID-10 por código ou por descrição. 
- Apresentação. Visualização da CID-10 como árvore.

## Análise dos requisitos

Derivados do Manual de Certificação da SBIS:
- ESTR.02.03 (Representação e registro de dados hierárquicos): (a) O S-RES deve possibilitar a representação de dados de natureza hierárquica em árvores ou hierarquias, preservando o relacionamento dos nodos pais com os nodos filhos, de tal forma que possibilite a navegação, busca e consulta destes dados em todas as direções e (b) A representação hierárquica deverá ser respeitada minimamente para os seguintes casos: familiograma (caso o S-RES ofereça familiograma); busca e navegação na captura de códigos de terminologias e classificações hierárquicas, como CID e CIAP. Por exemplo, um item de terminologia deve apontar para seu nodo pai, e para seus nodos filhos, se não for nodo terminal, e o usuário, além de poder fazer uma pesquisa por código ou extenso da terminologia, poderá navegar na estrutura hierárquica de alguma maneira.

## Referências
- Manual de Certificação da SBIS ([aqui](http://sbis.org.br/certificacao-sbis)).
