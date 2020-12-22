package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class TesteCalculoIOF {

    @Test
    fun `logica para calculo do IOF adicional`(){
        val valorEmprestimo: Double = 10000.00
        val operacao: Operacao = Operacao(BigDecimal(valorEmprestimo), 6)
       val iof: IOF = IOF(operacao)

        var IOFAdicional: BigDecimal = BigDecimal(valorEmprestimo)
           .multiply(AliquotaIOF.ADICIONAL.value)
           .setScale(2, RoundingMode.HALF_DOWN)

        Assertions.assertEquals(IOFAdicional, iof.iofAdicional())
    }

    @Test
    fun `logica para calculo do IOF corrente (diario em dias correntes)`(){
        val valorEmprestimo: Double = 10000.00
        val operacao: Operacao = Operacao(BigDecimal(valorEmprestimo), 6)
        val iof: IOF = IOF(operacao)

        val arrendondamento: RoundingMode = RoundingMode.HALF_DOWN;

        var IOFDiario: BigDecimal = BigDecimal(valorEmprestimo)
            .multiply(AliquotaIOF.POR_DIA_CORRIDO.value)
            .setScale(2, arrendondamento)

        Assertions.assertEquals(IOFDiario, iof.iofDiario())
    }


}