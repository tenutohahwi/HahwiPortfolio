package com.tenutohahwi.hahwiportfolio.realmvo;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class BarCode extends RealmObject
{
    @PrimaryKey
    private int index;

    private String barcode;
    private String capacity;
    private int cal;
    private String company;
    private String name;
    private String type;

    public int getIndex()
    {
        return index;
    }

    public void setIndex( int index )
    {
        this.index = index;
    }

    public String getBarcode()
    {
        return barcode;
    }

    public void setBarcode( String barcode )
    {
        this.barcode = barcode;
    }

    public String getCapacity()
    {
        return capacity;
    }

    public void setCapacity( String capacity )
    {
        this.capacity = capacity;
    }

    public int getCal()
    {
        return cal;
    }

    public void setCal( int cal )
    {
        this.cal = cal;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany( String company )
    {
        this.company = company;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getType()
    {
        return type;
    }

    public void setType( String type )
    {
        this.type = type;
    }
}
