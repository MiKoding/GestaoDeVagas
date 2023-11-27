package com.mikaioyamada.Gestao_Vagas.exceptions;

public class UserNotFoundException  extends  RuntimeException{
    public UserNotFoundException(){
        super("User not found");
    }
}