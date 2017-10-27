
import javax.xml.parsers.*;
import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class CarMakesScript {


    //All cars from mobile.de REST-API Key:Make Value:Modells
    static Map<String,ArrayList<String>> cars = new LinkedHashMap<String, ArrayList<String>>();;


    public static void main(String[] args)
    {

        String url = "http://services.mobile.de/1.0.0/refdata/sites/GERMANY/classes/Car/makes";
        getMakes(url);

        for (String make : cars.keySet()) {

            //todo: handle for makes
            System.out.println(make);



            for(String modells : cars.get(make) ){

                //todo: handle for modells
                System.out.println("\t" + modells);


            }
        }

    }

    public static void getMakes(String url){

        try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);

            doc.getDocumentElement().normalize();

            // loop through each item
            NodeList items = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < items.getLength(); i++)
            {

                String make = items.item(i).getAttributes().getNamedItem("key").getTextContent();
                String modellUrl = "http://services.mobile.de/refdata/sites/GERMANY/classes/Car/makes/" + make + "/models";

                ArrayList<String> modells = getModells(modellUrl);

                cars.put(items.item(i).getTextContent(), modells);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getModells(String url){

        ArrayList<String> modells = new ArrayList<String>();
        try
        {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(url);

            doc.getDocumentElement().normalize();

            // loop through each item
            NodeList items = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < items.getLength(); i++)
            {
                modells.add(items.item(i).getTextContent());

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return modells;
    }
}
