package br.com.calculo.aliquotas

import java.math.BigDecimal
import java.math.RoundingMode

enum class AliquotaIOF(val value: BigDecimal) {
    ADICIONAL(BigDecimal(0.0038).setScale(6, RoundingMode.HALF_DOWN)),
    POR_DIA_CORRIDO(BigDecimal(0.000041).setScale(6, RoundingMode.HALF_DOWN));

}

