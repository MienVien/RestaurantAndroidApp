package icon.melb.melbicon.kitchen;

import java.io.Serializable;

public class OrderItem implements Serializable{
    private String title;
    private double price;
    private double total;
    private int qty;
    private String notes;

    public OrderItem( String title, double price ){
        this( title, price, 1 );
    }

    public OrderItem(String title, double price, int qty){
        this.title = title;
        this.price = price;
        this.qty = qty;
        total = price*qty;
    }

    public OrderItem( String title, double price, int qty, String notes ){
        this( title, price, qty );
        this.notes = notes;
    }

    public String getNotes( ){
        if( notes != null )
            return "\t - Notes: " + notes;
        return "\n";
    }
    public String getTitle( ){
        return title;
    }

    public double getPrice( ){
        return price;
    }

    public int getQty( ){
        return qty;
    }

    public double getTotal( ){
        return total;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String toString() {
        return "Title: " + title
                +"\nPrice: " + price
                +"\nQTY: " + qty
                +"\nTotal: " + total;
    }
}
