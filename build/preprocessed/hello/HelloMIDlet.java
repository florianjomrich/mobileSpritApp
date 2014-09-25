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
     private Command exitCommand2;
     private Form SelectionScreen;
     private TextField cityLocationSelectionTextField;
     private ChoiceGroup choiceGroup;
     private List GasStations;
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
        if (displayable == GasStations) {//GEN-BEGIN:|7-commandAction|1|36-preAction
            if (command == List.SELECT_COMMAND) {//GEN-END:|7-commandAction|1|36-preAction
               // write pre-action user code here
                GasStationsAction();//GEN-LINE:|7-commandAction|2|36-postAction
               // write post-action user code here
            } else if (command == backCommand) {//GEN-LINE:|7-commandAction|3|40-preAction
               // write pre-action user code here
                switchDisplayable(null, getSelectionScreen());//GEN-LINE:|7-commandAction|4|40-postAction
               // write post-action user code here
            } else if (command == exitCommand2) {//GEN-LINE:|7-commandAction|5|48-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|6|48-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|19-preAction
        } else if (displayable == SelectionScreen) {
            if (command == exitCommand) {//GEN-END:|7-commandAction|7|19-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|8|19-postAction
                // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|9|23-preAction

                //this.checkLocation();
                //this.getDataFromServer();
                if(getCityLocationSelectionTextField().size()!=0){
                      //start the internet connection in a new thread

                    int selectedNumber = getChoiceGroup().getSelectedIndex();
                    System.out.println(selectedNumber);
                    int indexForURL = 0;
                    String titleForURL = "";

                 switch(selectedNumber){
                     case 0:
                         //Diesel
                         //System.out.println("Diesel");
                         titleForURL="Diesel";
                         indexForURL=3;
                         break;
                     case 1:
                         //Super E10
                         titleForURL="Super E10";
                         indexForURL=5;
                         break;
                     case 2:
                         //Super E5
                         titleForURL="Super E5";
                         indexForURL=7;
                         break;
                     case 3:
                         indexForURL=8;
                         titleForURL="Erdgas";
                         //Erdgas
                         break;
                     default:

                 }



                Thread newServerConncetionThread = new Thread(new ServerDataReceiver(this,this.getCityLocationSelectionTextField().getString(),indexForURL,titleForURL));
                newServerConncetionThread.start();
                }
                else{
                    getCityLocationSelectionTextField().setLabel("Select your city first:");
                }

//GEN-LINE:|7-commandAction|10|23-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|7-postCommandAction
        }//GEN-END:|7-commandAction|11|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|12|
    //</editor-fold>//GEN-END:|7-commandAction|12|

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
            SelectionScreen = new Form("Welcome", new Item[] { getCityLocationSelectionTextField(), getChoiceGroup() });//GEN-BEGIN:|14-getter|1|14-postInit
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





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: cityLocationSelectionTextField ">//GEN-BEGIN:|32-getter|0|32-preInit
    /**
     * Returns an initiliazed instance of cityLocationSelectionTextField component.
     * @return the initialized component instance
     */
    public TextField getCityLocationSelectionTextField() {
        if (cityLocationSelectionTextField == null) {//GEN-END:|32-getter|0|32-preInit
            // write pre-init user code here
            cityLocationSelectionTextField = new TextField("Please select your city/location", "Ruesselsheim", 32, TextField.ANY);//GEN-LINE:|32-getter|1|32-postInit
            // write post-init user code here
        }//GEN-BEGIN:|32-getter|2|
        return cityLocationSelectionTextField;
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: GasStations ">//GEN-BEGIN:|34-getter|0|34-preInit
    /**
     * Returns an initiliazed instance of GasStations component.
     * @return the initialized component instance
     */
    public List getGasStations() {
        if (GasStations == null) {//GEN-END:|34-getter|0|34-preInit
            // write pre-init user code here
            GasStations = new List("list", Choice.IMPLICIT);//GEN-BEGIN:|34-getter|1|34-postInit
            GasStations.append("List Element 1", null);
            GasStations.append("List Element 2", null);
            GasStations.append("List Element 3", null);
            GasStations.append("List Element 4", null);
            GasStations.append("List Element 5", null);
            GasStations.addCommand(getBackCommand());
            GasStations.addCommand(getExitCommand2());
            GasStations.setCommandListener(this);
            GasStations.setSelectedFlags(new boolean[] { false, false, false, false, false });//GEN-END:|34-getter|1|34-postInit
            // write post-init user code here
        }//GEN-BEGIN:|34-getter|2|
        return GasStations;
    }
    //</editor-fold>//GEN-END:|34-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: GasStationsAction ">//GEN-BEGIN:|34-action|0|34-preAction
    /**
     * Performs an action assigned to the selected list element in the GasStations component.
     */
    public void GasStationsAction() {//GEN-END:|34-action|0|34-preAction
        // enter pre-action user code here
        String __selectedString = getGasStations().getString(getGasStations().getSelectedIndex());//GEN-BEGIN:|34-action|1|42-preAction
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

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand2 ">//GEN-BEGIN:|47-getter|0|47-preInit
    /**
     * Returns an initiliazed instance of exitCommand2 component.
     * @return the initialized component instance
     */
    public Command getExitCommand2() {
        if (exitCommand2 == null) {//GEN-END:|47-getter|0|47-preInit
            // write pre-init user code here
            exitCommand2 = new Command("Exit", Command.EXIT, 0);//GEN-LINE:|47-getter|1|47-postInit
            // write post-init user code here
        }//GEN-BEGIN:|47-getter|2|
        return exitCommand2;
    }
    //</editor-fold>//GEN-END:|47-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroup ">//GEN-BEGIN:|50-getter|0|50-preInit
    /**
     * Returns an initiliazed instance of choiceGroup component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroup() {
        if (choiceGroup == null) {//GEN-END:|50-getter|0|50-preInit
            // write pre-init user code here
            choiceGroup = new ChoiceGroup("choiceGroup", Choice.EXCLUSIVE);//GEN-BEGIN:|50-getter|1|50-postInit
            choiceGroup.append("Diesel", null);
            choiceGroup.append("Super E10", null);
            choiceGroup.append("Super E5", null);
            choiceGroup.append("Erdgas", null);
            choiceGroup.setSelectedFlags(new boolean[] { false, false, true, false });
            choiceGroup.setFont(0, null);
            choiceGroup.setFont(1, null);
            choiceGroup.setFont(2, null);
            choiceGroup.setFont(3, null);//GEN-END:|50-getter|1|50-postInit
            // write post-init user code here
        }//GEN-BEGIN:|50-getter|2|
        return choiceGroup;
    }
    //</editor-fold>//GEN-END:|50-getter|2|

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
