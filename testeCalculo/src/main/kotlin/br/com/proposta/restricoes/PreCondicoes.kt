package br.com.proposta.restricoes

import java.lang.IllegalStateException
import java.math.BigDecimal

class PreCondicoes {

    fun verificarAderenciaAoIntervaloPermitido(montante: BigDecimal) {
        if(montante > 0.toBigDecimal() && montante <= LimitesConcessao.MAXIMO.value) else {
            throw IllegalStateException("Valor invalido passado para montagem de parcela.")
        }
    }

    fun verificarIntervaloMeses(numeroMeses: Int) {
        if (numeroMeses <= LimitePrazo.MAXIMO.value || numeroMeses >= LimitePrazo.MINIMO.value) {
            throw IllegalStateException("Numero de parcelas fora do invervalo permitido.")
        }
    }
}