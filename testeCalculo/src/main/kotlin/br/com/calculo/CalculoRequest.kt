package br.com.calculo

import io.micronaut.context.annotation.Primary
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.inject.Singleton

@Primary
@Singleton
class CalculoRequest {

    private var amortizacoes: Int = 1;
    private var valor: BigDecimal = BigDecimal.ZERO;

    fun getAmortizacoes(): Int {
        return this.amortizacoes;
    }

    fun getValor(): BigDecimal {
        return this.valor;
    }
}
