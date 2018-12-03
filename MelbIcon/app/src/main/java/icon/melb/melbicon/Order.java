package icon.melb.melbicon;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {
    private int dishQty;
    private Date orderDateTime;
    private String customerStatus;
    private String kitchenStatus;
    private List<OrderItem> combinedOrderItemList;
    protected static int orderCount = 0;
    private int orderID;
    private int tableNo;

    public Order() {
        this.dishQty = 1;
        this.orderDateTime = Calendar.getInstance().getTime();
        combinedOrderItemList = new ArrayList<>();
        customerStatus = "IN PROGRESS";
        kitchenStatus = "RECEIVED";
    }

    public Order(int dishQty, List<OrderItem> orderMenuItemList){
        //this.dishQty = dishQty; //Total Items ordered per Table
        this.orderDateTime = Calendar.getInstance().getTime();
        this.combinedOrderItemList = combinedOrderItemList;
        customerStatus = "IN PROGRESS";
        kitchenStatus = "RECEIVED";
    }

    public int getDishQty(){
        return combinedOrderItemList.size();
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
        return combinedOrderItemList;
    }
    public void addToOrderItemList(OrderItem orderItem) {
        combinedOrderItemList.add(orderItem);
    }

    public double getTotalPriceOrder() {
        double total = 0;

        for (OrderItem orderItem:combinedOrderItemList) {
            total += orderItem.getTotal();
        }
        return total;
    }

    public void changeCustomerStatus(String cutomerStatus){
    this.customerStatus = "COMPLETED";
    }

    public void changeKitchenStatus(String kitchenStatus){
        if(this.kitchenStatus.equalsIgnoreCase("RECEIVED")){
            this.kitchenStatus = "COOKING";
        }
        else{
            this.kitchenStatus = "COMPLETED";
        }
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getOrderID() {
        return orderID;
    }

}
