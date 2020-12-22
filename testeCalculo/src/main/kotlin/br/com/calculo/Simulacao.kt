package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import br.com.emprestimo.Emprestimo
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters

class Simulacao {

    var emprestimo: Emprestimo

    constructor(emprestimo: Emprestimo) {
        this.emprestimo = emprestimo
    }

    fun calcula(): SimulacaoResponse {

        if(prazoMaiorQue365Dias()) {
            var impostoIof = aplicaAliquotaMaximaApos365Dias();
            var montanteAPagar = montanteAPagar(impostoIof)

            return SimulacaoResponse(impostoIof)
        }else {
            val dataInicio: LocalDate = emprestimo.getDataCriacao().toLocalDate();

            var dataFinal: LocalDate = devolveTotalMeses();

            var diaAtual = dataInicio

            var iofTotal = BigDecimal.ZERO;
            var iofPeriodoEntreParcela: BigDecimal = BigDecimal.ZERO

            var montanteAPagar = emprestimo.getValor().setScale(2, RoundingMode.HALF_DOWN)
            var impostoIofAdicional: BigDecimal = BigDecimal.ZERO;

            var parcela = devolveParcelaPadrao();

            var totalDias: Long = 0

            while (diaAtual <= dataFinal) {

                var impostoDiario: BigDecimal =
                    montanteAPagar.multiply(AliquotaIOF.POR_DIA_CORRIDO.value).setScale(5, RoundingMode.HALF_DOWN)
                println("dia " + diaAtual + " - " + impostoDiario + " - " + montanteAPagar)

                iofTotal = iofTotal.add(impostoDiario)



                //montanteAPagar = montanteAPagar.add(impostoDiario).setScale(3, RoundingMode.HALF_DOWN)

                if (diaAtual.equals(dataInicio)) {
                    impostoIofAdicional =
                        montanteAPagar.multiply(AliquotaIOF.ADICIONAL.value).setScale(5, RoundingMode.HALF_DOWN)
                    println("IOF ADICIONAL " + impostoIofAdicional)
                    iofTotal = iofTotal.add(impostoIofAdicional)
                    //montanteAPagar = montanteAPagar.add(impostoIofAdicional)
                }

                if ((diaAtual.dayOfMonth == dataInicio.dayOfMonth) && (diaAtual != dataInicio)) {
                    if (diaAtual == dataFinal) {
                        println()
                        println("iof adicional " + impostoIofAdicional)
                        println("total acumulado (iof diario) " + iofPeriodoEntreParcela)
                        println(" dia - " + totalDias + " - " + diaAtual + " de amortizacao do montante atual de " + montanteAPagar)
                        println("amortização de " + parcela)
                        montanteAPagar = montanteAPagar.subtract(parcela)
                            .setScale(2, RoundingMode.HALF_DOWN)
                        println("montante apos a amortizacao " + montanteAPagar)
                    } else {
                        println()
                        println("total acumulado (iof diario) " + iofPeriodoEntreParcela)
                        println(" dia - " + totalDias + " - " + diaAtual + " de amortizacao do montante atual de " + montanteAPagar)
                        println("amortização de " + parcela)
                        montanteAPagar = montanteAPagar.subtract(parcela)
                            .setScale(2, RoundingMode.HALF_DOWN);
                        println("montante apos a amortizacao " + montanteAPagar)
                    }
                }

                diaAtual = diaAtual.plusDays(1)
                totalDias++
                iofPeriodoEntreParcela = iofPeriodoEntreParcela.add(impostoDiario).setScale(5, RoundingMode.HALF_DOWN)
            }

            return SimulacaoResponse(iofTotal.setScale(3, RoundingMode.HALF_DOWN))
        }
    }

    fun prazoMaiorQue365Dias(): Boolean {
        return emprestimo.getMeses() > 12;
    }

    fun aplicaAliquotaMaximaApos365Dias(): BigDecimal {
        return emprestimo.getValor()
            .multiply(AliquotaIOF.ALIQUOTA_MAXIMA.value)
            .setScale(2, RoundingMode.HALF_DOWN)
    }

    fun montanteAPagar(impostoIof: BigDecimal): BigDecimal {
        return emprestimo.getValor()
            .add(impostoIof)
            .setScale(2, RoundingMode.HALF_DOWN);
    }

    fun devolveTotalMeses(): LocalDate {
        return emprestimo.getDataCriacao()
            .plusMonths(emprestimo.getMeses().toLong()).toLocalDate()
    }

    fun devolveParcelaPadrao(): BigDecimal {
        return emprestimo.getValor()
            .divide(emprestimo.getMeses().toBigDecimal(), RoundingMode.HALF_DOWN)
            .setScale(3, RoundingMode.HALF_DOWN);
    }
};