package br.com.calculo

import io.micronaut.context.annotation.Context
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
interface Imposto {
    fun calcular(): BigDecimal;
}