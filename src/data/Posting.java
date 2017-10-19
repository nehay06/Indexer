package data;

import java.util.ArrayList;
import java.util.List;

public class Posting {
    Integer ID;
    Integer count;
    List <Integer> positions = new ArrayList<>();

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Integer> getPositions() {
        return positions;
    }

    public void setPositions(List<Integer> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        StringBuilder sb =new StringBuilder();
        sb.append(getID()+",");
        sb.append(getCount()+",");
        sb.append(getPositions()+"\n \n");
        return sb.toString();
    }
}
