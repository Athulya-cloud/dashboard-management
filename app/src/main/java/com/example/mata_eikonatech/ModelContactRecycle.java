package com.example.mata_eikonatech;

public class ModelContactRecycle {
    String name, number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ModelContactRecycle(String number) {
        this.number = number;
        this.name = name;
    }
}
