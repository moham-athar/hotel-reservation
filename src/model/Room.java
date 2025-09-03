package model;

public class Room implements IRoom{

    public String roomNumber;
    public Double price;
    public RoomType roomType;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public RoomType getRoomType() {
        return null;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Room number: " + roomNumber + ", price: " + price +
                ", type: " + roomType + ".";
    }


}
