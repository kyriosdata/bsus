## CID-10
A Classificação Internacional de Doenças (CID) empregada pelo Brasil é a CID-10. Profissionais de saúde, em geral, empregam os códigos presentes nessa classificação para se referirem a doenças de forma não ambígua, por exemplo, usam o código **A90** em vez de "dengue". 

O presente projeto implementa dois módulos: (a) um para acesso à CID-10 por meio de uma interface gráfica via browser (apenas com o propósito de ilustração) e (b) um para fornecer um serviço de busca aos códigos da CID-10. Uma [Web App](http://www.icd10codesearch.com/) similar pode ser consultada para ilustrar a intenção do presente projeto.

## Versão e copyright da CID-10
O conteúdo da CID-10 versão 2008 empregada pelo presente projeto foi obtida [aqui](http://www.datasus.gov.br/cid10/V2008/cid10.htm). Os créditos (copyright) do conteúdo da CID-10 são fornecidos [aqui](http://www.datasus.gov.br/cid10/V2008/copyright.htm).

## Requisitos
- A busca pode ser feita apenas por parte do código, parte da descrição ou de ambos. 
- A busca pela descrição por "dengue" deve trazer todas as entradas da CID-10 que contêm "dengue" como parte da descrição. Nesse caso, as entradas cujos códigos são A90 e A91. Observe que se a consulta for realizada apenas por "engue" (sem o d), o resultado também deve incluir aqueles oferecidos para "dengue".
- A busca por "90" pelo código e por "dengue" pela descrição, apenas produz a entrada de código A90.
- O módulo **cid10-2008.jar** deve incluir o código que implementa a busca e os dados propriamente ditos sobre os quais a busca é realizada. Ou seja, todas as informações necessárias para oferecer as funcionalidades acima devem estar contidas nesse único arquivo **cid10-2008.jar**.
- O módulo **cid10-2008.jar** deve incluir a classe CID10 que deve conter os métodos públicos **load** e **unload**. O primeiro carrega e oferece oportunidade para iniciar o componente, enquanto o segundo indica que o componente não mais será utilizado. 
- A principal operação dessa interface é **search**, que recebe como argumento um vetor de sequências de caracteres (**String[]**). Esse método retorna **null** caso a entrada fornecida não identifique código algum correspondente e, caso contrário, um vetor de sequências de caracteres, contendo tantas entradas quanto os códigos localizados. A resposta sempre é o código seguido de um espaço em branco seguido da descrição. Por exemplo, um retorno possível é **A90 Dengue (dengue clássico)**, ou seja, o código A90 seguido de espaço que é seguido da descrição correspondente ao código A90.

