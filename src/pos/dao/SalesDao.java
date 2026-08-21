/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pos.model.Sales;
import pos.service.DaoService;
import pos.util.DbUtil;
import pos.util.SalesUtil;

/**
 *
 * @author Admin
 */
public class SalesDao {

    DbUtil db = new DbUtil();
    PreparedStatement ps;
    ResultSet rs;
    String sql;

    SalesUtil su = new SalesUtil();

    public SalesDao() {
    }

    public int save(Sales e) {

        int salesId = 0;

        sql = "INSERT INTO sales(invoice_no,sales_date,customer_cell,total_amount) VALUES(?,?,?,?)";

        try {
            ps = db.getCon().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, e.getInvoiceNo());
            ps.setDate(2, e.getSqlsalesDate());
            ps.setString(3, e.getCustomerCell());
            ps.setDouble(4, e.getTotalAmount());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                salesId = rs.getInt(1);
            }

            ps.close();
            db.getCon().close();

        } catch (SQLException ex) {
            Logger.getLogger(SalesDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return salesId;


    }

    

}
