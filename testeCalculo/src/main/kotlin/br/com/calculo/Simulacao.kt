package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import br.com.proposta.Proposta
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate

class Simulacao {

    var proposta: Proposta
    val arredondamento: RoundingMode = RoundingMode.HALF_DOWN;

    constructor(proposta: Proposta, iof: IOF) {
        this.proposta = proposta
    }

    fun calcula(): SimulacaoResponse {

        if (proposta == null) {
            throw IllegalStateException("O emprestimo está nulo, por favor verificar os parâmetros")
        }else if (proposta != null && (proposta.getMeses() <= 0 || proposta.getValor().toLong() <= 0)) {
            throw IllegalStateException("O emprestimo com informações invalidas!")
        }

        if(prazoMaiorQue365Dias()) {
            var impostoIof = aplicaAliquotaMaximaApos365Dias();
            var montanteAPagar = montanteAPagar(impostoIof)

            return SimulacaoResponse(impostoIof)
        }else {
            val dataInicio: LocalDate = proposta.getDataCriacao().toLocalDate();
            var dataFinal: LocalDate = devolveTotalMeses();

            var diaAtual = dataInicio

            var iofTotal = BigDecimal.ZERO;
            var iofDiarioTotal: BigDecimal = BigDecimal.ZERO;
            var impostoIofAdicional: BigDecimal = BigDecimal.ZERO;

            var montanteAPagar = devolveValorConcedido();
            var parcela = devolveParcelaPadrao();

            var totalDias: Long = 0

            while (dataFinal >= diaAtual) {

               val diaVencimento: Boolean = diaAtual.dayOfMonth == dataInicio.dayOfMonth;
               val naoEhPrimeiroDia: Boolean = diaAtual != dataInicio;
               val primeiroDia = diaAtual.equals(dataInicio);
               val diaVencimentoMensal: Boolean = (diaVencimento) && (naoEhPrimeiroDia);
               val ultimoDiaPrazoAcordado : Boolean = diaAtual == dataFinal;

                var impostoDiario: BigDecimal = aplicaAliquotaIofDiarioAoMontante(montanteAPagar)
                println("dia " + diaAtual + " - " + impostoDiario + " - " + montanteAPagar)

                iofTotal = iofTotal.add(impostoDiario)

                if (primeiroDia) {
                    impostoIofAdicional = aplicaIofAdicionalAoMontante(montanteAPagar)
                    iofTotal = iofTotal.add(impostoIofAdicional)
                }

                if (diaVencimentoMensal) {
                    montanteAPagar = aplicaAmortizacaoParcela(montanteAPagar, parcela);
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

    private fun aplicaIofAdicionalAoMontante(montanteAPagar: BigDecimal) =
            montanteAPagar.multiply(AliquotaIOF.ADICIONAL.value).setScale(5, arredondamento)

    private fun aplicaAliquotaIofDiarioAoMontante(montanteAPagar: BigDecimal) =
            montanteAPagar.multiply(AliquotaIOF.POR_DIA_CORRIDO.value).setScale(5, arredondamento)

    private fun aplicaAmortizacaoParcela(montanteAPagar: BigDecimal, parcela: BigDecimal) =
            montanteAPagar.subtract(parcela)
                    .setScale(2, RoundingMode.HALF_DOWN)

    private fun devolveValorConcedido() = proposta.getValor().setScale(2, arredondamento)

    private fun prazoMaiorQue365Dias(): Boolean {
        return proposta.getMeses() > 12;
    }

    private fun aplicaAliquotaMaximaApos365Dias(): BigDecimal {
        return proposta.getValor()
            .multiply(AliquotaIOF.ALIQUOTA_MAXIMA.value)
            .setScale(2, arredondamento)
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

    private fun devolveParcelaPadrao(): BigDecimal {
        return proposta.getValor()
            .divide(proposta.getMeses().toBigDecimal(), arredondamento)
            .setScale(3, arredondamento);
    }
};