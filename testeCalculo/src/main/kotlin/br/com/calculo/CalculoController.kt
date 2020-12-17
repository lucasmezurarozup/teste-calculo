package br.com.calculo

import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import javax.print.attribute.standard.Media
import javax.validation.Valid

@Controller("/calculo")
class CalculoController {

    @Post
    @Consumes(MediaType.APPLICATION_JSON)
     fun calcula(@Body calculoRequest: CalculoRequest): HttpResponse<*> {
        return HttpResponse.ok(calculoRequest);
    }
}