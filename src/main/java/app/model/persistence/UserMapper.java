/**
 * DB methods for user. This class is package protected on purpose
 */
package app.model.persistence;

import app.model.entities.User;
import app.exceptions.CustomException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

class UserMapper
{
    static User login(String userName, String password, ConnectionPool connectionPool) throws CustomException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        User user = null;

        String sql = "SELECT * FROM \"user\" WHERE user_name = ? AND password = ?";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, userName);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    String role = rs.getString("role");
                    user = new User(userName, password, role);
                } else
                {
                    throw new CustomException("Wrong username or password");
                }
            }
        } catch (SQLException ex)
        {
            throw new CustomException(ex, "Error logging in. Something went wrong with the database");
        }
        return user;
    }

    static User createUser(String userName, String password, String role, ConnectionPool connectionPool) throws CustomException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        User user;
        String sql = "insert into \"user\" (user_name, password, role) values (?,?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1, userName);
                ps.setString(2, password);
                ps.setString(3, role);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    user = new User(userName, password, role);
                } else
                {
                    throw new CustomException("The user with username = " + userName + " could not be inserted into the database");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new CustomException(ex, "Could not insert username into database");
        }
        return user;
    }

    static List<User> getAllUsers(ConnectionPool connectionPool) throws CustomException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        List<User> userList = new ArrayList<>();
        String sql = "select * from \"user\"";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();

                while (rs.next())
                {
                    String userName = rs.getString("user_name");
                    String password = rs.getString("password");
                    String role = rs.getString("role");
                    User user = new User(userName, password, role);
                    userList.add(user);
                }
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new CustomException(ex, "Could not get users from database");
        }
        return userList;
    }
}
