package exceptions;

public class UsuarioJaCadastradoException extends Exception {
    public UsuarioJaCadastradoException() {
        super("Usuário já está cadastrado");
    }
}
