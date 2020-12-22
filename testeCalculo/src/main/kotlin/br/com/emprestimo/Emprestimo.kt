package br.com.emprestimo

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class Emprestimo {

    private var nome: String = ""
    private var meses: Int = 0
    private var valor: BigDecimal = BigDecimal.ZERO
    private var situacao: Situacao? = null
    private var tipoEmprestimo: TipoEmprestimo? = null
    private val amortizacoes: List<Amortizacao>? = null
    private val liberacoes: List<Liberacao>? = null
    private val dataCriacao: LocalDateTime = LocalDateTime.now()
    private val dataConcessao: LocalDateTime? = null
    private val dataUltimaAmortizacao: LocalDateTime? = null

    private constructor() {

    }

    public constructor(
        nome: String,
        meses: Int,
        valor: BigDecimal,
        situacao: Situacao,
        tipoEmprestimo: TipoEmprestimo
    ) {
        this.nome = nome
        this.meses = meses
        this.situacao = situacao
        this.tipoEmprestimo = tipoEmprestimo
        this.valor = valor
    }

    fun getNome(): String {
        return this.nome
    }

    fun getMeses(): Int {
        return this.meses
    }

    fun getValor(): BigDecimal {
        return this.valor
    }

    fun getDataCriacao(): LocalDateTime {
        return this.dataCriacao
    }
}