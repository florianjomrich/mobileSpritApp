/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hello;

/**
 *
 * @author TestUser
 */
public class Tankstelle {
    public String nameDerTankstelle;
    public String distanceToTankstelle;
    public String name_location_streetNumber;
    public String name_location_ZIP_CODE;
    public Double priceOfFuel;

    Tankstelle(String priceOfFuel, String distanceToTankstelle,String nameDerTankstelle,String name_location_streetNumber,String name_location_ZIP_CODE) {
       this.priceOfFuel = Double.valueOf(priceOfFuel);
       this.distanceToTankstelle = distanceToTankstelle;
       this.nameDerTankstelle = nameDerTankstelle;
       this.name_location_streetNumber = name_location_streetNumber;
       this.name_location_ZIP_CODE = name_location_ZIP_CODE;
    }



}
