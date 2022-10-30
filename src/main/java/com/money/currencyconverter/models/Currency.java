package com.money.currencyconverter.models;

import javax.persistence.Entity;
import javax.persistence.Id;
//соимость валюты в рублях, имя (String) будет ключом..
@Entity
public class Currency {
    @Id
    private String name;
    private double valueinRub;

    public Currency() {
    }
    public Currency(String name, double valueinRub) {
        this.name = name;
        this.valueinRub = valueinRub;
    }
    public String getName() {return name; }
    public void setName(String name) {this.name = name; }
    public double getValueinRub() {return valueinRub; }
    public void setValueinRub(double valueinRub) {this.valueinRub = valueinRub; }

    @Override
    public String toString() {
        return "Currency{" +
                "name='" + name + '\'' + ", valueinRub=" + valueinRub +
                '}';
    }


}
