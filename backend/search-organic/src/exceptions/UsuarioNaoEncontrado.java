package exceptions;

public class UsuarioNaoEncontrado extends Exception{
    public UsuarioNaoEncontrado() {
        super("Usuário não encontrado");
    }
}
