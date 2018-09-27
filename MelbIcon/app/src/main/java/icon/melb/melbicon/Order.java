package icon.melb.melbicon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {
    private int dishQty;
    private Date orderDateTime;
    private String cutomerStatus;
    private String kitchenStatus;
    private String orderNotes;
    private List<OrderItem> orderItemList;

    public Order() {
        this.dishQty = 1;
        this.orderDateTime = Calendar.getInstance().getTime();
        orderItemList = new ArrayList<>();
        orderNotes = "";
        cutomerStatus = "IN PROGRESS";
        kitchenStatus = "IN PROGRESS";
    }

    public Order(int dishQty, List<MenuItem> orderMenuItemList){
        this.dishQty = dishQty; //Total Items ordered per Table
        this.orderDateTime = Calendar.getInstance().getTime();
        this.orderItemList = orderItemList;
        cutomerStatus = "IN PROGRESS";
        kitchenStatus = "RECEIVED";
    }
    public Order(int dishQty, String orderNotes, List<MenuItem> orderMenuItemList){
        this.dishQty = dishQty; //Total Items ordered per Table
        this.orderDateTime = Calendar.getInstance().getTime();
        this.orderItemList = orderItemList;
        this.orderNotes = orderNotes;
        cutomerStatus = "IN PROGRESS";
        kitchenStatus = "RECEIVED";
    }

    public int getDishQty(){
        return dishQty;
    }
    public Date getOrderTimeDate(){
        return orderDateTime;
    }
    public String getCustomerStatus(){
        return cutomerStatus;
    }
    public String getKitchenStatus() {
        return kitchenStatus;
    }
    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }
    public double getTotalPriceOrder() {
        double total = 0;
        for (OrderItem orderItem:orderItemList) {
            total += orderItem.getTotal();
        }
        return total;
    }

    public void changeCustomerStatus(String cutomerStatus){
    this.cutomerStatus = "COMPLETED";
    }

    public void changeKitchenStatus(String kitchenStatus){
        if(this.kitchenStatus.equalsIgnoreCase("RECEIVED")){
            this.kitchenStatus = "COOKING";
        }
        else{
            this.kitchenStatus = "COMPLETED";
        }
    }

    public void addToOrder(OrderItem orderItem) {
        orderItemList.add(orderItem);
    }
}
