package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INT(64) NOT NULL AUTO_INCREMENT," +
                "name VARCHAR(32)," +
                "lastName VARCHAR(32)," +
                "age SMALLINT(64), " +
                "PRIMARY KEY(id))";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM Users WHERE id = ?";

        try (Connection connection = Util.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM Users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            for (User user : userList) {
                System.out.println(user.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE Users";

        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection error");
        }
    }
}
