package com.example.user.emloyeelogin;

/**
 * Created by Thomas on 03/04/2017.
 */

public class Employee
{
    private String name;
    private boolean checkedIn;
    private String time1;
    private String time2;
    private int inAppID;

    Employee(int inAppID, String name, String checkedIn, String time1, String time2)
    {
        this.inAppID = inAppID;
        this.name = name;
        this.time1 = time1;
        this.time2 = time2;

        if (checkedIn.equals("0"))
        {
            this.checkedIn = false;

        }
        else
        {
            this.checkedIn = true;
        }
    }

    public int getInAppID()
    {
        return inAppID;
    }

    public void setInAppID(int inAppID)
    {
        this.inAppID = inAppID;
    }

    public String getTime1()
    {
        return time1;
    }

    public void setTime1(String time1)
    {
        this.time1 = time1;
    }

    public String getTime2()
    {
        return time2;
    }

    public void setTime2(String time2)
    {
        this.time2 = time2;
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
