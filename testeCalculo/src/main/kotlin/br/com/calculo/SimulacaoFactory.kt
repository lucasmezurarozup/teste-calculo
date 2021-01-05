package br.com.calculo

import br.com.proposta.Proposta

class SimulacaoFactory(private val proposta: Proposta) {

    fun getInstance(): Calculo {
        if(prazoMaiorQue365Dias()) {
            return SimularComAliquotaMaxima(proposta, IOF())
        }else {
            return SimularComAliquotaNormal(proposta, IOF())
        }
    }

    private fun prazoMaiorQue365Dias(): Boolean {
        return proposta.getMeses() > 12;
    }
}