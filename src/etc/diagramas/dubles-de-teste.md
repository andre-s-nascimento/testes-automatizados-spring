# Dublês de Teste

## Dummy

Apenas para compilar, não é invocado - comum em TDD

## Fake

Implementação funcional, mas não é a usada em produção (ex. banco de dados em memória)

## Stub

Responde de acordo com definições preestabelecidas: quando chamar com determinado parâmetros -> retorna valor fixo (verificação de estado).

## Spy

Gravam informações sobre como foram chamados 'stub melhorado' (verificação de estado e comportamento)

## Mock

Interação exata com os objetos que os usam, verificar se o fluxo desejado foi invocado (verificação de comportamento) o estado não muda.
