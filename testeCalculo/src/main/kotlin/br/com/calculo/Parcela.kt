package br.com.calculo

import br.com.proposta.restricoes.PreCondicoes
import java.math.BigDecimal
import java.math.RoundingMode

class Parcela {
    val arredondamento: RoundingMode = RoundingMode.HALF_DOWN

    fun valorPadrao(montanteInicial: BigDecimal, numeroMeses: Int): BigDecimal {

        PreCondicoes().verificarAderenciaAoIntervaloPermitido(montanteInicial)
        PreCondicoes().verificarIntervaloMeses(numeroMeses)

        return montanteInicial
            .divide(numeroMeses.toBigDecimal(), arredondamento)
            .setScale(3, arredondamento);
    }
}