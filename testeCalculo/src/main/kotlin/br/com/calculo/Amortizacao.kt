package br.com.calculo

import br.com.proposta.restricoes.PreCondicoes
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class Amortizacao {

    fun amortizar(montanteAPagar: BigDecimal, parcela: BigDecimal): BigDecimal {

        //PreCondicoes().verificarAderenciaAoIntervaloPermitido(montanteAPagar)

        return montanteAPagar.subtract(parcela)
            .setScale(4, RoundingMode.HALF_DOWN);
    }

    fun diaVencimento(dataAtual: LocalDate, dataInicio: LocalDate): Boolean {

        if(mesesCom31Dias(dataAtual)) {
            return dataInicio.dayOfMonth == dataAtual.dayOfMonth
        }else {
           val dataFinalMes = dataAtual.with(TemporalAdjusters.lastDayOfMonth())
            if (dataAtual == dataFinalMes) {
                return true
            }
            return false
        }
    }

    private fun mesesCom31Dias(dataAtual: LocalDate): Boolean {
        for (mes in MesesMenosDe31Dias.values()) {
            if(dataAtual.monthValue == mes.value) {
                return false
            }
        }

        return true
    }
}