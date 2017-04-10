package com.example.mushy.employeelogin;

import android.widget.BaseAdapter;

/**
 * Created by Mushy on 4/10/2017.
 */

public class Product
{
    private int id;
    private String name;
    private int price;
    private String description;

    // Constructor
    public Product(int id, String name, int price, String description)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }


    // Getter Setter

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}


