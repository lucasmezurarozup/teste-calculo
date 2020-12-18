package br.com.calculo

import io.micronaut.context.annotation.Primary
import io.micronaut.core.annotation.Introspected
import javax.inject.Inject
import javax.inject.Singleton

@Primary
@Singleton
class Calculo(calculoRequest: CalculoRequest, imposto: Imposto) {

    private lateinit var imposto: Imposto;
    private lateinit var calculoRequest: CalculoRequest;


    fun executa() {
        if (calculoRequest != null) {
            println("Passou pelo Calculo.executa(_)")
            this.imposto?.calcular()
        };
    }

    fun getImposto(): Imposto {
        return this.getImposto()
    }
}