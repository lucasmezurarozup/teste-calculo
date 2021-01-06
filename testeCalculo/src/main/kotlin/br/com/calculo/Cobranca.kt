package br.com.calculo

import br.com.proposta.Proposta
import java.math.BigDecimal
import java.time.LocalDate

class Cobranca(proposta: Proposta) {
    private var iofTotal: BigDecimal = BigDecimal.ZERO
    private val datasCobranca: DatasCobranca = DatasCobranca(proposta)
    private val iof: IOF = IOF()
    private val parcela = Parcela().valorPadrao(proposta.getValor(), proposta.getMeses())

    fun vencimentoMensal(montanteAPagar: BigDecimal, diaAtual: LocalDate): BigDecimal {
        if(datasCobranca.ultimoDiaPrazo(diaAtual)) {
            println("iof total $iofTotal")
            return montanteAPagar
        }else if (datasCobranca.diaVencimento(diaAtual)) {
            return Amortizacao().amortizar(montanteAPagar, parcela)
        }else {
            return montanteAPagar
        }
    }

    fun aplicaIof(montanteAPagar: BigDecimal, diaAtual: LocalDate): BigDecimal {
        if(datasCobranca.primeiroDia(diaAtual)) {
            var impostoIofAdicional = iof.iofAdicional(montanteAPagar)
            println("$diaAtual - $impostoIofAdicional")
            iofTotal = iofTotal.add(impostoIofAdicional)
        }else {
            val impostoIofDiario = iof.iofDiario(montanteAPagar)
            println("$diaAtual - $impostoIofDiario")
            iofTotal = iofTotal.add(impostoIofDiario)
        }
        return iofTotal
    }
}