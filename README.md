
## DateTimeOptionsPicker
[ ![Download](https://api.bintray.com/packages/chivorn/maven/datetimeoptionspicker/images/download.svg) ](https://bintray.com/chivorn/maven/datetimeoptionspicker/_latestVersion)

This is a iOS imitaiton of the PickerView control, a time selector and option selector which has features as follows:
- DateTimePickerView: Support for the year, month, day, hour and other format.
- OptionsPickerView: Support one, two, three options, and can be set whether the linkage.

* Support three linkage
* Set whether the linkage
* Set the cycle mode
* Support custom layout
* Support the item's divider settings.
* Support item spacing settings
* The time selector supports start and end date settings.
* Support for "year, month, day, hour, minute, second", "province, city, district" and other options of the unit(label) display, hide and customize.
* Support custom text, color, text size and other attribute.
* Item text length is too long, the text will be automatically scaled to item length, to avoid displaying incomplete problem
* Support Dialog mode.
* Support for custom settings container.

## Original Library: [Android-PickerView](https://github.com/Bigkoo/Android-PickerView)

## New Feature:
* Support month name in Khmer and English

## Screenshot Demo

![1.KhmerDemo.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/1.KhmerDemo.gif)
![2.LunarDateTimePicker.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/2.LunarDateTimePicker.gif)
![3.DateTimePicker.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/3.DateTimePicker.gif)
![4.OptionsPicker.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/4.OptionsPicker.gif)
![5.CustomLayoutDateTimePicker.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/5.CustomLayoutDateTimePicker.gif)
![6.OptionsPicker(Non-Linkage).gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/6.OptionsPicker(Non-Linkage).gif)
![7.AreaDataParseSample.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/7.AreaDataParseSample.gif)
![8.CustomParentView.gif](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/preview/8.CustomParentView.gif)




## Precautions for use
* Note: When we set the start position of the time, we need to pay special attention to the setting of the month
* Cause: The Calendar component's internal month, starting from 0, that is, 0-11 for the January-December
* Wrong use cases: 
  startDate.set(2013,1,1);
  endDate.set(2020,12,1);
* Correct use case:
  startDate.set(2013,0,1);
  endDate.set(2020,11,1);
</br>

## **How to use：**

### 1.Add the dependency：
```gradle
implementation 'com.github.chivorns:datetimeoptionspicker:1.0.1'
```

### Maven
```
<dependency>
  <groupId>com.github.chivorns</groupId>
  <artifactId>datetimeoptionspicker</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
### 2.Add the following code in your Activity：

```java
    //DateTimePickerView
    dateTimePickerView = new DateTimePickerView.Builder(this, new DateTimePickerView.OnTimeSelectListener() {
        @Override
        public void onTimeSelect(Date date,View v) {//Callback
            yourTextView.setText(getTime(date));
        }
    })
            .build();
    dateTimePickerView.show();
```

```java
    //OptionsPickerView
    dtpvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
            ////Callback
            String tx = options1Items.get(options1).getPickerViewText()
                    + options2Items.get(options1).get(option2)
                    + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
            yourTextView.setText(tx);
        }
    }).build();

    //dtpvOptions.setPicker(options1Items);
    //dtpvOptions.setPicker(options1Items, options2Items);
    dtpvOptions.setPicker(options1Items, options2Items, options3Items);
    dtpvOptions.show();
```

### Notes
- when we start setting the date, we need to pay special attention.

- reason: the internal component of the Calendar adds 1 processing, which made the month's number of  count reduced one.
- error usage case:

>StartDate.set (2013,1,1);

>EndDate.set (2020,12,31);

- correct use case:

>StartDate.set (2013,0,1);

>EndDate.set (2020,11,31);
</br>


If the default style does not meet your expectations, You can also customize attributes to apply

### Customize Useage：
```java
 Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013,0,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2020,11,1);

        dateTimePickerView = new DateTimePickerView.Builder(this, new DateTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//callback
                yourTextView.setText(getTime(date));
            }
        })     
                .setType(new boolean[]{false, false, false, true, true, false})   // year-month-day-hour-min-sec
                .setCancelText("Cancel")
                .setSubmitText("Sure")
                .setContentSize(18)
                .setTitleSize(20)
                .setTitleText("Title")
                .setOutSideCancelable(false)// default is true
                .isCyclic(true)// default is false
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(0xFF666666)//night mode
                .setBgColor(0xFF333333)//night mode
                .setRangDate(startDate,endDate)
                .setLabel("year","month","day","hours","mins","seconds")
                .build();
```

```java
dtpvOptions = new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                ////Callback
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2)
                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                yourTextView.setText(tx);
            }
        })
                .setSubmitText("sure")
                .setCancelText("cancel")
                .setTitleText("title")
                .setSubCalSize(18)
                .setTitleSize(20)
                .setTitleColor(Color.BLACK)
                .setSubmitColor(Color.BLUE)
                .setCancelColor(Color.BLUE)
                .setTitleBgColor(0xFF666666)//night mode
                .setBgColor(0xFF444444)//night mode
                .setContentTextSize(18)
                .setLinkage(false)
                .isCenterLabel(false) //default is true , if you choose false , the label text will add to all item ContentText right
                .setLabels("province", "city", "district")
                .setCyclic(false, false, false)
                .setSelectOptions(0, 0, 0)  //default options
                .setOutSideCancelable(false)//dismiss， default is true
                .build();

        dtpvOptions.setPicker(options1Items, options2Items, options3Items);
```
### Customize Layout：
```java
  private void initCustomTimePicker() {
        // be careful：In the custom layout， the layout of the ID for optionspicker
        // or TimePicker and its child widget must not be modified,
        // otherwise  will be reported NullPointerException
        // For more details， Please refer to the two custom layouts in demo

        Calendar selectedDate = Calendar.getInstance();//System current time
        Calendar startDate = Calendar.getInstance();
        startDate.set(2013, 1, 23);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 2, 28);

        dtpvCustomTime = new DateTimePickerView.Builder(this, new DateTimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//call back
                btn_CustomTime.setText(getTime(date));
            }
        }).setType(new boolean[]{true, true, true, false, false, false})   // year-month-day-hour-min-sec
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dtpvCustomTime.returnData();
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
                .setDividerColor(Color.BLACK)
                .build();
    }
```

### If you need to set the non-linkage data：

```java
dtpvNoLinkOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

                String str = "food:"+food.get(options1)
                        +"\nclothes:"+clothes.get(options2)
                        +"\ncomputer:"+computer.get(options3);

                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        }).build();
        dtpvNoLinkOptions.setNPicker(food,clothes,computer);
        dtpvNoLinkOptions.show();
```

## For more detail, please refer to the Demo code, If there is still doubt about you, please [New Issue](https://github.com/Chivorns/DateTimeOptionsPicker/issues) 

###  [Here is demo code](https://github.com/Chivorns/DateTimeOptionsPicker/blob/master/app/src/main/java/com/chivorn/demo/MainActivity.java)

###  [Methods-and-parameters](https://github.com/Bigkoo/Android-PickerView/wiki/Methods-and-parameters)


## Thanks

- [WheelView](https://github.com/venshine/WheelView)
- [androidWheelView](https://github.com/weidongjian/androidWheelView/)

# License

```
Copyright 2014 Bigkoo
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
