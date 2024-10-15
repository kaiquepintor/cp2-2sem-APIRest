package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Método para cadastrar um cliente
    public int cadastrar(Cliente cliente) {
        String sql = "INSERT INTO T_CP_CLIENTE (id, nome, email, telefone, data_cadastro, tipo) " +
                "VALUES (CLIENTE_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";
        int idGerado = -1;
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setDate(4, new java.sql.Date(cliente.getDataCadastro().getTime()));
            stmt.setString(5, cliente.getTipo().name());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                    cliente.setId(idGerado); // Atualiza o ID no objeto cliente
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idGerado;
    }

    // Método para listar todos os clientes
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM T_CP_CLIENTE";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setDataCadastro(rs.getDate("data_cadastro"));
                cliente.setTipo(Cliente.TipoCliente.valueOf(rs.getString("tipo")));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    // Método para pesquisar cliente por ID
    public Cliente pesquisarPorId(int id) {
        Cliente cliente = null;
        String sql = "SELECT * FROM T_CP_CLIENTE WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefone(rs.getString("telefone"));
                    cliente.setDataCadastro(rs.getDate("data_cadastro"));
                    cliente.setTipo(Cliente.TipoCliente.valueOf(rs.getString("tipo")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    // Método para atualizar um cliente existente
    public void atualizar(Cliente cliente) {
        String sql = "UPDATE T_CP_CLIENTE SET nome = ?, email = ?, telefone = ?, data_cadastro = ?, tipo = ? WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefone());
            stmt.setDate(4, new java.sql.Date(cliente.getDataCadastro().getTime()));
            stmt.setString(5, cliente.getTipo().name());
            stmt.setInt(6, cliente.getId());

            System.out.println("Atualizando cliente: " + cliente);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para remover um cliente por ID
    public void remover(int id) {
        String sql = "DELETE FROM T_CP_CLIENTE WHERE id = ?";
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
