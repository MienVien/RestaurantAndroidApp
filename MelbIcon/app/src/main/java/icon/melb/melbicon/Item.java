package icon.melb.melbicon;

public class Item {
    private String title;
    private double price;
    private double total;
    private int qty;

    public Item(String title, double price, int qty){
        this.title = title;
        this.price = price;
        this.qty = qty;
        total = price*qty;
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
}
