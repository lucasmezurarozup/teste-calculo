package br.com.calculo

import br.com.proposta.Proposta

class Simulacao(
    private val proposta: Proposta,
    private val iof: IOF) {

    fun calcula(): SimulacaoResponse {

        if (proposta.getMeses() <= 0 || proposta.getValor().toLong() <= 0) {
            throw IllegalStateException("O emprestimo com informações invalidas!")
        }

        if(prazoMaiorQue365Dias()) {
            return SimularComAliquotaMaxima(proposta, iof).calcular()
        }else {
            return SimularComAliquotaNormal(proposta, iof).calcular()
        }
    }

    private fun prazoMaiorQue365Dias(): Boolean {
        return proposta.getMeses() > 12;
    }

};