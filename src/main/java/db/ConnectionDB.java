package db;

import java.sql.*;

public class ConnectionDB {
    private Connection currentConection;

    public ConnectionDB(String linkDB, String controllerDB, String userDB, String password) {
        this.currentConection = openConexion(linkDB,  controllerDB,  userDB,  password);
    }

    private Connection openConexion(String linkDB, String controllerDB, String userDB, String password) {
        Connection connection = null;
        try {
            Class.forName(controllerDB).newInstance();
            connection = DriverManager.getConnection(linkDB, userDB, password);
        } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConexion() {
        try {
            this.currentConection.close();
        } catch (SQLException e) {

        }
    }

    public ResultSet runSqlQuery(String query) throws SQLException {
        final Statement statement;
        statement = this.currentConection.createStatement();
        return statement.executeQuery(query);
    }

    public Boolean executeQuery(String query) throws SQLException {
        Statement statement = this.currentConection.createStatement();
        //currentConection.
        return statement.execute(query);
    }

    public PreparedStatement getPreparedStatement(String query) throws SQLException {
        return this.currentConection.prepareStatement(query);
    }

    public PreparedStatement getPreparedStatement(String query, int flags) throws SQLException {
        return this.currentConection.prepareStatement(query, flags);
    }

    /**
     * Initilize a transaction in database
     *
     * @throws SQLException If initialization fails
     */
    public void initTransaction() throws SQLException {
        this.currentConection.setAutoCommit(false);
    }

    /**
     * Finish a transaction in database and commit changes
     *
     * @throws SQLException If a rollback fails
     */
    public void commitTransaction() throws SQLException {
        try {
            this.currentConection.commit();
        } catch (SQLException e) {
            if (this.currentConection != null) {
                this.currentConection.rollback();
            }
        } finally {
            this.currentConection.setAutoCommit(false);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        this.closeConexion();
        super.finalize();
    }
}
