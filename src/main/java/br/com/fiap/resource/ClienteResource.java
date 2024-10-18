package br.com.fiap.resource;

import br.com.fiap.dao.ClienteDAO;
import br.com.fiap.model.Cliente;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import java.util.List;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    private ClienteDAO clienteDAO = new ClienteDAO();

    // POST - Criar cliente
    @POST
    public Response criarCliente(Cliente cliente, @Context UriInfo uriInfo) {
        try {
            clienteDAO.cadastrar(cliente);
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            System.out.println("Cliente Criado:\n" + cliente.toString());
            return Response.created(builder.path(Integer.toString(cliente.getId())).build()).entity(cliente).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Erro ao criar cliente: " + e.getMessage()).build();
        }
    }

    // GET - Cliente específico
    @GET
    @Path("/{id}")
    public Response getCliente(@PathParam("id") int id) {
        Cliente cliente = clienteDAO.pesquisarPorId(id);
        if (cliente != null) {
            System.out.println("Cliente:\n" + cliente.toString());
            return Response.ok(cliente).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
    }

    // GET - Listar clientes
    @GET
    public Response getTodosClientes() {
        List<Cliente> clientes = clienteDAO.listar();
        System.out.println("Lista de Clientes:\n" + clientes.toString());
        return Response.ok(clientes).build();

    }

    // PUT - Atualizar cliente
    @PUT
    @Path("/{id}")
    public Response atualizarCliente(@PathParam("id") int id, Cliente clienteAtualizado) {
        Cliente cliente = clienteDAO.pesquisarPorId(id);
        if (cliente != null) {

            cliente.setNome(clienteAtualizado.getNome());
            cliente.setEmail(clienteAtualizado.getEmail());
            cliente.setTelefone(clienteAtualizado.getTelefone());
            cliente.setTipo(clienteAtualizado.getTipo());

            clienteDAO.atualizar(cliente);
            System.out.println("Cliente Atualizado:\n" + cliente.toString());
            return Response.ok(cliente).build();
        } else {
            System.out.println("Cliente não encontrado com ID: " + id);
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
    }

    // DELETE - Remover cliente
    @DELETE
    @Path("/{id}")
    public Response removerCliente(@PathParam("id") int id) {
        Cliente cliente = clienteDAO.pesquisarPorId(id);
        if (cliente != null) {
            clienteDAO.remover(id);
            System.out.println("Cliente Removido:\n" + cliente.toString());
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Cliente não encontrado").build();
        }
    }
}
