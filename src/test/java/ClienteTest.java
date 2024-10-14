import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.model.Cliente;

import java.util.Date;
import java.util.List;

public class ClienteTest {
    public static void main(String[] args) {
        ClienteDAO clienteDAO = new ClienteDAO();

        // 1. Cadastrar um novo cliente
        Cliente novoCliente = new Cliente();
        novoCliente.setNome("Maria Oliveira");
        novoCliente.setEmail("maria.oliveira@example.com");
        novoCliente.setTelefone("99999-9999");
        novoCliente.setDataCadastro(new Date());
        novoCliente.setTipo(Cliente.TipoCliente.INDIVIDUAL);
        int idGerado = clienteDAO.cadastrar(novoCliente);
        System.out.println("Cliente cadastrado com ID: " + idGerado);

        // 2. Listar todos os clientes
        List<Cliente> clientes = clienteDAO.listar();
        System.out.println("Lista de Clientes:");
        clientes.forEach(System.out::println);

        // 3. Pesquisar cliente por ID
        int idPesquisa = novoCliente.getId(); // Supondo que o ID foi gerado e atualizado
        Cliente clientePesquisado = clienteDAO.pesquisarPorId(idPesquisa);
        System.out.println("Cliente pesquisado: " + clientePesquisado);

        // 4. Atualizar cliente
        if (clientePesquisado != null) {
            clientePesquisado.setTelefone("88888-8888");
            clienteDAO.atualizar(clientePesquisado);
            System.out.println("Cliente atualizado: " + clientePesquisado);
        }

        // 5. Remover cliente
        clienteDAO.remover(idPesquisa);
        System.out.println("Cliente removido com ID: " + idPesquisa);

        // 6. Verificar remoção
        Cliente clienteRemovido = clienteDAO.pesquisarPorId(idPesquisa);
        if (clienteRemovido == null) {
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Erro ao remover cliente.");
        }
    }
}
