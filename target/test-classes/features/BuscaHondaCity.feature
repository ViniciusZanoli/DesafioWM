#language: pt

Funcionalidade: Buscar veiculo por marca modelo e versao

  Esquema do Cenario: Realiza busca de um honda city
    Dado que clique em comprar
    E depois em busca avançada
    E entao escolher a marca <MARCA>
    E o modelo honda <MODELO>
    E a versao
    Entao deve ser validado a mensagem Honda "Honda City 1.5 Dx 16v Flex 4p Automático Novos e Usados"

    Exemplos:
    |MARCA    |MODELO  |
    |"honda"  |"city"  |