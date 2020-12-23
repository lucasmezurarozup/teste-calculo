package br.com.proposta

import java.math.BigDecimal
import java.time.LocalDateTime

class Amortizacao {

    private var valor: BigDecimal? = null;
    private val dataAmortizacao: LocalDateTime = LocalDateTime.now();
    private var emprestimoId: String? = null;
    private var atrasado: Boolean? = null;

    private constructor() {

    }

    public constructor(valor: BigDecimal, emprestimoId: String, atrasado: Boolean) {
        this.valor = valor;
        this.emprestimoId = emprestimoId
        this.atrasado = atrasado
    }

}
