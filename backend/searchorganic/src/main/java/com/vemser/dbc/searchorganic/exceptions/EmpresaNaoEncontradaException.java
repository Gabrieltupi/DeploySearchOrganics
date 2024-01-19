package com.vemser.dbc.searchorganic.exceptions;

public class EmpresaNaoEncontradaException extends  Exception{
    public EmpresaNaoEncontradaException() {
        super("Empresa não encontrada");
    }
}
