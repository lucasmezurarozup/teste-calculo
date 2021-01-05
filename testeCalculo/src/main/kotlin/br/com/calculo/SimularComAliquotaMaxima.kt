package br.com.calculo

import br.com.proposta.Proposta

class SimularComAliquotaMaxima(val proposta: Proposta, val iof: IOF): Calculo {

    override fun calcular(): SimulacaoResponse {
        val impostoIof = iof.iofAliquotaMaxima(proposta.getValor())
        return SimulacaoResponse(impostoIof)
    }
}