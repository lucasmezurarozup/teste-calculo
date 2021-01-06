package br.com.calculo

import br.com.proposta.Proposta
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

class SimularComAliquotaNormal(private val proposta: Proposta): Calculo {

    private val arredondamento: RoundingMode = RoundingMode.HALF_DOWN
    private val dataFinal = devolveTotalMeses()

    override fun calcular(): SimulacaoResponse {

        var diaAtual = proposta.getDataCriacao().toLocalDate()
        var iofTotal = BigDecimal.ZERO

        var montanteAPagar = devolveValorConcedido()

        val cobranca: Cobranca = Cobranca(proposta)

        while (dataFinal >= diaAtual) {
            iofTotal = cobranca.aplicaIof(montanteAPagar, diaAtual)
            montanteAPagar = cobranca.vencimentoMensal(montanteAPagar, diaAtual)

            diaAtual = diaAtual.plusDays(1)
        }

        return SimulacaoResponse(iofTotal.setScale(2, arredondamento))
    }

    private fun devolveTotalMeses(): LocalDate {
        return proposta.getDataCriacao()
            .plusMonths(proposta.getMeses().toLong()).toLocalDate()
    }

    private fun devolveValorConcedido() = proposta.getValor().setScale(2, arredondamento)
}