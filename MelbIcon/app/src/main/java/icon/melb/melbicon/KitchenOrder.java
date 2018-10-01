package icon.melb.melbicon.Kitchen;

public static class KitchenOrder {
    private int tableNum;
    private int numOfDish;
    private String orderReceiveTime;
    private String status;

    public KitchenOrder( int tableNum, int numOfDish, String orderReceiveTime, String status ){
        this.tableNum = tableNum;
        this.numOfDish = numOfDish;
        this.orderReceiveTime = orderReceiveTime;
        this.status = status;
    }

    public int getTableNum( ){
        return tableNum;
    }

    public int getNumOfDish( ){
        return numOfDish;
    }

    public String getOrderReceiveTime( ){
        return orderReceiveTime;
    }

    public String getStatus( ){
        return status;
    }

    public void setStatus( String newStatus ){
        status = newStatus;
    }
}
