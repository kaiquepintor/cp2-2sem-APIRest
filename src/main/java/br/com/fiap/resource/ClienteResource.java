package br.com.fiap.resource;

import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.model.Cliente;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.util.Date;
import java.util.List;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteDAO clienteDAO = new ClienteDAO();

    // POST /clientes - Criar um novo cliente
    @POST
    public Response criarCliente(Cliente cliente, @Context UriInfo uriInfo) {
        try {
            cliente.setDataCadastro(new Date()); // Define a data de cadastro como a data atual
            clienteDAO.cadastrar(cliente);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            return Response.created(builder.path(Integer.toString(cliente.getId())).build()).entity(cliente).build();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o stack trace no console
            return Response.serverError().entity("Erro ao criar cliente: " + e.getMessage()).build();
        }
    }

    // GET /clientes/{id} - Retornar um cliente específico
    @GET
    @Path("/{id}")
    public Response getCliente(@PathParam("id") int id) {
        Cliente cliente = clienteDAO.pesquisarPorId(id);
        if (cliente != null) {
            return Response.ok(cliente).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
    }

    // GET /clientes - Retornar todos os clientes
    @GET
    public Response getTodosClientes() {
        List<Cliente> clientes = clienteDAO.listar();
        return Response.ok(clientes).build();
    }

    // PUT /clientes/{id} - Atualizar um cliente existente
    @PUT
    @Path("/{id}")
    public Response atualizarCliente(@PathParam("id") int id, Cliente clienteAtualizado) {
        System.out.println("Atualizando cliente com ID: " + id);
        System.out.println("Dados recebidos: " + clienteAtualizado);
        Cliente cliente = clienteDAO.pesquisarPorId(id);
        if (cliente != null) {
            // Atualiza os dados do cliente
            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            cliente.setTelefone(clienteAtualizado.getTelefone());
            cliente.setDataCadastro(clienteAtualizado.getDataCadastro());
            cliente.setTipo(clienteAtualizado.getTipo());
            clienteDAO.atualizar(cliente);
            return Response.ok(cliente).build();
        } else {
            System.out.println("Cliente não encontrado com ID: " + id);
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
    }

    // DELETE /clientes/{id} - Remover um cliente
    @DELETE
    @Path("/{id}")
    public Response removerCliente(@PathParam("id") int id) {
        Cliente cliente = clienteDAO.pesquisarPorId(id);
        if (cliente != null) {
            clienteDAO.remover(id);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
    }
}
