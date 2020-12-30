package br.com.proposta

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class Proposta {

    private var nome: String = ""
    private var meses: Int = 0
    private var valor: BigDecimal = BigDecimal.ZERO
    private var situacao: Situacao? = null
    private var tipoEmprestimo: TipoEmprestimo? = null
    private val amortizacoes: List<Amortizacao>? = null
    private val dataCriacao: LocalDateTime = LocalDateTime.of(LocalDate.of(2020, 12, 31), LocalTime.MIDNIGHT)
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