/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.List;

/**
 *
 * @author TestUser
 */
public class ServerDataReceiver implements Runnable {

    HelloMIDlet currentMidlet;
    String currentCityLocation;
    int indexForURL;
    String titleForURL;

    public ServerDataReceiver(HelloMIDlet currentMidlet,String currentCityLocation,int indexForURL, String titleForURL){
        this.currentMidlet = currentMidlet;
        this.currentCityLocation = currentCityLocation;
        this.indexForURL=indexForURL;
        this.titleForURL = titleForURL;
    }

    public void run() {
       this.getDataFromServer();
    }



     public String getDataFromServer(){
    HttpConnection httpConn = null;
    InputStream is = null;
    String serverUrl = "http://www.clever-tanken.de/tankstelle_liste?spritsorte="+indexForURL+"&r=5&ort="+currentCityLocation+"&lat=&lon=";
    String dataRead = "";
    try{
        httpConn = (HttpConnection)Connector.open(serverUrl);
        if((httpConn.getResponseCode() == HttpConnection.HTTP_OK)){
            int length = (int)httpConn.getLength();
            is = httpConn.openInputStream();
            if(length == -1){//unknown length returned by server.
//It is more efficient to read the data in chunks, so we
//will be reading in chunk of 1500 = Maximum MTU possible

                int chunkSize = 1500;
                byte[] data = new byte[chunkSize];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int dataSizeRead = 0;//size of data read from input stream.
                while((dataSizeRead = is.read(data))!= -1){
//it is not recommended to write to string in the
//loop as it causes heap defragmentation and it is
//inefficient, therefore we use the
//ByteArrayOutputStream.
                    baos.write(data, 0, dataSizeRead );
                    System.out.println("Data Size Read = "+dataSizeRead);
                }
                dataRead = new String(baos.toByteArray());
                baos.close();
            } else{//known length
                DataInputStream dis = new DataInputStream(is);
                byte[] data = new byte[length];
//try to read all the bytes returned from the server.
                dis.readFully(data);
                dataRead = new String(data);
            }
        }
    }


//Since only limited number of network objects can be in open state
//it is necessary to clean them up as soon as we are done with them.
    finally{//Networking done. Clean up the network objects
        try{
            if(is != null)
                is.close();
        } catch(Throwable t){
            System.out.println("Exception occurred while closing input " +
                    "stream.");
        }
        try{
            if(httpConn != null)
                httpConn.close();
        } catch(Throwable t){
            System.out.println("Exception occurred "+t.toString());
        }

      //  System.out.print(dataRead);
        this.handleReadData(dataRead);
        return null;
    }

    }



      private void handleReadData(String dataRead) {
        String delimiter = "<div id=\"tankstelle";
        String[] splittedData = split(dataRead,delimiter);
     // System.out.println(splittedData[1]);
        Vector alleTankstellen = new Vector();
        for(int index=1;index<splittedData.length;index++){

            //preis DONE
            String[] price = split(splittedData[index],"<div class=\"price\">");
            price = split(price[1],"<sup>");
            //System.out.println(price[0]);

             //name
            String[] name = split(splittedData[index],"<div class=\"price_location\">");
            name = split(name[1],"div class=\"price_provider\"></div>");
           // System.out.println(name[0]);

            //name_location
            String[]name_location = split(name[0],"</div>");

            String name_location_NameDerTanke = split(name_location[0],"<div class=\"location_name\">")[1];
            String name_location_streetNumber = split(name_location[1],"<div class=\"location_street_number\">")[1];
            String name_location_ZIP_CODE = split(name_location[2],"<div class=\"location_zip_code_city\">")[1];
          //  System.out.println(name_location_NameDerTanke);
          //    System.out.println(name_location_streetNumber);
          //  System.out.println(name_location_ZIP_CODE);




            //distance
             String[] distance = split(splittedData[index],"<div class=\"location_distance\">");
            distance = split(distance[1],"</div>");
            //System.out.println(distance[0]);

            alleTankstellen.addElement(new Tankstelle(price[0],distance[0],name_location_NameDerTanke,name_location_streetNumber,name_location_ZIP_CODE));
        }

        displayReadData(alleTankstellen);
    }

     private void displayReadData(Vector alleTankstellen) {
       currentMidlet.switchDisplayable(null, currentMidlet.getGasStations());
        List currentList = currentMidlet.getGasStations();
        if(alleTankstellen.size()<5){
            for(int index=0;index<alleTankstellen.size();index++){
                Tankstelle currentTankstelle = (Tankstelle) alleTankstellen.elementAt(index);
           currentList.set(index, currentTankstelle.nameDerTankstelle+"\n"+currentTankstelle.priceOfFuel+" Euro \n"+currentTankstelle.name_location_streetNumber+"\n"+currentTankstelle.name_location_ZIP_CODE+"\n"+currentTankstelle.distanceToTankstelle, null);
            }
        }
        else{
            for(int index=0;index<5;index++){
           Tankstelle currentTankstelle = (Tankstelle) alleTankstellen.elementAt(index);
           currentList.set(index, currentTankstelle.nameDerTankstelle+"\n"+currentTankstelle.priceOfFuel+" Euro \n"+currentTankstelle.name_location_streetNumber+"\n"+currentTankstelle.name_location_ZIP_CODE+"\n"+currentTankstelle.distanceToTankstelle, null);
       }
        }
       
        currentList.setTitle(this.titleForURL);
    }


      private String[] split(String original,String separator) {
    Vector nodes = new Vector();
    // Parse nodes into vector
    int index = original.indexOf(separator);
    while(index >= 0) {
        nodes.addElement( original.substring(0, index) );
        original = original.substring(index+separator.length());
        index = original.indexOf(separator);
    }
    // Get the last node
    nodes.addElement( original );

     // Create split string array
    String[] result = new String[ nodes.size() ];
    if( nodes.size() > 0 ) {
        for(int loop = 0; loop < nodes.size(); loop++)
        {
            result[loop] = (String)nodes.elementAt(loop);
          //  System.out.println(result[loop]);
        }

    }
   return result;
}
}
