package com.tenutohahwi.hahwiportfolio.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @author shyknight_m@naver.com
 * @class MathbankApp
 * @date 2017-06-27
 * @see 1.Height가 제대로 딱 맞게 표현되는 ListView
 */

public class HeightFitListView extends ListView {

    public HeightFitListView( Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeightFitListView( Context context) {
        super(context);
    }

    public HeightFitListView( Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}

