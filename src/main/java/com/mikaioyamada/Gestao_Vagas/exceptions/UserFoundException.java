package com.mikaioyamada.Gestao_Vagas.exceptions;

public class UserFoundException  extends  RuntimeException{
    public UserFoundException(){
        super("Usuário já existe");
    }
}
