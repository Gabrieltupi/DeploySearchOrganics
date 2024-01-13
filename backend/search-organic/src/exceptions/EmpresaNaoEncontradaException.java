package exceptions;

public class EmpresaNaoEncontradaException extends  Exception{
    public EmpresaNaoEncontradaException() {
        super("Empresa não encontrada");
    }
}
