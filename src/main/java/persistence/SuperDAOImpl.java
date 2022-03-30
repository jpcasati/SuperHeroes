/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import data.SuperBean;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author jpcasati
 */
public class SuperDAOImpl implements SuperDAO {
    
    // This information should be coming from a Properties file
    private final static String URL = "jdbc:mysql://localhost:3306/SUPERHERO?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final static String USER = "paymaster";
    private final static String PASSWORD = "finance";

    /**
     * Retrieve one record from the given table based on the primary key
     *
     * @param id
     * @return The SuperBean object
     * @throws java.sql.SQLException
     */
    @Override
    public SuperBean findID(int id) throws SQLException {

        // Diagnostic
        System.out.println("findID");

        SuperBean superData = new SuperBean();

        String selectQuery = "SELECT ID, FULLNAME, SUPERNAME, WAGE FROM SALARY WHERE ID = ?";

        // Using try with resources
        // Class that implement the Closable interface created in the
        // parenthesis () will be closed when the block ends.
        try ( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); // You must use PreparedStatements to guard against SQL
                // Injection
                  PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            // Only object creation statements can be in the parenthesis so
            // first try-with-resources block ends
            pStatement.setInt(1, id);
            // A new try-with-resources block for creating the ResultSet object
            // begins
            try ( ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {
                    superData = createSuperData(resultSet);
                }
            }
        }
        return superData;
    }

    /**
     * This method adds a SuperBean object as a record to the database.The
     * column list does not include ID as this is an auto increment value in the
     * table.
     *
     * @param superData
     * @return The number of records created, should always be 1
     * @throws SQLException
     */
    @Override
    public int create(SuperBean superData) throws SQLException {

        // Diagnostic
        System.out.println("create");

        int recordsCreated;
        String createQuery = "INSERT INTO SALARY (FULLNAME, SUPERNAME, WAGE ) VALUES (?,?,?)";

        // Connection is only open for the operation and then immediately closed
        try ( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); 
                // Using a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against
                // SQL Injection
                  PreparedStatement ps = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);) {
            fillPreparedStatementForSuperHeroes(ps, superData);
            recordsCreated = ps.executeUpdate();

            // Retrieve generated primary key value and assign to bean
            try ( ResultSet rs = ps.getGeneratedKeys();) {
                int recordNum = -1;
                if (rs.next()) {
                    recordNum = rs.getInt(1);
                }
                superData.setId(recordNum);
            }
        }
        return recordsCreated;
    }

    /**
     * This method will update all the fields of a record except ID. Usually
     * updates are tied to specific fields and so only those fields need appear
     * in the SQL statement.
     *
     * @param superData
     * @return The number of records updated, should be 0 or 1
     * @throws SQLException
     *
     */
    @Override
    public int update(SuperBean superData) throws SQLException {

        // Diagnostic
        System.out.println("update");

        int recordsUpdated;

        String updateQuery = "UPDATE SALARY SET FULLNAME = ?, SUPERNAME = ?, WAGE = ? WHERE ID = ?";

        // Connection is only open for the operation and then immediately closed
        try ( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); 
                // You must use a prepared statement to handle the conversion
                // of special characters in the SQL statement and guard against
                // SQL Injection
                  PreparedStatement ps = connection.prepareStatement(updateQuery);) {
            fillPreparedStatementForSuperHeroes(ps, superData);
            // The fillPreparedStatementForSuperHeroes does not set the ID
            ps.setString(4, superData.getId()+"");

            recordsUpdated = ps.executeUpdate();
        }
        return recordsUpdated;
    }

    /**
     * This method deletes a single record based on the criteria of the primary
     * key field ID value. It should return either 0 meaning that there is no
     * record with that ID or 1 meaning a single record was deleted. If the
     * value is greater than 1 then something unexpected has happened. A
     * criteria other than ID may delete more than one record.
     *
     * @param id The primary key to use to identify the record that must be
     * deleted
     * @return The number of records deleted, should be 0 or 1
     * @throws SQLException
     */
    @Override
    public int delete(int id) throws SQLException {

        // Diagnostic
        System.out.println("delete");

        int recordsDeleted;

        String deleteQuery = "DELETE FROM SALARY WHERE ID = ?";

        // Connection is only open for the operation and then immediately closed
        try ( Connection connection = DriverManager.getConnection(URL, USER, PASSWORD); 
                  // You must use PreparedStatements to guard against SQL Injection
                  PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, id);
            recordsDeleted = ps.executeUpdate();
        }
        return recordsDeleted;

    }

    /**
     * Private method that creates an object of type SuperBean from the current
     * record in the ResultSet
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    private SuperBean createSuperData(ResultSet resultSet) throws SQLException {
        SuperBean superData = new SuperBean();
        superData.setId(resultSet.getInt("ID"));
        superData.setFullName(resultSet.getString("FULLNAME"));
        superData.setSuperName(resultSet.getString("SUPERNAME"));
        superData.setWage(resultSet.getBigDecimal("WAGE"));
        return superData;
    }

    /**
     * Add the fields from a SuperBean object to the PreparedString on behalf of
     * the create/update method
     *
     * @param ps
     * @param superData
     * @throws SQLException
     */
    private void fillPreparedStatementForSuperHeroes(PreparedStatement ps, SuperBean superData) throws SQLException {
        ps.setString(1, superData.getFullName());
        ps.setString(2, superData.getSuperName());
        ps.setBigDecimal(3, superData.getWage());
    }

    
}
