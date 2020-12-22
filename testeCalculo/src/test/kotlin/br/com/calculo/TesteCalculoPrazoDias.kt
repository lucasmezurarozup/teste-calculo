package br.com.calculo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TesteCalculoPrazoDias {

    @Test
    fun `teste prazo dias corridos em 3 meses a partir de 18-12-2020`() {
        val operacao: Operacao = Operacao(BigDecimal(10000.00), 3)
        val calculadoraPrazo: CalculadoraPrazo = CalculadoraPrazo(operacao)

        val dataConcessao: LocalDateTime = LocalDateTime.of(
            LocalDate.of(2020, 12,18),
            LocalTime.MIDNIGHT
        );

        Assertions.assertEquals(90, calculadoraPrazo.totalDiasCorridos(dataConcessao))
    }
}