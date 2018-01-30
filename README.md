# app2app
Utilização Básica A integração com o aplicativo do PagSeguro é realizado através de uma chamada seu aplicativo Android para o aplicativo PagSeguro, onde os parâmetros de cobrança são enviados pela classe Intent do Android. O aplicativo do PagSeguro por sua vez é aberto e o fluxo normal de venda é iniciado.

Ao final do processamento da transação o aplicativo do PagSeguro envia os resultados ao aplicativo origem, para que este possa informar ao seu usuário sobre o resultado da operação.

A utilização dessa funcionalidade consiste na criação do código para o envio da solicitação de transação e tratamento da resposta do aplicativo do PagSeguro.
