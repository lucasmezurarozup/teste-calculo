package br.com.calculo

import br.com.proposta.restricoes.PreCondicoes
import java.math.BigDecimal
import java.math.RoundingMode

class Parcela {
    val arredondamento: RoundingMode = RoundingMode.HALF_UP

    fun valorPadrao(montanteInicial: BigDecimal, numeroMeses: Int): BigDecimal {

        PreCondicoes().verificarAderenciaAoIntervaloPermitido(montanteInicial)
        PreCondicoes().verificarIntervaloMeses(numeroMeses)

        val parcela: BigDecimal = montanteInicial
            .divide(numeroMeses.toBigDecimal(), arredondamento)
            .setScale(3, arredondamento)

       val parcela2 =  montanteInicial.toDouble().div(numeroMeses.toDouble())


        return parcela2.toBigDecimal()
    }
}