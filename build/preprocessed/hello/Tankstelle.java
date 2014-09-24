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
    public Double priceOfFuel;

    Tankstelle(String priceOfFuel, String distanceToTankstelle,String nameDerTankstelle) {
       this.priceOfFuel = Double.valueOf(priceOfFuel);
       this.distanceToTankstelle = distanceToTankstelle;
       this.nameDerTankstelle = nameDerTankstelle;
    }



}
