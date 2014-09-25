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
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationProvider;

/**
 * @author TestUser
 */
public class HelloMIDlet extends MIDlet implements CommandListener {

    private boolean midletPaused = false;


     public String getDataFromServer(){
    HttpConnection httpConn = null;
    InputStream is = null;
    String serverUrl = "http://www.clever-tanken.de/tankstelle_liste?spritsorte=3&r=5&ort=Berlin&lat=&lon=";
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


     public void checkLocation() 
    {
        try {
            String string;
            Location l;
            LocationProvider lp;
            Coordinates c;
            // Set criteria for selecting a location provider:
            // accurate to 500 meters horizontally
            Criteria cr = new Criteria();
            cr.setHorizontalAccuracy(500);
            // Get an instance of the provider
            lp = LocationProvider.getInstance(cr);
            // Request the location, setting a one-minute timeout
            l = lp.getLocation(60);
            c = l.getQualifiedCoordinates();
            if (c != null) {
                // Use coordinate information
                double lat = c.getLatitude();
                double lon = c.getLongitude();
                string = "\nLatitude : " + lat + "\nLongitude : " + lon;
            } else {
                string = "Location API failed";
            }
            System.out.println(string);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
  }


     //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
     private Command exitCommand;
     private Command okCommand;
     private Command exitCommand1;
     private Command backCommand;
     private Form SelectionScreen;
     private TextField textField;
     private List list;
     //</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public HelloMIDlet() {
    }

    private void displayReadData(Vector alleTankstellen) {
        switchDisplayable(null, getList());
        List currentList = getList();
       for(int index=0;index<5;index++){
           Tankstelle currentTankstelle = (Tankstelle) alleTankstellen.elementAt(index);
           currentList.set(index, currentTankstelle.nameDerTankstelle+"\n"+currentTankstelle.priceOfFuel+"\n"+currentTankstelle.name_location_streetNumber+"\n"+currentTankstelle.name_location_ZIP_CODE+"\n"+currentTankstelle.distanceToTankstelle, null);
       }
    }

    private void handleReadData(String dataRead) {
        String delimiter = "<div id=\"tankstelle";
        String[] splittedData = split(dataRead,delimiter);
    //  System.out.println(splittedData[1]);
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
            System.out.println(name_location_NameDerTanke);


           

            //distance
             String[] distance = split(splittedData[index],"<div class=\"location_distance\">");
            distance = split(distance[1],"</div>");
           // System.out.println(distance[0]);

            alleTankstellen.addElement(new Tankstelle(price[0],distance[0],name_location_NameDerTanke,name_location_streetNumber,name_location_ZIP_CODE));
        }

        displayReadData(alleTankstellen);
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

   //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
   //</editor-fold>//GEN-END:|methods|0|

   //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
   /**
    * Initilizes the application.
    * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
    */
   private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
   }//GEN-BEGIN:|0-initialize|2|
   //</editor-fold>//GEN-END:|0-initialize|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
   /**
    * Performs an action assigned to the Mobile Device - MIDlet Started point.
    */
   public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction

    //</editor-fold>

