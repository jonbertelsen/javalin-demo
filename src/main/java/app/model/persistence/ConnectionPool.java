package app.model.persistence;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Singleton pattern applied to handling a Hikari ConnectionPool
 */
public class ConnectionPool {
    // TODO: Change default access credentials for MySql server as needed below:
    private static final String DEFAULT_USER = "dev";
    private static final String DEFAULT_PASSWORD = "ax2";
    private static final String DEFAULT_URL = "jdbc:postgresql://localhost:5432/startcode?currentSchema=public";

    public static ConnectionPool instance = null;
    public static HikariDataSource ds = null;

    /***
     * Empty and private contructor due to single pattern. Use getInstance methods to
     * instantiate and get a connection pool.
     */
    private ConnectionPool() {
    }

    /***
     * Getting a singleton instance of a Hikari Connection Pool with default credentials and
     * connection string hardcoded in class
     * @return
     */
    public static ConnectionPool getInstance()
    {
        return getInstance(DEFAULT_USER, DEFAULT_PASSWORD, DEFAULT_URL);
    }

    /***
     * Getting a singleon instance of a Hikari Connection Pool with specific credentials
     * and connection string.
     * @param user for Postgresql database user
     * @param password for Postgresql database user
     * @param url connection string for postgresql database. Remember to add currentSchema to string
     * @return
     */
    public static ConnectionPool getInstance(String user, String password, String url) {
        if (instance == null) {
            if (System.getenv("DEPLOYED") != null) {
                ds = createHikariConnectionPool(
                        System.getenv("JDBC_USER"),
                        System.getenv("JDBC_PASSWORD"),
                        System.getenv("JDBC_CONNECTION_STRING_STARTCODE"));
            } else {
                ds = createHikariConnectionPool(user, password, url);
            }
            instance = new ConnectionPool();
        }
        return instance;
    }

    /***
     * Getting a live connection from a Hikari Connection Pool
     * @return a database connection to be used in sql requests
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        Logger.getLogger("web").log(Level.INFO, ": get data connection");
        return ds.getConnection();
    }

    /***
     * Closing a Hikari Connection Pool after use.
     */
    public void close() {
        Logger.getLogger("web").log(Level.INFO, "Shutting down connection pool");
        ds.close();
    }

    /***
     * Configuring a Hikari DataSource ConnectionPool. Default pool size is 3.
     * @param user for Postgresql database user
     * @param password for Postgresql database user
     * @param url connection string for postgresql database. Remember to add currentSchema to string
     * @return a Hikari DataSource
     */
    private static HikariDataSource createHikariConnectionPool(String user, String password, String url) {
        Logger.getLogger("web").log(Level.INFO,
                String.format("Connection Pool created for: (%s, %s, %s)", user, password, url));
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(3);
        config.setPoolName("Postgresql Pool");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

}