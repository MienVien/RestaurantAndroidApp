package icon.melb.melbicon.kitchen;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class KitchenOrder implements Serializable{
    private int dishQty;
    private Date orderDateTime;
    private String customerStatus;
    private String kitchenStatus;
    private String orderNotes;
    private List<OrderItem> orderItemList;

    public KitchenOrder() {
        this.dishQty = 1;
        this.orderDateTime = Calendar.getInstance().getTime();
        orderItemList = new ArrayList<>();
        orderNotes = "";
        customerStatus = "IN PROGRESS";
        kitchenStatus = "IN PROGRESS";
    }

    public KitchenOrder(int dishQty, List<OrderItem> orderMenuItemList){
        this.dishQty = dishQty; //Total Items ordered per Table
        this.orderDateTime = Calendar.getInstance().getTime();
        this.orderItemList = orderMenuItemList;
        customerStatus = "IN PROGRESS";
        kitchenStatus = "RECEIVED";
    }
    public KitchenOrder(int dishQty, String orderNotes, List<Item> orderMenuItemList){
        this.dishQty = dishQty; //Total Items ordered per Table
        this.orderDateTime = Calendar.getInstance().getTime();
        this.orderItemList = orderItemList;
        this.orderNotes = orderNotes;
        customerStatus = "IN PROGRESS";
        kitchenStatus = "RECEIVED";
    }

    public int getDishQty(){
        return dishQty;
    }
    public Date getOrderTimeDate(){
        return orderDateTime;
    }
    public String getCustomerStatus(){
        return customerStatus;
    }
    public String getKitchenStatus() {
        return kitchenStatus;
    }
    public List<OrderItem> getOrderItemList() {
        return this.orderItemList;
    }

    public double getTotalPriceOrder() {
        double total = 0;
        for (OrderItem orderItem:orderItemList) {
            total += orderItem.getTotal();
        }
        return total;
    }

    public void changeCustomerStatus( ){
        if(this.customerStatus.equalsIgnoreCase("RECEIVED")){ //Order just received
            this.customerStatus = "COOKING";
        }
        else if(this.customerStatus.equalsIgnoreCase("COOKING") ){ //Order is "On Progress"/"Cooking"
            this.customerStatus = "COMPLETED";
        }
        else{ //Order is already completed
            //do nothing.
            //Just a trap
        }
    }

    public void changeKitchenStatus( ){
        if(this.kitchenStatus.equalsIgnoreCase("RECEIVED")){ //Order just received
            this.kitchenStatus = "COOKING";
        }
        else if(this.kitchenStatus.equalsIgnoreCase("COOKING") ){ //Order is "On Progress"/"Cooking"
            this.kitchenStatus = "COMPLETED";
        }
        else{ //Order is already completed
            //do nothing.
            //Just a trap
        }
    }

    public void addToOrder(OrderItem orderItem) {
        orderItemList.add(orderItem);
    }
}
