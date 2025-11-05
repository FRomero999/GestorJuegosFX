package org.example.gestorjuegosfx.game;

import lombok.Data;
import org.example.gestorjuegosfx.common.DAO;
import org.example.gestorjuegosfx.common.DataProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameDAO implements DAO<Game> {

    private DataSource dataSource;

    private Game mapper(ResultSet rs) throws SQLException {
        Game videoGame = new Game();
        videoGame.setId(rs.getInt("id"));
        videoGame.setTitle(rs.getString("title"));
        videoGame.setPlatform(rs.getString("platform"));
        videoGame.setYear(rs.getInt("year"));
        videoGame.setDescription(rs.getString("description"));
        videoGame.setUser_id(rs.getInt("user_id"));
        videoGame.setImage_url(rs.getString("image_url"));
        return videoGame;
    }

    public GameDAO(DataSource dataProvider) {
        this.dataSource = dataProvider;
    }

    @Override
    public Optional<Game> save(Game game) {
        String sql = "INSERT INTO games VALUES (0,?,?,?,?,?,?)";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
        ){
            pst.setString(1, game.getTitle());
            pst.setString(2, game.getPlatform());
            pst.setInt(3, game.getYear());
            pst.setString(4, game.getDescription());
            pst.setInt(5, game.getUser_id());
            pst.setString(6, game.getImage_url());

            Integer res = pst.executeUpdate();
            if(res>0){
                ResultSet keys = pst.getGeneratedKeys();
                keys.next();
                game.setId(keys.getInt(1));
                return Optional.of(game);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Game> update(Game game) {
        return Optional.empty();
    }

    @Override
    public Optional<Game> delete(Game game) {
        return Optional.empty();
    }

    @Override
    public List<Game> findAll() {
        return List.of();
    }

    @Override
    public Optional<Game> findById(Integer id) {
        return Optional.empty();
    }

    public List<Game> findAllByUserId(Integer id) {
        ArrayList<Game> listado = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE user_id = ?";
        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ){
            stmt.setInt(1, id);
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                listado.add( mapper(resultado) );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listado;
    }
}
