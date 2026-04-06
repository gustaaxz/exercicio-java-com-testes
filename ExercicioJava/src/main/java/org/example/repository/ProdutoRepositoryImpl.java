package org.example.repository;

import org.example.model.Produto;
import org.example.util.ConexaoBanco;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository{

    @Override
    public Produto save(Produto produto) throws SQLException {
        String command = """
                INSERT INTO produtos
                (nome, preco, quantidade, categoria)
                VALUES
                (?,?,?,?)
                """;
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(command,
                     PreparedStatement.RETURN_GENERATED_KEYS)){
                 stmt.setString(1, produto.getNome());
                 stmt.setDouble(2, produto.getPreco());
                 stmt.setInt(3, produto.getQuantidade());
                 stmt.setString(4, produto.getCategoria());

                 stmt.executeUpdate();

                 ResultSet rs = stmt.getGeneratedKeys();
                 if(rs.next()) {
                     produto.setId(rs.getInt(1));
                     return produto;
                 }
        }
        return null;
    }

    @Override
    public List<Produto> findAll() throws SQLException {
        String query = """
                SELECT id, nome, preco, quantidade, categoria
                FROM produto
                """;
        List<Produto> produtos = new ArrayList<>();
        try (Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                Double preco = rs.getDouble("preco");
                int quantidade = rs.getInt("quantidade");
                String categoria = rs.getString("categoria");

                var produto = new Produto(id, nome, preco, quantidade, categoria);
            }
        }
        return List.of();
    }

    @Override
    public Produto findById(int id) throws SQLException {
        return null;
    }

    @Override
    public Produto update(Produto produto) throws SQLException {
        return null;
    }

    @Override
    public void deleteById(int id) throws SQLException {

    }
}
