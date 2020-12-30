package br.com.proposta.restricoes

import java.math.BigDecimal

enum class LimitesConcessao(val value: BigDecimal) {
    MAXIMO(BigDecimal.valueOf(21000.00)),
    MINIMO(BigDecimal.valueOf(100.00));
}