/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import data.SuperBean;
import java.sql.SQLException;

/**
 *
 * @author jpcasati
 */
public interface SuperDAO {
    
    // Create
    public int create(SuperBean superData) throws SQLException;

    // Read
    public SuperBean findID(int id) throws SQLException;

    // Update
    public int update(SuperBean superData) throws SQLException;

    // Delete
    public int delete(int ID) throws SQLException;

    
}
