package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import io.micronaut.context.annotation.Primary
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Singleton

@Primary
@Singleton
class IOF(private val operacao: Operacao): Imposto {

    override fun calcular(): BigDecimal {
       var total: BigDecimal = BigDecimal.ZERO;
        println("Passou aqui");
        println(AliquotaIOF.ADICIONAL.value)
        var iofAdicional: BigDecimal = iofAdicional();
        println("IOF adicional > R$: "+ iofAdicional)
        total = total.add(iofAdicional).setScale(2, RoundingMode.HALF_DOWN);
        var iofDiario: BigDecimal = iofDiario();
        println("IOF corrente > R$: "+ iofDiario)
        total = total.add(iofDiario).setScale(2, RoundingMode.HALF_DOWN);
        println(total);
        return total;
    }

    fun iofAdicional(): BigDecimal {
        return operacao.getValor()
                .multiply(AliquotaIOF.ADICIONAL.value).setScale(2, RoundingMode.HALF_DOWN);
    }

    fun iofDiario(): BigDecimal {
        return operacao.getValor()
                .multiply(AliquotaIOF.POR_DIA_CORRIDO.value).setScale(2, RoundingMode.HALF_DOWN);
    }

}