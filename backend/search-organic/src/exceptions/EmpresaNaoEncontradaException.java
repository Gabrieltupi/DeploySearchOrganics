package exceptions;

public class EmpresaNaoEncontradaException extends  Exception{
    public EmpresaNaoEncontradaException() {
        super("Usuário não encontrado");
    }
}
