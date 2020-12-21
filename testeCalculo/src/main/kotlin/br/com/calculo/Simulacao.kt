package br.com.calculo

import br.com.calculo.aliquotas.AliquotaIOF
import br.com.emprestimo.Emprestimo
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import java.util.function.Consumer

class CobradoMensalmenteIOF {

    var amortizacaoEmprestimo: AmortizacaoEmprestimo;
    var emprestimo: Emprestimo

    constructor(emprestimo: Emprestimo, amortizacaoEmprestimo: AmortizacaoEmprestimo) {
        this.emprestimo = emprestimo
        this.amortizacaoEmprestimo = amortizacaoEmprestimo
    }

    fun simulacao(dataSimulacao: LocalDateTime) {

        val dataInicioMes: LocalDateTime =
            LocalDateTime.of(LocalDate.of(dataSimulacao.year, dataSimulacao.month, 1), LocalTime.MIDNIGHT);
        var dataFinal: LocalDateTime = emprestimo.getDataCriacao().plusMonths(emprestimo.getMeses().toLong());
        val totalMeses = CalculadoraPrazo(null).totalMesesEntreDatas(emprestimo.getDataCriacao(), dataFinal)


        println("inicio - " + dataInicioMes)
        println("final - " + dataFinal)

        var valor = emprestimo.getValor();

        println("Montante " + valor)

        var mesAtual: Long = 0;

        do {
            /*valor = valor.subtract(amortizacaoEmprestimo.getValorAmortizado())
           println("mês "+totalMesesPercorridos+" valor restante "+ valor)*/

            var totalDiasMesAgora = 0
            if (mesAtual.toInt() == 0) {
                totalDiasMesAgora =
                    CalculadoraPrazo(null)
                        .totalDiasMesAtualAPartirDataFornecida(emprestimo.getDataCriacao())
                        .toInt()
                println("total dias no primeiro mês - " + totalDiasMesAgora)
            } else {
                totalDiasMesAgora = CalculadoraPrazo(null)
                    .totalDiasProximoMesCobranca(emprestimo.getDataCriacao(), mesAtual)
                    .toInt() + 1
                println("total dias - " + totalDiasMesAgora)
            }

            var totalImpostoMes: BigDecimal = BigDecimal.ZERO


            println("montante atual - " + valor)

            var impostoDiario = valor.multiply(AliquotaIOF.POR_DIA_CORRIDO.value)

            println("iof diario sobre o montante - " + impostoDiario)

            var diasCobrandoIOF: Int = 0
            while (diasCobrandoIOF < totalDiasMesAgora) {

                totalImpostoMes = totalImpostoMes.add(impostoDiario)

                diasCobrandoIOF++
            }


            println("Mes " + mesAtual + " - dias " + totalDiasMesAgora + " " + " iof dias corridos mensal: " + totalImpostoMes)

            mesAtual++;

        } while (mesAtual <= totalMeses)

    }

    fun simulacao2() {

        val dataInicio: LocalDate = emprestimo.getDataCriacao().toLocalDate();
        var dataFinal: LocalDate = emprestimo.getDataCriacao().plusMonths(emprestimo.getMeses().toLong()).toLocalDate();

        var dataInicio2 = dataInicio

        var iofPeriodoEntreParcela: BigDecimal = BigDecimal.ZERO

        var montanteAPagar = emprestimo.getValor().setScale(2, RoundingMode.HALF_DOWN)
        var impostoIofAdicional: BigDecimal = BigDecimal.ZERO;

        while(dataInicio2 <= dataFinal) {

            var impostoDiario: BigDecimal = montanteAPagar.multiply(AliquotaIOF.POR_DIA_CORRIDO.value).setScale(2, RoundingMode.HALF_DOWN)


            iofPeriodoEntreParcela = iofPeriodoEntreParcela.add(impostoDiario).setScale(2, RoundingMode.HALF_DOWN)
            montanteAPagar = montanteAPagar.add(impostoDiario).setScale(2, RoundingMode.HALF_DOWN)

            if (dataInicio2.equals(dataInicio)) {
                impostoIofAdicional = montanteAPagar.multiply(AliquotaIOF.ADICIONAL.value).setScale(2, RoundingMode.HALF_DOWN)
                println("IOF ADICIONAL " + impostoIofAdicional)
                montanteAPagar = montanteAPagar.add(impostoIofAdicional)
            }

            var verificaUltimoDiaMes: LocalDate = dataInicio2.with(TemporalAdjusters.lastDayOfMonth());
            if (verificaUltimoDiaMes.dayOfMonth == dataInicio2.dayOfMonth) {
                println()
                //println("ultimo dia do mês de "+ dataInicio2.month.name)
            }

            if ((dataInicio2.dayOfMonth == dataInicio.dayOfMonth) && (dataInicio2 != dataInicio)) {
                if (dataInicio2 == dataFinal) {
                    println()
                    println("iof adicional "+ impostoIofAdicional)
                    println("total acumulado (iof diario) " + iofPeriodoEntreParcela)
                    println(" dia " + dataInicio2 + " - "+ dataInicio2.month.name +" de amortizacao do montante atual de " + montanteAPagar)
                    println("amortização de " + amortizacaoEmprestimo.getValorAmortizado())
                    montanteAPagar = montanteAPagar.subtract(amortizacaoEmprestimo.getValorAmortizado())
                        .setScale(2, RoundingMode.HALF_DOWN)
                }else {
                    println()
                    println("total acumulado (iof diario) " + iofPeriodoEntreParcela)
                    println(" dia " + dataInicio2 + " de amortizacao do montante atual de " + montanteAPagar)
                    println("amortização de " + amortizacaoEmprestimo.getValorAmortizado())
                    montanteAPagar = montanteAPagar.subtract(amortizacaoEmprestimo.getValorAmortizado())
                        .setScale(2, RoundingMode.HALF_DOWN)
                }
            }

            dataInicio2 = dataInicio2.plusDays(1)

        }

    }
};