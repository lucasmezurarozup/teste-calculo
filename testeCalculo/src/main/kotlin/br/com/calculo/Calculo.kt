package br.com.calculo

import io.micronaut.context.annotation.Primary
import io.micronaut.core.annotation.Introspected
import java.math.BigDecimal
import javax.inject.Inject
import javax.inject.Singleton

@Primary
@Singleton
class Calculo(val imposto: Imposto) {

    fun executa(): BigDecimal {

            println("Passou pelo Calculo.executa(_)")

            return this.imposto?.calcular()

    }
}