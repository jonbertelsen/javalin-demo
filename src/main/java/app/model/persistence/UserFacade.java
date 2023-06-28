package app.model.persistence;

import app.model.entities.User;
import app.exceptions.CustomException;

import java.util.List;

/***
 * Facade methods for database operations on the User entity
 */
public class UserFacade
{
    public static User login(String username, String password, ConnectionPool connectionPool) throws CustomException
    {
        return UserMapper.login(username, password, connectionPool);
    }

    public static User createUser(String username, String password, String role, ConnectionPool connectionPool) throws CustomException
    {
        return UserMapper.createUser(username, password, role, connectionPool);
    }

    public static List<User> getAllUsers(ConnectionPool connectionPool) throws CustomException
    {
        return UserMapper.getAllUsers(connectionPool);
    }
}
