package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import br.com.proposta.Proposta
import io.micronaut.context.annotation.Primary
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Singleton

@Primary
@Singleton
class IOF(private val proposta: Proposta): Imposto {

    private val arrendondamento: RoundingMode = RoundingMode.HALF_DOWN;

    override fun calcular(): BigDecimal {
       var total: BigDecimal = BigDecimal.ZERO;

        total = total.add(iofAdicional())
            .setScale(2, arrendondamento);
        total = total.add(iofDiario())
            .setScale(2, arrendondamento);

        return total;
    }

    fun iofAdicional(): BigDecimal {
        return proposta.getValor()
                .multiply(AliquotaIOF.ADICIONAL.value)
                .setScale(2, arrendondamento);
    }

    fun iofDiario(): BigDecimal {
        return proposta.getValor()
                .multiply(AliquotaIOF.POR_DIA_CORRIDO.value)
                .setScale(2, arrendondamento);
    }
}