package br.com.calculo

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import java.math.BigDecimal
import javax.inject.Inject

@Controller("/calculo")
class CalculoController() {

    @Post("/")
    @Consumes(MediaType.APPLICATION_JSON)
     fun calcula(@Body calculoRequest: CalculoRequest): HttpResponse<*> {

        val operacao: Operacao = Operacao(
                calculoRequest.getValor(),
                calculoRequest.getAmortizacoes()
        );

        val iof: IOF = IOF(operacao);

        return HttpResponse.ok(iof.calcular());
    }
}