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


class TesteCalculoParcela {

    @Test
    fun `testando insercao de parcela`() {
        val proposta: Proposta = Proposta(
            "lucas",
            10,
            BigDecimal(10000.00),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )

        val parcela: BigDecimal = Parcela().valorPadrao(proposta.getValor(),proposta.getMeses())

        Assertions.assertEquals(BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_DOWN), parcela.setScale(2, RoundingMode.HALF_DOWN))
    }

    @Test
    fun `inserindo valor positivo abaixo do limite mínimo de emprestimo`() {
        val proposta: Proposta = Proposta(
            "lucas",
            10,
            BigDecimal(0),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )

        assertThrows<IllegalStateException> {
            val parcela: BigDecimal = Parcela()
                .valorPadrao(proposta.getValor(),proposta.getMeses())
        }
    }

    @Test
    fun `inserindo numero de meses maior que o permitido`() {
        val proposta: Proposta = Proposta(
            "lucas",
            16,
            BigDecimal(0),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )

        assertThrows<IllegalStateException> {
            val parcela: BigDecimal = Parcela()
                .valorPadrao(proposta.getValor(),proposta.getMeses())
        }
    }

    @Test
    fun `inserindo valor negativo`() {
        val proposta: Proposta = Proposta(
            "lucas",
            10,
            BigDecimal(-19090.10),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )

        assertThrows<IllegalStateException> {
            val parcela: BigDecimal = Parcela()
                .valorPadrao(proposta.getValor(),proposta.getMeses())
        }
    }

    @Test
    fun `inserindo valor acima do limite permitido`() {
        val proposta: Proposta = Proposta(
            "lucas",
            10,
            BigDecimal(29090.1),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO
        )

        assertThrows<IllegalStateException> {
            val parcela: BigDecimal = Parcela()
                .valorPadrao(proposta.getValor(),proposta.getMeses())
        }
    }

};