            // write pre-action user code here
       switchDisplayable(null, getSelectionScreen());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here

this.checkLocation();
this.getDataFromServer();



   }//GEN-BEGIN:|3-startMIDlet|2|
   //</editor-fold>//GEN-END:|3-startMIDlet|2|


   

   //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
   /**
    * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
    */
   public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
   }//GEN-BEGIN:|4-resumeMIDlet|2|
   //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
   /**
    * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
    * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
    * @param nextDisplayable the Displayable to be set
    */
   public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
       Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
       if (alert == null) {
           display.setCurrent(nextDisplayable);
       } else {
           display.setCurrent(alert, nextDisplayable);
       }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
   }//GEN-BEGIN:|5-switchDisplayable|2|
   //</editor-fold>//GEN-END:|5-switchDisplayable|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
   /**
    * Called by a system to indicated that a command has been invoked on a particular displayable.
    * @param command the Command that was invoked
    * @param displayable the Displayable where the command was invoked
    */
   public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
       if (displayable == SelectionScreen) {//GEN-BEGIN:|7-commandAction|1|19-preAction
           if (command == exitCommand) {//GEN-END:|7-commandAction|1|19-preAction
                // write pre-action user code here
               exitMIDlet();//GEN-LINE:|7-commandAction|2|19-postAction
                // write post-action user code here
           } else if (command == okCommand) {//GEN-LINE:|7-commandAction|3|23-preAction
                // write pre-action user code here
               switchDisplayable(null, getList());//GEN-LINE:|7-commandAction|4|23-postAction
                // write post-action user code here
           }//GEN-BEGIN:|7-commandAction|5|36-preAction
       } else if (displayable == list) {
           if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|5|36-preAction
               // write pre-action user code here
               listAction();//GEN-LINE:|7-commandAction|6|36-postAction
               // write post-action user code here
           } else if (command == backCommand) {//GEN-LINE:|7-commandAction|7|40-preAction
               // write pre-action user code here
               switchDisplayable(null, getSelectionScreen());//GEN-LINE:|7-commandAction|8|40-postAction
               // write post-action user code here
           }//GEN-BEGIN:|7-commandAction|9|7-postCommandAction
       }//GEN-END:|7-commandAction|9|7-postCommandAction
        // write post-action user code here
   }//GEN-BEGIN:|7-commandAction|10|
   //</editor-fold>//GEN-END:|7-commandAction|10|

   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
   /**
    * Returns an initiliazed instance of exitCommand component.
    * @return the initialized component instance
    */
   public Command getExitCommand() {
       if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
           exitCommand = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
       }//GEN-BEGIN:|18-getter|2|
       return exitCommand;
   }
   //</editor-fold>//GEN-END:|18-getter|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: SelectionScreen ">//GEN-BEGIN:|14-getter|0|14-preInit
   /**
    * Returns an initiliazed instance of SelectionScreen component.
    * @return the initialized component instance
    */
   public Form getSelectionScreen() {
       if (SelectionScreen == null) {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
           SelectionScreen = new Form("Welcome", new Item[] { getTextField() });//GEN-BEGIN:|14-getter|1|14-postInit
           SelectionScreen.addCommand(getExitCommand());
           SelectionScreen.addCommand(getOkCommand());
           SelectionScreen.setCommandListener(this);//GEN-END:|14-getter|1|14-postInit
            // write post-init user code here
       }//GEN-BEGIN:|14-getter|2|
       return SelectionScreen;
   }
   //</editor-fold>//GEN-END:|14-getter|2|



   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|22-getter|0|22-preInit
   /**
    * Returns an initiliazed instance of okCommand component.
    * @return the initialized component instance
    */
   public Command getOkCommand() {
       if (okCommand == null) {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
           okCommand = new Command("Ok", Command.OK, 0);//GEN-LINE:|22-getter|1|22-postInit
            // write post-init user code here
       }//GEN-BEGIN:|22-getter|2|
       return okCommand;
   }
   //</editor-fold>//GEN-END:|22-getter|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand1 ">//GEN-BEGIN:|25-getter|0|25-preInit
   /**
    * Returns an initiliazed instance of exitCommand1 component.
    * @return the initialized component instance
    */
   public Command getExitCommand1() {
       if (exitCommand1 == null) {//GEN-END:|25-getter|0|25-preInit
            // write pre-init user code here
           exitCommand1 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|25-getter|1|25-postInit
            // write post-init user code here
       }//GEN-BEGIN:|25-getter|2|
       return exitCommand1;
   }
   //</editor-fold>//GEN-END:|25-getter|2|





   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textField ">//GEN-BEGIN:|32-getter|0|32-preInit
   /**
    * Returns an initiliazed instance of textField component.
    * @return the initialized component instance
    */
   public TextField getTextField() {
       if (textField == null) {//GEN-END:|32-getter|0|32-preInit
            // write pre-init user code here
           textField = new TextField("Please select your city/location", null, 32, TextField.ANY);//GEN-LINE:|32-getter|1|32-postInit
            // write post-init user code here
       }//GEN-BEGIN:|32-getter|2|
       return textField;
   }
   //</editor-fold>//GEN-END:|32-getter|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|39-getter|0|39-preInit
   /**
    * Returns an initiliazed instance of backCommand component.
    * @return the initialized component instance
    */
   public Command getBackCommand() {
       if (backCommand == null) {//GEN-END:|39-getter|0|39-preInit
            // write pre-init user code here
           backCommand = new Command("Back", Command.BACK, 0);//GEN-LINE:|39-getter|1|39-postInit
            // write post-init user code here
       }//GEN-BEGIN:|39-getter|2|
       return backCommand;
   }
   //</editor-fold>//GEN-END:|39-getter|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Getter: list ">//GEN-BEGIN:|34-getter|0|34-preInit
   /**
    * Returns an initiliazed instance of list component.
    * @return the initialized component instance
    */
   public List getList() {
       if (list == null) {//GEN-END:|34-getter|0|34-preInit
            // write pre-init user code here
           list = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|34-getter|1|34-postInit
           list.append("List Element 1", null);
           list.append("List Element 2", null);
           list.append("List Element 3", null);
           list.append("List Element 4", null);
           list.append("List Element 5", null);
           list.addCommand(getBackCommand());
           list.setCommandListener(this);
           list.setSelectedFlags(new boolean[] { false, false, false, false, false });//GEN-END:|34-getter|1|34-postInit
            // write post-init user code here
       }//GEN-BEGIN:|34-getter|2|
       return list;
   }
   //</editor-fold>//GEN-END:|34-getter|2|

   //<editor-fold defaultstate="collapsed" desc=" Generated Method: listAction ">//GEN-BEGIN:|34-action|0|34-preAction
   /**
    * Performs an action assigned to the selected list element in the list component.
    */
   public void listAction() {//GEN-END:|34-action|0|34-preAction
        // enter pre-action user code here
       String __selectedString = getList().getString(getList().getSelectedIndex());//GEN-BEGIN:|34-action|1|42-preAction
       if (__selectedString != null) {
           if (__selectedString.equals("List Element 1")) {//GEN-END:|34-action|1|42-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|2|42-postAction
                // write post-action user code here
           } else if (__selectedString.equals("List Element 2")) {//GEN-LINE:|34-action|3|43-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|4|43-postAction
                // write post-action user code here
           } else if (__selectedString.equals("List Element 3")) {//GEN-LINE:|34-action|5|44-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|6|44-postAction
                // write post-action user code here
           } else if (__selectedString.equals("List Element 4")) {//GEN-LINE:|34-action|7|45-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|8|45-postAction
                // write post-action user code here
           } else if (__selectedString.equals("List Element 5")) {//GEN-LINE:|34-action|9|46-preAction
                // write pre-action user code here
//GEN-LINE:|34-action|10|46-postAction
                // write post-action user code here
           }//GEN-BEGIN:|34-action|11|34-postAction
       }//GEN-END:|34-action|11|34-postAction
        // enter post-action user code here
   }//GEN-BEGIN:|34-action|12|
   //</editor-fold>//GEN-END:|34-action|12|

    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

}
