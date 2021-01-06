package br.com.server

import br.com.calculo.Amortizacao
import br.com.calculo.IOF
import br.com.calculo.Parcela
import br.com.calculo.Simulacao
import br.com.calculo.iof.IOFServiceGrpc
import br.com.calculo.iof.IOFServiceGrpcKt
import br.com.calculo.iof.PropostaRequest
import br.com.calculo.iof.SimulacaoResponse
import br.com.proposta.Proposta
import br.com.proposta.Situacao
import br.com.proposta.TipoEmprestimo
import io.grpc.stub.StreamObserver
import java.math.BigDecimal
import javax.inject.Singleton

@Singleton
class IOFEndpoint: IOFServiceGrpcKt.IOFServiceCoroutineImplBase() {



    override suspend fun simular(request: PropostaRequest): SimulacaoResponse {

        val proposta = Proposta(request.nome,
            request.meses,
            BigDecimal.valueOf(request.valor),
            Situacao.SOLICITADO,
            TipoEmprestimo.MICRO_CREDITO)

        val simulacao = Simulacao(proposta);
        System.out.println("Nome: "+request.nome);
        System.out.println("Meses: "+request.meses);
        System.out.println("Valor: "+request.valor);

        return SimulacaoResponse
            .newBuilder()
            .setMontanteAPagar(simulacao.calcula().getMontanteAPagar().toDouble())
            .build()
    }
}