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
 
## Qual o objetivo do BSUS?
O Ministério da Saúde (MS) é um mantenedor e fornecedor de informações em saúde no Brasil. Tais informações são enviadas/recebidas por um Sistema de Informação em Saúde (SIS). Essa interação entre um SIS e o "barramento SUS" pode ser ilustrada na figura abaixo.

![bsus-context](https://cloud.githubusercontent.com/assets/1735792/24827988/9b06f354-1c2a-11e7-98dc-38a80be4de77.png)

O BSUS é uma proposta de biblioteca de software em Java para realizar a comunicação entre um SIS e o "barramento SUS". O objetivo é _isolar o código de um SIS do código que realiza a interação com o barramento SUS_. Cabe à biblioteca os detalhes do protocolo de comunicação (inclui conversões entre formatos de dados), de alteraçes nesse protocolo, da localização e da autenticação para acesso ao barramento.

Os serviços oferecidos pelo barramento SUS (SOA-SUS) a serem contemplados pela biblioteca são identificados na figura abaixo, juntamente com a CID-10.

![bsus-barramento](https://cloud.githubusercontent.com/assets/1735792/24828306/74c16714-1c30-11e7-8130-44c13928fbc9.png)

### Objetivos de qualidade do BSUS
- O BSUS deve ser acompanhado de testes de unidade com 100% de cobertura.
- Testes de integração deverão demonstrar que o BSUS opera conforme a especificação estabelecida para interação com o barramento SUS.
- Os serviços do barramento SUS deverão ser localizados de forma transparente (transparência de localização). 
- O BSUS não deve comprometer a execução de outras operações do código cliente caso os serviços do barramento não estejam disponíveis.
- O armazenamento de informação sigilosa como credencial de acesso deve fazer uso de estratégia compatível com exigências de segurança esperadas de um SIS.

## Projeto do software do BSUS

### Módulos

Os elementos a serem implementados são identificados no diagrama abaixo. Há dois grupos de módulos: (a) interface e (b) implementação. O cliente usa diretamente apenas a interface e independe da implementação desse módulo. Em consequência, a implementação pode variar, sem necessariamente exigir mudança no cliente. 

Observe que para acesso a um serviço remoto é necessário a localização do serviço, bem como credencial de acesso, ambos registrados "fora" do código fonte, conforme ilustrado. 

![bsus-modulos](https://cloud.githubusercontent.com/assets/1735792/24927295/f55eae9e-1ed4-11e7-94e2-ed5c04235aa0.png)

### Instalação

As unidades de implementação identificadas no diagrama acima são empacotadas conforme abaixo: (a) um arquivo jar e (b) um arquivo zip. Ou seja, a implementação da biblioteca exige o acesso a apenas dois arquivos. O primeiro deles em tempo de compilação, o segundo, apenas durante a execução. O primeiro reúne a interface de acesso ao barramento e a fábrica que cria instâncias dessa interface. O segundo reúne um arquivo jar e a distribuição do Hystrix.  

Observe que o arquivo bsus (jar) inclui a implementação da biblioteca (BSUS), a interface Connection e a implementação dessa interface, que faz uso do Hystrix. 

![bsus-instalacao](https://cloud.githubusercontent.com/assets/1735792/24927204/98b464ea-1ed4-11e7-9ac2-8d7d2f18f638.png)

### Componentes
Conforme o diagrama acima, para fazer uso do BSUS é necessário acesso módulo *bsus-interface.jar* e também ao módulo *bsus-1.0.0.zip*. O primeiro é independente do segundo. O segundo inclui a implementação necessária da interface juntamente com todas as dependências (arquivos jar) empregados pela implementação. 

Em tempo de execução, temos pelos menos dois processos e várias instâncias, conforme ilustrado abaixo. Um dos processos reúne todos os objetos ilustrados abaixo, exceto o "Barramento SUS", componente em execução em servidor remoto. 

![bsus-componentes](https://cloud.githubusercontent.com/assets/1735792/25067663/d855ad12-2220-11e7-9817-4b1f832fb96b.png)


### Configuração, segurança, tolerância a falhas e latência

- Vault (https://www.vaultproject.io/). Vault é uma ferramenta que trata do armazenamento de segredos, tendo em vista que o uso de um arquivo de configuração não é uma estratégia recomendada atualmente. 
- Consul (https://www.consul.io/). Consul é útil para assegurar a transparência de localização. 
- Hystrix (https://github.com/Netflix/Hystrix) é uma implementação de "Circuit breaker" e, portanto, trata de tolerância a falhas e de latência, em particular, na presença de falhas. 
- Circuit breaker ([definição](https://martinfowler.com/bliki/CircuitBreaker.html)). Padrão de projeto útil a cenários onde há necessidade de requisição de serviços remotos. 

Acerca do armazenamento de informações sigilosas (segredos), pode-se obter informação adicional [aqui](https://spring.io/blog/2016/06/24/managing-secrets-with-vault). Consulte ainda [Google](https://support.google.com/cloud/answer/6310037?hl=en) e 
[Stackoverflow](http://stackoverflow.com/questions/25964435/different-ways-to-store-a-password-variable-in-a-java-web-application/25969056#25969056) para mais informações. 



