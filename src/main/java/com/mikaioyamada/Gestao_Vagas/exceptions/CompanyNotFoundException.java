package com.mikaioyamada.Gestao_Vagas.exceptions;

public class CompanyNotFoundException  extends  RuntimeException{
    public CompanyNotFoundException(){
        super("Company not found");
    }
}