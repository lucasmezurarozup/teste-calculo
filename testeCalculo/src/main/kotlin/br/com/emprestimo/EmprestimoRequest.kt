package br.com.emprestimo

import io.micronaut.context.annotation.Primary
import java.math.BigDecimal
import javax.inject.Singleton

@Primary
@Singleton
class EmprestimoRequest {

    private var nome: String = ""
    private var meses = 0
    private var valor: BigDecimal = BigDecimal.ZERO
    private var tipoEmprestimo: TipoEmprestimo = TipoEmprestimo.MICRO_CREDITO;

    fun getNome(): String {
        return this.nome;
    }

    fun getMeses(): Int {
        return meses;
    }

    fun getValor(): BigDecimal {
        return valor;
    }

    fun getTipoEmprestimo(): TipoEmprestimo {
        return this.tipoEmprestimo;
    }
}