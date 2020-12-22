package br.com.calculo

import io.micronaut.context.annotation.Primary
import java.math.BigDecimal
import javax.inject.Singleton

@Primary
@Singleton
class SimulacaoResponse {

    private var montanteAPagar: BigDecimal;

    constructor(montanteAPagar: BigDecimal) {
        this.montanteAPagar = montanteAPagar;
    }

    fun getMontanteAPagar(): BigDecimal {
        return montanteAPagar
    }
}