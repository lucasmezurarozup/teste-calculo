package br.com.calculo

import java.time.*
import java.time.temporal.TemporalAdjusters

class CalculadoraPrazo {

    private val totalDias: Long = 0;
    private var operacao: Operacao? = null;

    constructor(operacao: Operacao?) {
        this.operacao = operacao;
    }

    fun totalDiasCorridos(dataConcessao: LocalDateTime): Long {

        val final : LocalDateTime = dataConcessao.plusMonths(operacao!!.getParcelas().toLong());
        val contagemDias: Long = Duration.between(dataConcessao, final).toDays();

        println(contagemDias)

        return contagemDias;
    }

    fun totalDiasMesAtualAPartirDataFornecida(dataConcessao: LocalDateTime): Long {
        val finalMes: LocalDateTime = dataConcessao.with(TemporalAdjusters.lastDayOfMonth());
        val contagemDias: Long = Duration.between(dataConcessao, finalMes).toDays();

        return contagemDias;
    }

    fun totalDiasProximoMesCobranca(dataConcessao: LocalDateTime, periodo: Long): Long {

        var pegaMes = dataConcessao.plusMonths(periodo)
        var inicioMes = LocalDateTime.of(LocalDate.of(pegaMes.year, pegaMes.month, 1), LocalTime.MIDNIGHT)

        val finalMes: LocalDateTime = inicioMes.with(TemporalAdjusters.lastDayOfMonth());
        val contagemDias: Long = Duration.between(inicioMes, finalMes).toDays();

        return contagemDias
    }

    fun totalMesesEntreDatas(inicio: LocalDateTime, final: LocalDateTime): Int {
        return Period.between(inicio.toLocalDate(),final.toLocalDate()).months;
    }
}