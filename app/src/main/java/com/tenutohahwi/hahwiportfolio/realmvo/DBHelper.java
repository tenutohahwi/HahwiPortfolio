package com.tenutohahwi.hahwiportfolio.realmvo;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

public class DBHelper
{
    private static DBHelper ourInstance = new DBHelper();

    public static DBHelper getInstance()
    {
        return ourInstance;
    }

    Realm realm;

    public DBHelper()
    {
        realm = Realm.getDefaultInstance();
    }

    public Realm getRealmInstance()
    {
        return realm;
    }

    public void insertEatFood( String barcode )
    {
        EatFood eatFood = new EatFood();
        eatFood.setIndex(getAutoIncrementIndexEatFood(eatFood));
        eatFood.setBarcode(barcode);
        SimpleDateFormat dt           = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String           sCurrentDate = dt.format(new Date());
        eatFood.setDatetime(sCurrentDate);

        realm.beginTransaction();
        realm.copyToRealm(eatFood);
        realm.commitTransaction();

    }


    public RealmResults<EatFood> getEatFoodWithIndex( int index )
    {
        return realm.where(EatFood.class).equalTo("index", index).findAll();
    }

    // index에 해당하는 정보를 변경
    public void editEatFoodIndex( int index, String barcode, String datetime )
    {
        RealmResults<EatFood> result = getEatFoodWithIndex(index);

        if (result.isEmpty())
        {
            return;
        }

        EatFood eatFood = result.get(0);

        realm.beginTransaction();
        eatFood.setBarcode(barcode);
        eatFood.setDatetime(datetime);
        realm.commitTransaction();

    }

    // index 값을 받아서 해당하는 member를 삭제
    public void deleteEatFoodWithIndex( int index )
    {
        RealmResults<EatFood> result = getEatFoodWithIndex(index);

        if (result.isEmpty())
        {
            return;
        }

        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();

    }

    public List<EatFood> getEatFood()
    {
        List<EatFood> eatFoodList = realm.where(EatFood.class).findAll().sort("index", Sort.DESCENDING);
        return eatFoodList;
    }

    public List<EatFood> getEatFoodMonth(String yearMonth)
    {
        //earMonth+"-01" , yearMonth+"-31"
        RealmQuery<EatFood> eatFoodRealmQuery = realm.where(EatFood.class).contains("datetime", yearMonth);
        List<EatFood> eatFoodList = eatFoodRealmQuery.findAll();

        return eatFoodList;
    }
    
    
    

    // Realm에 Auto Increment가 없기 때문에 수동으로 처리해주는 부분
    private int getAutoIncrementIndexEatFood( Object object )
    {
        int    nextIndex;
        Number currentIndex = null;

        // 질의 부분에서 instance를 확인하여 들어가는 부분
        // index에서 가장 큰 값을 구해서, 1을 더해준다.
        if (object instanceof EatFood)
        {
            currentIndex = realm.where(EatFood.class).max("index");
        }

        if (currentIndex == null)
        {
            nextIndex = 0;
        }
        else
        {
            nextIndex = currentIndex.intValue() + 1;
        }

        return nextIndex;
    }


    public void insertBarCode( String barcode, int cal, String capacity, String company, String name, String type )
    {
        BarCode barCode = new BarCode();
        barCode.setIndex(getAutoIncrementIndexBarCode(barCode));
        barCode.setBarcode(barcode);
        barCode.setCal(cal);
        barCode.setCapacity(capacity);
        barCode.setCompany(company);
        barCode.setName(name);
        barCode.setType(type);

        realm.beginTransaction();
        realm.copyToRealm(barCode);
        realm.commitTransaction();

    }


    public RealmResults<BarCode> getBarCodeWithIndex( int index )
    {
        return realm.where(BarCode.class).equalTo("index", index).findAll();
    }

    // index에 해당하는 정보를 변경
    public void editBarCodeIndex( int index, String barcode, String datetime )
    {
        RealmResults<BarCode> result = getBarCodeWithIndex(index);

        if (result.isEmpty())
        {
            return;
        }

        BarCode BarCode = result.get(0);

        realm.beginTransaction();
        BarCode.setBarcode(barcode);
        realm.commitTransaction();
    }

    // index 값을 받아서 해당하는 member를 삭제
    public void deleteBarCodeWithIndex( int index )
    {
        RealmResults<BarCode> result = getBarCodeWithIndex(index);

        if (result.isEmpty())
        {
            return;
        }

        realm.beginTransaction();
        result.deleteAllFromRealm();
        realm.commitTransaction();

    }

    public List<BarCode> getBarCode()
    {
        List<BarCode> BarCodeList = realm.where(BarCode.class).findAll().sort("index", Sort.DESCENDING);
        return BarCodeList;
    }

    public List<BarCode> getBarCodeMonth(String yearMonth)
    {
        //earMonth+"-01" , yearMonth+"-31"
        RealmQuery<BarCode> BarCodeRealmQuery = realm.where(BarCode.class).contains("datetime", yearMonth);
        List<BarCode> BarCodeList = BarCodeRealmQuery.findAll();

        return BarCodeList;
    }

    // Realm에 Auto Increment가 없기 때문에 수동으로 처리해주는 부분
    private int getAutoIncrementIndexBarCode( Object object )
    {
        int    nextIndex;
        Number currentIndex = null;

        // 질의 부분에서 instance를 확인하여 들어가는 부분
        // index에서 가장 큰 값을 구해서, 1을 더해준다.
        if (object instanceof BarCode)
        {
            currentIndex = realm.where(BarCode.class).max("index");
        }

        if (currentIndex == null)
        {
            nextIndex = 0;
        }
        else
        {
            nextIndex = currentIndex.intValue() + 1;
        }

        return nextIndex;
    }
}
