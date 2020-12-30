package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException
import java.math.BigDecimal
import java.math.RoundingMode

class TesteCalculoIOF {

    val arrendondamento: RoundingMode = RoundingMode.HALF_DOWN;

    @Test
    fun `logica para calculo do IOF adicional`(){

       val iof: IOF = IOF()

        var IOFAdicional: BigDecimal = BigDecimal.valueOf(10000.00)
           .multiply(AliquotaIOF.ADICIONAL.value)
           .setScale(4, arrendondamento)

        Assertions.assertEquals(IOFAdicional, iof.iofAdicional(BigDecimal(10000.00)))
    }

    @Test
    fun `logica para calculo do IOF corrente (diario em dias correntes)`(){
        val iof: IOF = IOF()

        var IOFDiario: BigDecimal = BigDecimal.valueOf(10000.00)
            .multiply(AliquotaIOF.POR_DIA_CORRIDO.value)
            .setScale(4, arrendondamento)

        Assertions.assertEquals(IOFDiario, iof.iofDiario(BigDecimal(10000.00)))
    }

    @Test
    fun `verificando a passagem de valor negativos para a função de calculo`() {

        val iof: IOF = IOF()

        var IOFDiario: BigDecimal = BigDecimal(-1)
            .multiply(AliquotaIOF.POR_DIA_CORRIDO.value)
            .setScale(4, arrendondamento)
        
        assertThrows<IllegalStateException> {
            iof.iofDiario(BigDecimal.valueOf(-1))
        }

    }

    @Test
    fun `verificando a passagem de valor acima do permitido`() {

        val iof: IOF = IOF()

        var IOFDiario: BigDecimal = BigDecimal(-1)
            .multiply(AliquotaIOF.POR_DIA_CORRIDO.value)
            .setScale(4, arrendondamento)

        assertThrows<IllegalStateException> {
            iof.iofDiario(BigDecimal.valueOf(100000.00))
        }

    }

}