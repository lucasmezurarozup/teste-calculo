package br.com.calculo

import br.com.proposta.Proposta
import br.com.proposta.Situacao
import br.com.proposta.TipoEmprestimo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException
import java.math.BigDecimal
import java.math.RoundingMode

class TesteAmortizacao {

    @Test
    fun `teste de amortizacao`() {
        val proposta: Proposta = Proposta(
            "lucas",
            10,
            BigDecimal(10000.00),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )

        val amortizacao: BigDecimal = Amortizacao()
            .amortizar(
                proposta.getValor(),
                Parcela().valorPadrao(proposta.getValor(), proposta.getMeses()))

        Assertions.assertEquals(BigDecimal.valueOf(9000.00).setScale(2, RoundingMode.HALF_DOWN), amortizacao)
    }

    @Test
    fun `teste de amortizacao com numero de meses maior que o permitido`() {
        val proposta: Proposta = Proposta(
            "lucas",
            17,
            BigDecimal(10000.00),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )


        assertThrows<IllegalStateException> {

            val amortizacao: BigDecimal = Amortizacao()
                .amortizar(
                    proposta.getValor(),
                    Parcela().valorPadrao(proposta.getValor(), proposta.getMeses()))
        }
    }
}