package servicos;
import modelo.Consumidor;
import java.util.ArrayList;

public class ConsumidorCRUD {

    private ArrayList<Consumidor> consumidores;

    public ConsumidorCRUD(){
        this.consumidores = new ArrayList<>();
    }

    public void criar(Consumidor novoConsumidor){
        consumidores.add(novoConsumidor);
    }

    public void exibirTodos(){
        for(Consumidor consumidor : consumidores){
            consumidor.imprimir();
        }
    }

    public void exibirConsumidor(){
        for(Consumidor consumidor : consumidores){
            System.out.println("Nome: " + consumidor);
        }
    }

    public void exibir(int id){
        for(Consumidor consumidor : consumidores){
            if(id == consumidor.getId()){
                consumidor.imprimir();
                return;
            }
        }
        System.out.println("Consumidor não encontrado!");
    }

    public void editar(int id, Consumidor consumidorEditado){
        for(Consumidor consumidor : consumidores){
            if(id == consumidor.getId()){
                consumidor.setCpf(consumidorEditado.getCpf());
                consumidor.setUsuario(consumidorEditado.getUsuario());
                System.out.println("Edição realizada com sucesso!");
                return;
            }
        }
        System.out.println("Consumidor não encontrado");
    }

    public void remover(int id){
        for(Consumidor consumidor: consumidores){
            if(consumidor.getId() == id){
                consumidores.remove(consumidor);
                return;
            }
        }
        System.out.println("Consumidor não encontrado!");
    }

}
