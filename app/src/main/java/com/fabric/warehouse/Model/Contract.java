package com.fabric.warehouse.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 6193 on 2016/4/12.
 */
public class Contract {
    private String name;

    public Contract() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Contract> generateSampleList(){
        List<Contract> list = new ArrayList<>();
        for(int i=0; i < 30; i++){
            Contract contract = new Contract();
            contract.setName("Name - " + i);
            list.add(contract);
        }
        return list;
    }
}
