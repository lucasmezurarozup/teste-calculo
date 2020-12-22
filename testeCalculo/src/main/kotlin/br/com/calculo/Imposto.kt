package br.com.calculo

import io.micronaut.context.annotation.Context
import io.micronaut.context.annotation.Primary
import java.math.BigDecimal
import javax.inject.Singleton

@Primary
@Singleton
interface Imposto {
    fun calcular(): BigDecimal;
}