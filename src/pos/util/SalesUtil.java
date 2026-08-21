package pos.util;

public class SalesUtil {

    public double getTotalSalesPrice(double unitPrice, double quantity) {

        return unitPrice * quantity;
    }
    
    public double getDiscountAmount(double totalPrice, double discountrate) {

        return (totalPrice * discountrate / 100);
    }
    
    public double getActualPrice(double totalPrice, double discountAmount) {

        return totalPrice - discountAmount;
    }
   

}
