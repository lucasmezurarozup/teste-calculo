package br.com.calculo

import br.com.proposta.Proposta
import br.com.proposta.PropostaRequest
import br.com.proposta.Situacao
import br.com.proposta.TipoEmprestimo
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*

@Controller("/calculo")
class CalculoController() {


    @Post("/", consumes = arrayOf(MediaType.APPLICATION_JSON))
    fun visualizaProposta(@Body propostaRequest: PropostaRequest): HttpResponse<*> {

        val proposta: Proposta = Proposta(propostaRequest.getNome(),
            propostaRequest.getMeses(),
            propostaRequest.getValor(),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO);

       val simulacao = Simulacao(proposta, IOF(), Amortizacao(), Parcela());

       val simulacaoResponse : SimulacaoResponse = simulacao.calcula()

        return HttpResponse.ok(simulacaoResponse);
    }
}