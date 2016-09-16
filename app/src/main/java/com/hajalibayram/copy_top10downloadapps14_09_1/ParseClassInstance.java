package com.hajalibayram.copy_top10downloadapps14_09_1;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

/**
 * Created by User on 9/15/2016.
 */
public class ParseClassInstance {

    private String xmlData;
    private ArrayList<ClassInstance> classInstances;// classInstance obyektlerini liste yazir
    //getteri lazimdi

    public ArrayList<ClassInstance> getClassInstances(){
        return classInstances;
    }

    /* Constructor*/
    public ParseClassInstance(String xmlData){
        this.xmlData = xmlData;
        classInstances = new ArrayList<>();
    }
    /*END*/

    /**TEMPLATE*/
    public boolean process(){//programin esas hissesi
//        boolean status = true;  lazimsizdi
        ClassInstance currentRecord = null;// hal hazirki recordu yadda saxlayan ClassInstance obyekti ucun yer ayrilir
        boolean inEntry = false; // lazim olan faylin icinde olub olmadigimizi aydinlasdirir
        String textValue = "";// bize lazim olan texti yadda saxlamaq ucun ayrilan String

        /* exception handling den istifade gereklidir */
        try {
            /* Burdan o terefe copy-paste*/
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(this.xmlData));
            int eventType = xmlPullParser.getEventType();
            while (eventType!=XmlPullParser.END_DOCUMENT){
                String tagName = xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("entry")){//hansi tag lazimdi onu entry yerine yaziriq
                            inEntry = true;// entrinin icinde oldugumuzu bildirir. Bele ki lazim olan yerdeyik
                            currentRecord = new ClassInstance();// yeni record yaradir
                        }
                        break;
                    case XmlPullParser.TEXT:
                        textValue = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if(inEntry){
                            if(tagName.equalsIgnoreCase("entry")){//hansi tag lazimdi onu entry yerine yaziriq
                                classInstances.add(currentRecord);
                                inEntry = false;
                            }else if(tagName.equalsIgnoreCase("name"))
                                currentRecord.setName(textValue);
                        }
                        break;
                    default:
                        //Nothing else to do
                }
                eventType = xmlPullParser.next();
            }
        }
        catch (Exception e) {
            Toast toast = Toast.makeText(MainActivity.getContext(), "Something went wrong", Toast.LENGTH_SHORT);
            toast.show();
        }
        return false;
    }
            /*END*/


}
