package com.chivorn.datetimeoptionspicker.chivorn_utils.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by Hum Vorn on 1/3/2018.
 */

public class MonthModel {
    private static final String[] EnMonth =
            {
                    "January",
                    "February",
                    "March",
                    "April",
                    "May",
                    "June",
                    "July",
                    "August",
                    "September",
                    "October",
                    "November",
                    "December"
            };

    private static final String[] KhMonth =
            {
                    "មករា",
                    "កម្ភៈ",
                    "មិនា",
                    "មេសា",
                    "ឧសភា",
                    "មិថុនា",
                    "កក្កដា",
                    "សីហា",
                    "កញ្ញា",
                    "តុលា",
                    "វិច្ឆិកា",
                    "ធ្នូ"
            };


    public static List<String> getMonth() {
        List<String> monthList = new ArrayList<>();
        Locale locale = Locale.getDefault();

        if (locale.getLanguage().toLowerCase().equals("kh") || locale.getLanguage().toLowerCase().equals("km")) {
            Collections.addAll(monthList, KhMonth);
        } else {
            Collections.addAll(monthList, EnMonth);
        }
        //List<String> month_list_from_xml = Arrays.asList(context.getResources().getStringArray(R.array.month_list));
        return monthList;
    }
}
