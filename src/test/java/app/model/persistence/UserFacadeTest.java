package app.model.persistence;

import app.model.entities.User;
import app.exceptions.CustomException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

class UserFacadeTest {

    private static final String USER = "dev";
    private static final String PASSWORD = "ax2";
    private static final String URL = "jdbc:postgresql://localhost:5432/startcode?currentSchema=test";

    private static ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL);

    @BeforeAll
    public static void setUpClass()
    {
        try (Connection testConnection = connectionPool.getConnection())
        {
            try (Statement stmt = testConnection.createStatement())
            {
                // The test schema is already created, so we only need to delete/create test tables
                stmt.execute("DROP TABLE IF EXISTS test.user");

                // TODO: Create user table. Add your own tables here in the samme manner
                stmt.execute("CREATE TABLE test.user AS (SELECT * from public.user) WITH NO DATA");
            }
        }
        catch (SQLException throwables)
        {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection())
        {
            try (Statement stmt = testConnection.createStatement())
            {
                // TODO: Remove all rows from all tables - add your own tables here
                stmt.execute("DELETE FROM test.user");

                // TODO: Insert a few users - insert rows into your own tables here
                stmt.execute("INSERT INTO test.user (user_name, password, role) " +
                        "VALUES ('user','1234','user'),('admin','1234','admin'), ('ben','1234','user')");
            }
        }
        catch (SQLException throwables)
        {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @AfterAll
    static void afterAll(){
        connectionPool.close();
    }


    @Test
    void testConnection() throws SQLException
    {
        assertNotNull(connectionPool);
    }

    @Test
    void getAllUsers() throws CustomException {
        int expectedSize = 3;
        User expectedUser1 = new User("user", "1234", "user");
        User expectedUser2 = new User("admin", "1234", "admin");
        User expectedUser3 = new User("ben", "1234", "user");
        List<User> actualUserList = UserFacade.getAllUsers(connectionPool);
        int actualSize = actualUserList.size();
        assertEquals(expectedSize, actualSize);
        // Check: https://codingnconcepts.com/java/unit-test-with-hamcrest-assertthat/#collection-matchers-for-list
        assertThat(actualUserList, containsInAnyOrder(expectedUser1, expectedUser2, expectedUser3));
    }

    @Test
    void login() throws CustomException {
        User expectedUser = new User("user", "1234", "user");
        User actualUser = UserFacade.login("user", "1234", connectionPool);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void invalidPasswordLogin() throws CustomException
    {
        assertThrows(CustomException.class, () -> UserFacade.login("user", "123", connectionPool));
    }

    @Test
    void invalidUserNameLogin() throws CustomException
    {
        assertThrows(CustomException.class, () -> UserFacade.login("bob", "1234", connectionPool));
    }

    @Test
    void createUser() throws CustomException {
        User newUser = UserFacade.createUser("jill", "1234", "user", connectionPool);
        User logInUser = UserFacade.login("jill", "1234", connectionPool);
        User expectedUser = new User("jill", "1234", "user");
        assertEquals(expectedUser, newUser);
        assertEquals(expectedUser, logInUser);
    }
}