# CID-10

A CID-10 (versão 2008) é contemplada em duas versões: (a) [Javascript](js) para uso de todos os dados no cliente (sem conexão com backend) e (b) versão [Java](java/preprocessor) para ser requisitada via cliente. 

A primeira é uma experimentação desejável, pois se os resultados mostrarem-se razoáveis, não há necessidade de tráfego em rede para a localização desejada. A segunda é uma alternativa quando o primeiro caso não for viável.

# Copyright
O conteúdo da CID-10 empregada pelo presente projeto foi obtida [aqui](http://www.datasus.gov.br/cid10/V2008/cid10.htm). Os devidos créditos (copyright) por esse trabalho são fornecidos [aqui](http://www.datasus.gov.br/cid10/V2008/copyright.htm).

# Portais relevantes
- [Busca](http://www.icd10codesearch.com/). 

### Contexto
A Classificação Internacional de Doenças (CID) empregada pelo Brasil é a CID-10. Profissionais de saúde, em geral, empregam os códigos presentes nessa classificação para se referirem a doenças de forma não ambígua. Por exemplo, usam o código A90 para se referir à "dengue". 

### Objetivo
Desenvolver software para busca (localização) do código de uma doença da CID-10.

### Requisitos
- A busca pode ser feita apenas por parte do código, parte da descrição ou de ambos. Por exemplo, a busca por "dengue" deve trazer todas as entradas da CID-10 que contêm "dengue" como parte da descrição. Nesse caso, as entradas cujos códigos são A90 e A91. Por outro lado, se a entrada inclui "90" e "dengue", então apenas a entrada de código A90 é a resposta correta para a consulta. Observe que não é necessário fornecer nem o código nem a descrição completa.
- O módulo **cid10.jar** deve incluir o código que implementa a busca e os dados propriamente ditos sobre os quais a busca é realizada. Ou seja, os arquivos que contêm a CID10 deverão estar embutidos nesse arquivo jar.
- O módulo **cid10.jar** deve incluir a classe CID10 que deve conter os métodos públicos **load** e **unload**. O primeiro carrega e mantém em RAM os códigos e eventuais estruturas para a busca eficiente e o segundo remove da memória RAM qualquer índice e/ou dados para busca eficiente (cache). O método relevante dessa classe é o método **search**, que recebe como argumento um vetor de sequências de caracteres (**String[]**). Esse método retorna **null** caso a entrada fornecida não identifique algum código correspondente e, caso contrário, um vetor de sequências de caracteres, contendo tantas entradas quanto os códigos localizados. A resposta sempre é o código seguido de um espaço em branco seguido da descrição. Por exemplo, um retorno possível é **A90 Dengue (dengue clássico)**, ou seja, o código A90 seguido de espaço que é seguido da descrição correspondente ao código A90.

### Links
- Download da [CID-10](http://www.datasus.gov.br/cid10/V2008/cid10.htm)
