package com.mikaioyamada.Gestao_Vagas.exceptions;

public class JobNotFoundException  extends  RuntimeException{
    public JobNotFoundException(){
        super("Job not found");
    }
}
