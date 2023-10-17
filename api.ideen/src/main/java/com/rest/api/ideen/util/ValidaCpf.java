package com.rest.api.ideen.util;

public class ValidaCpf {
//    public static boolean validarCPF(String cpf) {
//        return cpf.replaceAll("[^0-9]", "").length() == 11;
//    }


    public static boolean validarCPF(String cpf) {
        // REMOVE TODOS OS CARACTERES NAO NUMERICOS
        cpf = cpf.replaceAll("[^0-9]", "");

        // VERIFICA SE O CPF TEM 11 DIGITOS
        if (cpf.length() != 11) {
            return false;
        }

        // CALCULA OS DIGITOS VERIFICADORES
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * (10 - i);
        }
        int primeiroDigito = 11 - (soma % 11);
        if (primeiroDigito >= 10) {
            primeiroDigito = 0;
        }

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += (cpf.charAt(i) - '0') * (11 - i);
        }
        int segundoDigito = 11 - (soma % 11);
        if (segundoDigito >= 10) {
            segundoDigito = 0;
        }

        // VERIFICA SE OS DIGITOS CALCULADOS COINCIDEM COM OS DIGITOS ORIGINAIS
        return (cpf.charAt(9) - '0' == primeiroDigito) && (cpf.charAt(10) - '0' == segundoDigito);
    }


}

