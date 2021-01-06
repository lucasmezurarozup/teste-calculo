package br.com.proposta.restricoes

import java.math.BigDecimal

enum class LimitesConcessao(val value: BigDecimal) {
    MAXIMO(BigDecimal.valueOf(20300.00)),
    MINIMO(BigDecimal.valueOf(400.00));
}