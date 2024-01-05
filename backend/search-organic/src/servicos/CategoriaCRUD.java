package servicos;

import modelo.Categoria;
import modelo.Empresa;

import java.util.ArrayList;

public class CategoriaCRUD {

    private ArrayList<Categoria> categorias = new ArrayList<>();

    public void criarCategoria(String variedade) {
        Categoria novaCategoria = new Categoria(variedade);
        categorias.add(novaCategoria);

    }

    public void exibirCategoria(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                categoria.imprimir();
                return;
            }
        }
        System.out.println("Categoria não encontrada!");
    }

    public void listarCategorias() {
        for (Categoria categoria : categorias) {
            categoria.imprimir();
        }
    }

    public void atualizarCategoria(int id, String novaVariedade) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                categoria.setVariedade(novaVariedade);
                System.out.println("Categoria atualizada!");
                return;
            }
        }
        System.out.println("Categoria não encontrada!");
    }

    public void excluirCategoria(int id) {
        for (Categoria categoria : categorias) {
            if (categoria.getId() == id) {
                categorias.remove(categoria);
                System.out.println("Categoria removida!");
                return;
            }
        }
        System.out.println("Categoria não encontrada!");
    }
}
