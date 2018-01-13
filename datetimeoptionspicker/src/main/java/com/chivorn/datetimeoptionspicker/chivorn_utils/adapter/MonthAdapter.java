package com.chivorn.datetimeoptionspicker.chivorn_utils.adapter;

import com.chivorn.datetimeoptionspicker.adapter.WheelAdapter;
import com.chivorn.datetimeoptionspicker.chivorn_utils.model.MonthModel;

/**
 * Created by Hum Vorn on 1/3/2018.
 */

public class MonthAdapter implements WheelAdapter {
    @Override
    public int getItemsCount() {
        return MonthModel.getMonth().size();
    }

    @Override

    public Object getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            return MonthModel.getMonth().get(index);
        }
        return "";
    }

    @Override
    public int indexOf(Object obj) {
        for (int i = 0; i < getItemsCount(); i++) {
            if (obj.equals(MonthModel.getMonth().get(i))) {
                return i;
            }
        }
        return -1;
    }
}
