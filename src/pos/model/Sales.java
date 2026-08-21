/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pos.model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Sales {
    
    private int id;
    private String invoiceNo;
    private Date salesDate;
    private String customerCell;
    private double totalAmount;

    public Sales() {
    }

    public Sales(int id, String invoiceNo, Date salesDate, String customerCell, double totalAmount) {
        this.id = id;
        this.invoiceNo = invoiceNo;
        this.salesDate = salesDate;
        this.customerCell = customerCell;
        this.totalAmount = totalAmount;
    }

    public Sales(String invoiceNo,  String customerCell, double totalAmount) {
        this.invoiceNo = invoiceNo;
        this.customerCell = customerCell;        
        this.totalAmount = totalAmount;
    }
    
    

    public java.sql.Date getSqlsalesDate() {

        return new java.sql.Date(new Date().getTime());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public String getCustomerCell() {
        return customerCell;
    }

    public void setCustomerCell(String customerCell) {
        this.customerCell = customerCell;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Sales{" + "id=" + id + ", invoiceNo=" + invoiceNo + ", salesDate=" + salesDate + ", customerCell=" + customerCell + ", totalAmount=" + totalAmount + '}';
    }
    
    


}
