package br.com.calculo

import br.com.proposta.Proposta
import java.time.LocalDate

class DatasCobranca(private val proposta: Proposta) {

    val dataInicio = proposta.getDataCriacao().toLocalDate();
    val dataFinal =  proposta.getDataCriacao().plusMonths(proposta.getMeses().toLong()).toLocalDate();

    fun primeiroDia(diaAtual: LocalDate): Boolean {
        return diaAtual.equals(dataInicio);
    }

    fun diaVencimento(diaAtual: LocalDate): Boolean {
        //verificar a questão de menos de 30 dias. por exemplo FEV ou meses com menos de 31 dias.
        return Amortizacao().diaVencimento(diaAtual, dataInicio) && !primeiroDia(diaAtual)
    }

    fun ultimoDiaPrazo(diaAtual: LocalDate): Boolean {
        return diaAtual == dataFinal
    }
}