package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import br.com.proposta.restricoes.PreCondicoes
import io.micronaut.context.annotation.Primary
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Singleton

@Primary
@Singleton
class IOF() {

    private val arrendondamento: RoundingMode = RoundingMode.HALF_DOWN;


    fun iofAdicional(montante: BigDecimal): BigDecimal {
        this.verificandoValidadeMontante(montante)
        return montante
                .multiply(AliquotaIOF.ADICIONAL.value)
                .setScale(4, arrendondamento);
    }

    fun iofDiario(montante: BigDecimal): BigDecimal {
        this.verificandoValidadeMontante(montante)
        return montante
                .multiply(AliquotaIOF.POR_DIA_CORRIDO.value)
                .setScale(4, arrendondamento);
    }

    fun iofAliquotaMaxima(montante: BigDecimal): BigDecimal {
        this.verificandoValidadeMontante(montante)
        return montante
            .multiply(AliquotaIOF.ALIQUOTA_MAXIMA.value)
            .setScale(4, arrendondamento);
    }

    private fun verificandoValidadeMontante(montante: BigDecimal) {
       PreCondicoes().verificarAderenciaAoIntervaloPermitido(montante)
    }
}