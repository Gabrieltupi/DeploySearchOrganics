package exceptions;

public class EnderecoNaoEncontrado extends Exception{
    public EnderecoNaoEncontrado() {
        super("Endereço não encontrado");
    }
}
