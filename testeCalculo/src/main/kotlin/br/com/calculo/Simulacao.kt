package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import br.com.proposta.Proposta
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

class Simulacao(
    private val proposta: Proposta,
    private val iof: IOF,
    private val amortizacao: Amortizacao,
    private val parcela: Parcela) {

    private val arredondamento: RoundingMode = RoundingMode.HALF_DOWN

    private val dataInicio: LocalDate = proposta.getDataCriacao().toLocalDate()
    private val dataFinal: LocalDate = devolveTotalMeses()

    fun calcula(): SimulacaoResponse {

        if (proposta.getMeses() <= 0 || proposta.getValor().toLong() <= 0) {
            throw IllegalStateException("O emprestimo com informações invalidas!")
        }

        if(prazoMaiorQue365Dias()) {
            val impostoIof = iof.iofAliquotaMaxima(proposta.getValor()) //aplicaAliquotaMaximaApos365Dias();
            montanteAPagar(impostoIof)

            return SimulacaoResponse(impostoIof)
        }else {
            var diaAtual = dataInicio
            var (iofTotal,iofDiarioTotal, impostoIofAdicional) = listOf(BigDecimal.ZERO ,BigDecimal.ZERO, BigDecimal.ZERO)

            var montanteAPagar = devolveValorConcedido()
            val parcela = parcela.valorPadrao(proposta.getValor(), proposta.getMeses());

            var totalDias: Long = 0

            while (dataFinal >= diaAtual) {

               val diaVencimento: Boolean = amortizacao.diaVencimento(diaAtual, dataInicio)//diaAtual.dayOfMonth == dataInicio.dayOfMonth
               val naoEhPrimeiroDia: Boolean = diaAtual != dataInicio
               val primeiroDia = diaAtual.equals(dataInicio)
               val diaVencimentoMensal: Boolean = (diaVencimento) && (naoEhPrimeiroDia)
               val ultimoDiaPrazoAcordado : Boolean = diaAtual == dataFinal

                val impostoDiario: BigDecimal = aplicaIofDiario(montanteAPagar, diaAtual)
                iofTotal = iofTotal.add(impostoDiario)

                if (primeiroDia) {
                    impostoIofAdicional = iof.iofAdicional(montanteAPagar)
                    iofTotal = iofTotal.add(impostoIofAdicional)
                }

                //verificar a questão de menos de 30 dias. por exemplo FEV ou meses com menos de 31 dias.
                if (diaVencimentoMensal) {
                    montanteAPagar = amortizacao.amortizar(montanteAPagar, parcela) //aplicaAmortizacaoParcela(montanteAPagar, parcela);
                    imprimeInformacao(montanteAPagar, iofDiarioTotal, totalDias, diaAtual, parcela)

                    if (ultimoDiaPrazoAcordado) {
                        imprimeTotalEmIof(impostoIofAdicional, iofDiarioTotal)
                    }
                }

                diaAtual = diaAtual.plusDays(1)
                totalDias++
                iofDiarioTotal = sumarizaAoTotal(iofDiarioTotal, impostoDiario)
            }

            return SimulacaoResponse(iofTotal.setScale(3, arredondamento))
        }
    }

    private fun aplicaIofDiario(montanteAPagar: BigDecimal, diaAtual: LocalDate): BigDecimal {
        val impostoDiario: BigDecimal = iof.iofDiario(montanteAPagar)
        println("dia " + diaAtual + " - " + impostoDiario + " - " + montanteAPagar)
        return impostoDiario
    }

    private fun imprimeTotalEmIof(impostoIofAdicional: BigDecimal, iofDiarioTotal: BigDecimal) {
        println()
        println("iof adicional " + impostoIofAdicional)
        println("iof diario total " + iofDiarioTotal)
    }

    private fun imprimeInformacao(montanteAPagar: BigDecimal, iofDiarioTotal: BigDecimal, totalDias: Long, diaAtual: LocalDate, parcela: BigDecimal) {
        println("montante apos a amortizacao " + montanteAPagar)
        println()
        println("total acumulado (iof diario) " + iofDiarioTotal)
        println(" dia - " + totalDias + " - " + diaAtual + " de amortizacao do montante atual de " + montanteAPagar)
        println("amortização de " + parcela)
    }

    private fun sumarizaAoTotal(iofDiarioTotal: BigDecimal, impostoDiario: BigDecimal) =
            iofDiarioTotal.add(impostoDiario).setScale(5, arredondamento)

    private fun devolveValorConcedido() = proposta.getValor().setScale(2, arredondamento)

    private fun prazoMaiorQue365Dias(): Boolean {
        return proposta.getMeses() > 12;
    }

    private fun montanteAPagar(impostoIof: BigDecimal): BigDecimal {
        return proposta.getValor()
            .add(impostoIof)
            .setScale(2, arredondamento);
    }

    private fun devolveTotalMeses(): LocalDate {
        return proposta.getDataCriacao()
            .plusMonths(proposta.getMeses().toLong()).toLocalDate()
    }
};