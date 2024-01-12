package servicos;

import exceptions.SenhaIncorretaException;
import exceptions.UsuarioJaCadastradoException;
import modelo.Usuario;

public class AutenticacaoService {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();
    public Usuario autenticar(String login, String senha){
        try{
            Usuario usuario = usuarioRepository.buscaPorLogin(login);
            if(!(usuario.getPassword().equals(senha))){
                throw new SenhaIncorretaException();
            }

            return usuario;
        }
        catch (UsuarioJaCadastradoException usuJaCadastrado){
            throw new RuntimeException(usuJaCadastrado.getMessage());
        } catch (SenhaIncorretaException senhaIncorrExce) {
            throw new RuntimeException(senhaIncorrExce.getMessage());
        }
    }

}
