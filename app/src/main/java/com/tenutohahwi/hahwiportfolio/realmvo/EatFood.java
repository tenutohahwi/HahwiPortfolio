package com.tenutohahwi.hahwiportfolio.realmvo;

import java.lang.reflect.Member;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class EatFood extends RealmObject
{
    @PrimaryKey
    private int index;

    private String barcode;
    private String datetime;

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

    public String getDatetime()
    {
        return datetime;
    }

    public void setDatetime( String datetime )
    {
        this.datetime = datetime;
    }
}
