package br.com.emprestimo

import java.time.LocalDateTime

class Liberacao {

    private var funcionarioId: String? = null;
    private var dataLiberacao: LocalDateTime = LocalDateTime.now()
    private var emprestimoId: String? = null;
}
