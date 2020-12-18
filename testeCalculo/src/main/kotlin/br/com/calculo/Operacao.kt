package br.com.calculo

import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
data class Operacao(private val valor: BigDecimal,private val parcelas: Int) {

    fun getValor(): BigDecimal {
        return this.valor;
    }

    fun getParcelas(): Int {
        return this.parcelas;
    }
}
