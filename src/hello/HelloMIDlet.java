/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
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

        System.out.print(dataRead);
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
     private Form SelectionScreen;
     private TextField textField;
     private Form form;
     //</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public HelloMIDlet() {
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
                switchDisplayable(null, getForm());//GEN-LINE:|7-commandAction|4|23-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|26-preAction
        } else if (displayable == form) {
            if (command == exitCommand1) {//GEN-END:|7-commandAction|5|26-preAction
                // write pre-action user code here
                switchDisplayable(null, getSelectionScreen());//GEN-LINE:|7-commandAction|6|26-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|7-postCommandAction
        }//GEN-END:|7-commandAction|7|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|8|
    //</editor-fold>//GEN-END:|7-commandAction|8|

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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: form ">//GEN-BEGIN:|24-getter|0|24-preInit
    /**
     * Returns an initiliazed instance of form component.
     * @return the initialized component instance
     */
    public Form getForm() {
        if (form == null) {//GEN-END:|24-getter|0|24-preInit
            // write pre-init user code here
            form = new Form("RequestAnswert ");//GEN-BEGIN:|24-getter|1|24-postInit
            form.addCommand(getExitCommand1());
            form.setCommandListener(this);//GEN-END:|24-getter|1|24-postInit
            // write post-init user code here
        }//GEN-BEGIN:|24-getter|2|
        return form;
    }
    //</editor-fold>//GEN-END:|24-getter|2|



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