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
    private String eid;
    private String job;
    private String parttime;
    private String isWorkingDay;

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

    Employee (int inAppID, String name, String eid, String job, String time1, String time2, String isWorkingDay)
    {
        this.eid = eid;
        this.name = name;
        this.job = job;
        this.time1 = time1;
        this.time2 = time2;
        this.isWorkingDay = isWorkingDay;
        this.inAppID = inAppID;
    }

    public String getIsWorkingDay() {
        return isWorkingDay;
    }

    public void setIsWorkingDay(String isWorkingDay) {
        this.isWorkingDay = isWorkingDay;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getParttime() {
        return parttime;
    }

    public void setParttime(String parttime) {
        this.parttime = parttime;
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
