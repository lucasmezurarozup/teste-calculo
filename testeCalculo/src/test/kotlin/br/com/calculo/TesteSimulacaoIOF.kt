package br.com.calculo

import br.com.proposta.Proposta
import br.com.proposta.Situacao
import br.com.proposta.TipoEmprestimo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode


class TesteSimulacaoIOF {


    @Test
    fun mais() {
        Assertions.assertEquals(20, 20)
    }

    @Test
    fun `testando insercao de simulacao acima de 365 dias`() {

        val proposta: Proposta = Proposta(
            "lucas",
            15,
            BigDecimal.valueOf(1000.00),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )
        val simulacao: Simulacao = Simulacao(
            proposta,
            IOF()
        )

        Assertions.assertEquals(
            BigDecimal.valueOf(15.00).setScale(4, RoundingMode.HALF_DOWN),
            simulacao.calcula().getMontanteAPagar()
        )
    }

    @Test
    fun `testando simulacao com 6 meses`() {

        val proposta: Proposta = Proposta(
            "lucas",
            15,
            BigDecimal.valueOf(1000.00),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )
        val simulacao: Simulacao = Simulacao(
            proposta,
            IOF()
        )

        val iofAdicional = IOF().iofAdicional(proposta.getValor())

        Assertions.assertEquals(
            BigDecimal.valueOf(15.00).setScale(4, RoundingMode.HALF_DOWN),
            simulacao.calcula().getMontanteAPagar()
        )
    }
}