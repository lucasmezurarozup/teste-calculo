package br.com.calculo

import br.com.emprestimo.*
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import java.math.BigDecimal

@Controller("/calculo")
class CalculoController() {


    @Post("/", consumes = arrayOf(MediaType.APPLICATION_JSON))
    fun visualizaProposta(@Body emprestimoRequest: EmprestimoRequest): HttpResponse<*> {

        val emprestimo: Emprestimo = Emprestimo(emprestimoRequest.getNome(),
            emprestimoRequest.getMeses(),
            emprestimoRequest.getValor(),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO);


       val simulacao = Simulacao(emprestimo);

       val simulacaoResponse : SimulacaoResponse = simulacao.calcula()

        return HttpResponse.ok(simulacaoResponse);
    }
}