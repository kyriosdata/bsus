# Barramento SUS (bsus)
Façade para acesso ao Barramento SUS (bsus) 

<br />
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/">
<img alt="Creative Commons License" style="border-width:0"
 src="https://i.creativecommons.org/l/by/4.0/88x31.png" /></a>
 <br />This work is licensed under a <a rel="license" 
 href="http://creativecommons.org/licenses/by/4.0/">Creative Commons 
 Attribution 4.0 International License</a>. 
 <br />Fábio Nogueira de Lucena - Fábrica de Software - 
 Instituto de Informática (UFG).

## Qual a função do BSUS?
O Ministério da Saúde (MS) é mantenedor de informações em saúde no Brasil. Algumas dessas informações são enviadas ao MS ou recebidas do MS por um Sistema de Informação em Saúde (SIS). Essa interação ocorre por meio do "barramento SUS" conforme ilustra a figura abaixo.

![bsus-context](https://cloud.githubusercontent.com/assets/1735792/24827988/9b06f354-1c2a-11e7-98dc-38a80be4de77.png)

O BSUS é uma proposta de biblioteca de software em Java para realizar a comunicação entre um SIS e o barramento SUS. O objetivo é _isolar um SIS do que é necessário para realizar a interação com o barramento SUS_ de tal forma que a biblioteca se ocupe de detalhes do protocolo de comunicação, de alteraçes nesse protocolo, da localização e da autenticação para acesso ao barramento.

No momento, os serviços oferecidos pelo barramento SUS e de interesse da biblioteca são identificados pela figura abaixo.

![bsus-barramento](https://cloud.githubusercontent.com/assets/1735792/24828306/74c16714-1c30-11e7-8130-44c13928fbc9.png)

O projeto (_software design_) de acesso aos serviços varia conforme as funcionalidades oferecidas por cada um deles. Abaixo, contudo, segue uma orientação.

***
## Projeto
Os elementos a serem implementados são identificados no diagrama abaixo.

![bsus-modulos](https://cloud.githubusercontent.com/assets/1735792/24828919/3af708b4-1c3e-11e7-9c2f-b99dec681e1a.png)

As unidades de implementação identificadas no diagrama acima são empacotados conforme abaixo. Observe que "Circuit Breaker" foi substituído por uma implementação específica (Hystrix), que inclui/depende de vários outros elementos.

***


## Como usar (via maven)?

Acrescente a dependencia abaixo no arquivo pom.xml:

<pre>
&lt;dependency&gt;
  &lt;groupId&gt;com.github.kyriosdata.bsus&lt;/groupId&gt;
  &lt;artifactId&gt;bsus&lt;/artifactId&gt;
  &lt;version&gt;1.0.0&lt;/version&gt;
&lt;/dependency&gt;
</pre>

# Serviços oferecidos
- CID10. Permite a localização de um determinado código dado uma descrição ou da descrição para um dado código. Duas versões devem ser produzidas: (a) Javascript para uso de todos os dados no cliente (sem conexão com backend) e (b) versão Java para ser requisitada via cliente. A primeira é uma experimentação desejável, pois se os resultados mostrarem-se razoáveis, não há necessidade de tráfego em rede para a localização desejada. A segunda é uma alternativa quando o primeiro caso não for viável (um exemplo pode ser visto [aqui](http://www.icd10codesearch.com/)). Arquivos correspondentes à CID-10 podem ser obtidos [aqui](http://www.datasus.gov.br/cid10/V2008/cid10.htm).

## Alguns valores para teste
- CNES: Centro de Saúde Campus Samambaia (7381549)
- CNES: Hispital das Clínicas da UFG (2338424)

- Como armazenar as credenciais de acesso?
- [Google](https://support.google.com/cloud/answer/6310037?hl=en)
- [Stackoverflow](http://stackoverflow.com/questions/25964435/different-ways-to-store-a-password-variable-in-a-java-web-application/25969056#25969056)

