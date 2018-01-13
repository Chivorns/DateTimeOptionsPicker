package com.chivorn.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chivorn.datetimeoptionspicker.DateTimePickerView;
import com.chivorn.datetimeoptionspicker.OptionsPickerView;
import com.chivorn.datetimeoptionspicker.listener.CustomListener;
import com.chivorn.demo.bean.CardBean;
import com.chivorn.demo.bean.ProvinceBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    /*   private ArrayList<ArrayList<ArrayList<IPickerViewData>>> options3Items = new ArrayList<>();*/
    private Button btn_Time, btn_Options, btn_CustomOptions, btn_CustomTime, btn_no_linkage, btn_to_Fragment;

    private DateTimePickerView dateTimePickerView, dtpvCustomTime, dtpvCustomLunar;
    private OptionsPickerView dtpvOptions, dtpvCustomOptions, dtpvNoLinkOptions;
    private ArrayList<CardBean> cardItem = new ArrayList<>();

    private ArrayList<String> food = new ArrayList<>();
    private ArrayList<String> clothes = new ArrayList<>();
    private ArrayList<String> computer = new ArrayList<>();
    private Locale locale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //等数据加载完毕再初始化并显示Picker,以免还未加载完数据就显示,造成APP崩溃。
        getOptionData();

        initTimePicker();
        initCustomTimePicker();
        initLunarPicker();
        initOptionPicker();
        initCustomOptionPicker();
        initNoLinkOptionsPicker();

        btn_Time = (Button) findViewById(R.id.btn_Time);
        btn_Options = (Button) findViewById(R.id.btn_Options);
        btn_CustomOptions = (Button) findViewById(R.id.btn_CustomOptions);
        btn_CustomTime = (Button) findViewById(R.id.btn_CustomTime);
        btn_no_linkage = (Button) findViewById(R.id.btn_no_linkage);
        btn_to_Fragment = (Button) findViewById(R.id.btn_fragment);

        btn_Time.setOnClickListener(this);
        btn_Options.setOnClickListener(this);
        btn_CustomOptions.setOnClickListener(this);
        btn_CustomTime.setOnClickListener(this);
        btn_no_linkage.setOnClickListener(this);
        btn_to_Fragment.setOnClickListener(this);

        findViewById(R.id.btn_GotoJsonData).setOnClickListener(this);
        findViewById(R.id.btn_lunar).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_Time && dateTimePickerView != null) {
            // dateTimePickerView.setDate(Calendar.getInstance());
           /* dateTimePickerView.show(); //show timePicker*/
            dateTimePickerView.show(v);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
        } else if (v.getId() == R.id.btn_Options && dtpvOptions != null) {
            dtpvOptions.show(); //弹出条件选择器
        } else if (v.getId() == R.id.btn_CustomOptions && dtpvCustomOptions != null) {
            dtpvCustomOptions.show(); //弹出自定义条件选择器
        } else if (v.getId() == R.id.btn_CustomTime && dtpvCustomTime != null) {
            dtpvCustomTime.show(); //弹出自定义时间选择器
        } else if (v.getId() == R.id.btn_no_linkage && dtpvNoLinkOptions != null) {//不联动数据选择器
            dtpvNoLinkOptions.show();
        } else if (v.getId() == R.id.btn_GotoJsonData) {//跳转到 省市区解析示例页面
            startActivity(new Intent(MainActivity.this, JsonDataActivity.class));
        } else if (v.getId() == R.id.btn_fragment) {//跳转到 fragment
            startActivity(new Intent(MainActivity.this, FragmentTestActivity.class));
        } else if (v.getId() == R.id.btn_lunar) {
            dtpvCustomLunar.show();
        }
    }

    private void initLunarPicker() {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        dtpvCustomLunar = new DateTimePickerView.Builder(this, new DateTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                Toast.makeText(MainActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.datetimeoptionspicker_custom_lunar, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomLunar.returnData();
                                dtpvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomLunar.dismiss();
                            }
                        });
                        //公农历切换
                        CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
                        cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                dtpvCustomLunar.setLunarCalendar(!dtpvCustomLunar.isLunarCalendar());
                                //自适应宽
                                setTimePickerChildWeight(v, 0.8f, isChecked ? 1f : 1.1f);
                            }
                        });

                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timepicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timepicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timepicker.getChildCount(); i++) {
                            View childAt = timepicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.RED)
                .build();
    }


    private void initTimePicker() {
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 0, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 11, 28);
        //时间选择器
        dateTimePickerView = new DateTimePickerView.Builder(this, new DateTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                Button btn = (Button) v;
                btn.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
//                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
    }

    private void initCustomTimePicker() {

        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2014, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2027, 2, 28);
        //时间选择器 ，自定义布局
        dtpvCustomTime = new DateTimePickerView.Builder(this, new DateTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                btn_CustomTime.setText(getTime(date));
            }
        })
                /*.setType(DateTimePickerView.Type.ALL)//default is all
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setTitleColor(Color.BLACK)
               /*.setDividerColor(Color.WHITE)//设置分割线的颜色
                .setTextColorCenter(Color.LTGRAY)//设置选中项的颜色
                .setLineSpacingMultiplier(1.6f)//设置两横线之间的间隔倍数
                .setTitleBgColor(Color.DKGRAY)//标题背景颜色 Night mode
                .setBgColor(Color.BLACK)//滚轮背景颜色 Night mode
                .setSubmitColor(Color.WHITE)
                .setCancelColor(Color.WHITE)*/
               /*.gravity(Gravity.RIGHT)// default is center*/
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.datetimeoptionspicker_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomTime.returnData();
                                dtpvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setContentSize(18)
                .setType(new boolean[]{false, false, false, true, true, true})
                .setLabel("y", "m", "d", "h", "min", "s")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();

    }

    private void initOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        getCardData();
        getNoLinkData();

        //选项1
        options1Items.add(new ProvinceBean(0, "iPhone", "Description", "Other"));
        options1Items.add(new ProvinceBean(1, "Samsung", "Description", "Other"));
        options1Items.add(new ProvinceBean(2, "OPPO", "Description", "Other"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("4S");
        options2Items_01.add("5");
        options2Items_01.add("6");
        options2Items_01.add("6 Plus");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("Galaxy Note 4");
        options2Items_02.add("Galaxy J2 Pro");
        options2Items_02.add("Galaxy A8+");
        options2Items_02.add("Galaxy J7+");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("A71");
        options2Items_03.add("F3");
        options2Items_03.add("F3 Plus");
        options2Items_03.add("A77");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        /*--------数据源添加完毕---------*/
    }


    private void initOptionPicker() {//条件选择器初始化

        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */

        dtpvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(options2)
                       /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                btn_Options.setText(tx);
            }
        })
                .setTitleText("Select Product")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x66000000) //设置外部遮罩颜色
                .build();

        //dtpvOptions.setSelectOptions(1,1);
        /*dtpvOptions.setPicker(options1Items);//一级选择器*/
        dtpvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*dtpvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    private void initCustomOptionPicker() {//条件选择器初始化，自定义布局
        /**
         * @description
         *
         * 注意事项：
         * 自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针。
         * 具体可参考demo 里面的两个自定义layout布局。
         */
        dtpvCustomOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = cardItem.get(options1).getPickerViewText();
                btn_CustomOptions.setText(tx);
            }
        })
                .setLayoutRes(R.layout.datetimeoptionspicker_custom_options, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        final TextView tvAdd = (TextView) v.findViewById(R.id.tv_add);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomOptions.returnData();
                                dtpvCustomOptions.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomOptions.dismiss();
                            }
                        });

                        tvAdd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getCardData();
                                dtpvCustomOptions.setPicker(cardItem);
                            }
                        });

                    }
                })
                .isDialog(true)
                .build();

        dtpvCustomOptions.setPicker(cardItem);//添加数据

    }

    private void initNoLinkOptionsPicker() {// 不联动的多级选项
        dtpvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = "food:" + food.get(options1)
                        + "\nclothes:" + clothes.get(options2)
                        + "\ncomputer:" + computer.get(options3);

                Toast.makeText(MainActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        }).build();
        dtpvNoLinkOptions.setNPicker(food, clothes, computer);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void getOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

        getCardData();
        getNoLinkData();

        //选项1
        options1Items.add(new ProvinceBean(0, "iPhone", "Description", "Other"));
        options1Items.add(new ProvinceBean(1, "Samsung", "Description", "Other"));
        options1Items.add(new ProvinceBean(2, "OPPO", "Description", "Other"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("4S");
        options2Items_01.add("5");
        options2Items_01.add("6");
        options2Items_01.add("6 Plus");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("Galaxy Note 4");
        options2Items_02.add("Galaxy J2 Pro");
        options2Items_02.add("Galaxy A8+");
        options2Items_02.add("Galaxy J7+");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("A71");
        options2Items_03.add("F3");
        options2Items_03.add("F3 Plus");
        options2Items_03.add("A77");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        /*--------数据源添加完毕---------*/
    }

    int currentItemNum = 1;

    private void getCardData() {
        int itemCount = currentItemNum + 5;
        for (int i = currentItemNum; i < itemCount; i++) {
            cardItem.add(new CardBean(i, "No.ABC " + i));
            currentItemNum++;
        }

        for (int i = 0; i < cardItem.size(); i++) {
            if (cardItem.get(i).getCardNo().length() > 9) {
                String str_item = cardItem.get(i).getCardNo().substring(0, 9) + "...";
                cardItem.get(i).setCardNo(str_item);
            }
        }
    }

    private void getNoLinkData() {
        food.add("KFC");
        food.add("MacDonald");
        food.add("Pizza hut");

        clothes.add("Nike");
        clothes.add("Adidas");
        clothes.add("Anima");

        computer.add("ASUS");
        computer.add("Lenovo");
        computer.add("Apple");
        computer.add("HP");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_en:
                locale = new Locale("en");
                Locale.setDefault(locale);
                return true;
            case R.id.action_kh:
                locale = new Locale("km");
                Locale.setDefault(locale);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
