package com.mikaioyamada.Gestao_Vagas.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor //cria um construtor com argumentos
public class ErrorMessageDTO { //converte um objeto pro formato que estaremos passando.
    private String message;
    private String field;

}
