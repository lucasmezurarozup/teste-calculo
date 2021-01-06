package br.com.calculo

import br.com.proposta.Proposta

class SimulacaoFactory() {

    fun tipo(proposta: Proposta): Calculo {

        val prazoMaiorQue365Dias = proposta.getMeses() > 12

        if(prazoMaiorQue365Dias) {
            return SimularComAliquotaMaxima(proposta, IOF())
        }else {
            return SimularComAliquotaNormal(proposta)
        }
    }
}