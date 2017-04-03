package com.example.user.emloyeelogin;

/**
 * Created by Thomas on 03/04/2017.
 */

public class Employee
{
    private String name;
    private boolean checkedIn;

    Employee(String name, String checkedIn)
    {
        this.name = name;

        if (checkedIn.equals("0"))
        {
            this.checkedIn = false;
        }
        else
        {
            this.checkedIn = true;
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isCheckedIn()
    {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn)
    {
        this.checkedIn = checkedIn;
    }
}
