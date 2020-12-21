package br.com.emprestimo

class Emprestimo {
    private val nome: String? = null
    private val amortizacoes = 0
    private val valor: BigDecimal? = null
    private val situacao: Situacao? = null
    private val tipoEmprestimo: TipoEmprestimo? = null
    private val amortizacoes: List<Amortizacao>? = null
    private val liberacoes: List<Liberacao>? = null
    private val dataCriacao: LocalDate? = null
    private val dataConcessao: LocalDate? = null
    private val dataUltimaAmortizacao: LocalDate? = null

    private constructor() {}
    private constructor(
        nome: String,
        amortizacoes: Int,
        valor: BigDecimal,
        situacao: Situacao,
        tipoEmprestimo: TipoEmprestimo
    ) {
    }
}