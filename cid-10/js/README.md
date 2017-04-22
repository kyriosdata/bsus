# CID-10 (JavaScript)

Estão previstos os seguintes módulos para execução no navegador: 

- **Dados**. Empacotamento dos dados para uso da CID-10 no navegador, sem necessidade de acesso a um servidor remoto. Ou seja, todo o conteúdo da a CID-10 é transferida para o cliente. 
- **Consulta**. Realização de busca na CID-10 por código ou por descrição. Em campo específico para o código e outro para a descrição, pode-se restringir o conjunto de entradas da CID-10 ilustrados na tabela para aqueles que contêm os valores indicados nos campos. A tabela contendo os códigos da CID deve possuir k entradas, ou seja, em qualquer instante, no máximo, são exibidas k entradas, de i até i + k -1.
- **Navegação**. Visualização da CID-10 como estrutura hierárquica (também auxilia no processo de consulta), o que significa que para um dado nodo pode-se navegar pela hierarquia seguindo seu nodo pai ou um dos nodos descendentes.

## Análise dos requisitos

Segundo o Manual de Certificação da SBIS, o requisito identificado por ESTR.02.03 (Representação e registro de dados hierárquicos) está descrito por dois subtiens: 
- O S-RES deve possibilitar a representação de dados de natureza hierárquica em árvores ou hierarquias, preservando o relacionamento dos nodos pais com os nodos filhos, de tal forma que possibilite a navegação, busca e consulta destes dados em todas as direções e 
- A representação hierárquica deverá ser respeitada minimamente para os seguintes casos: familiograma (caso o S-RES ofereça familiograma); busca e navegação na captura de códigos de terminologias e classificações hierárquicas, como CID e CIAP. Por exemplo, um item de terminologia deve apontar para seu nodo pai, e para seus nodos filhos, se não for nodo terminal, e o usuário, além de poder fazer uma pesquisa por código ou extenso da terminologia, poderá navegar na estrutura hierárquica de alguma maneira.

## Organização da CID-10
A CID-10 inclui códigos e as descrições correspondentes para doenças organizadas em capítulos. Cada capítulo reúne vários grupos. Cada grupo está organizado em categorias e, cada categoria, por sua vez, está organizada em sub-categorias, conforme ilustrado abaixo:
<pre>
CID-10
|
-- Capítulo 
   |
   -- Grupo 1
      |
      -- Categoria
         |
         -- Sub-categoria
</pre>

## Referências
- Manual de Certificação da SBIS ([aqui](http://sbis.org.br/certificacao-sbis)).
- Consulta e navegação podem fazer uso de [SlickGrid](http://mleibman.github.io/SlickGrid/examples/example5-collapsing.html) possivelmente.
