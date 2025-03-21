/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public void cadastrarProduto(ProdutosDTO produto) {
        String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getValor());
            stmt.setString(3, produto.getStatus());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        String sql = "SELECT id, nome, valor, status FROM produtos";

        try {
            conn = new conectaDAO().connectDB(); // Estabelece a conexão
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listagem;
    }

    public void venda(int id) {
        String sql = "UPDATE produtos SET status='vendido' WHERE id =?";
        try {
            conn = new conectaDAO().connectDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int linhaAfetada = stmt.executeUpdate();
            if (linhaAfetada > 0) {
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);

            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao vender o produto:" + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public ArrayList<ProdutosDTO>listarProdutosVendidos(){
        ArrayList<ProdutosDTO>listagem = new ArrayList<>();
        String sql = "SELECT id,nome,valor,status FROM produtos WHERE status='vendido'";
        try{
             conn = new conectaDAO().connectDB();
            PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery();
             while(resultSet.next()){
                  ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("nome"));
                produto.setValor(resultSet.getInt("valor"));
                produto.setStatus(resultSet.getString("status"));
                listagem.add(produto);
                 
             }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listagem;
    }

}
