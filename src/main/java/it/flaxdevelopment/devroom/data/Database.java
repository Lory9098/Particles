package it.flaxdevelopment.devroom.data;

import lombok.Getter;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("all")
public class Database {

    @Getter
    private static Database instance;

    private String host;
    private String database;
    private String user;
    private String password;
    private Connection connection;

    public Database(String host, String database, String user, String password){
        instance = this;
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public void connect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        try {
            Consumer<List<HashMap<List<HashMap<String, Integer>>, List<HashMap<String, Integer>>>>> cazzo = new Consumer<List<HashMap<List<HashMap<String, Integer>>, List<HashMap<String, Integer>>>>>() {
                @Override
                public void accept(List<HashMap<List<HashMap<String, Integer>>, List<HashMap<String, Integer>>>> hashMaps) {
                    System.out.println("porcodio");
                }
            };
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
    }

    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS players (uuid VARCHAR(255), particles VARCHAR(255))");
    }

    public void addPlayer(Player player) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * FROM players WHERE uuid='" + player.getUniqueId().toString() + "'");
        if (!result.next()) {
            statement.executeUpdate("INSERT INTO players (uuid, particles) VALUES ('" + player.getUniqueId().toString() + "', null)");
        }
    }

    public void updatePlayer(Player player, Particle p) throws SQLException {
        String particles = null;
        if(p != null){
            particles = p.name();
        }else{
            p = null;
        }
        String query = "UPDATE players SET particles = ? WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, particles);
            statement.setString(2, player.getUniqueId().toString());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getParticles(Player player) {
        String query = "SELECT particles FROM players WHERE uuid = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, player.getUniqueId().toString());
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getString("particles");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
