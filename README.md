# Barramento SUS (bsus)
Façade para acesso ao Barramento SUS (bsus) 

[<img src="https://api.travis-ci.org/kyriosdata/bsus.svg?branch=master">](https://travis-ci.org/kyriosdata/bsus)
[![Dependency Status](https://www.versioneye.com/user/projects/5818f81589f0a91d55eb921c/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/5818f81589f0a91d55eb921c)
[![Sonarqube](https://sonarqube.com/api/badges/gate?key=com.github.kyriosdata.bsus:bsus)](https://sonarqube.com/dashboard/index?id=com.github.kyriosdata.bsus%3Absus)
[![Javadocs](http://javadoc.io/badge/com.github.kyriosdata.bsus/bsus.svg)](http://javadoc.io/doc/com.github.kyriosdata.bsus/bsus)

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
O Ministério da Saúde (MS) é manteneder de informações sobre a saúde no Brasil por meio do Datasus. Algumas dessas informações são enviadas/recebidas de um Sistema de Informação em Saúde (SIS) por meio do "barramento SUS" conforme ilustra a figura abaixo.

![bsus-context](https://cloud.githubusercontent.com/assets/1735792/24827988/9b06f354-1c2a-11e7-98dc-38a80be4de77.png)

O BSUS é uma proposta de biblioteca de software em Java para realizar a comunicação entre um SIS e o barramento SUS. 


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

