import java.util.HashMap;
import java.util.Set;
import java.util.Random;
/**
 * Write a description of class Item here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Item
{
   
   private String name; 
   public int itemRoom;
   private double value; 
   private String itemDesc; 
   private int rNumber;
  
   
   //Initialize item 
   public Item(String name, String itemDesc, double value)
    {
        this.name = name; 
        this.itemDesc = itemDesc; 
        this.value = value; 
        
    }

    //Return item room
    public int getItemRoom()
    {
      return itemRoom;
    }
    
    //Return item Name
    public String getItemName()
    {
        return name;
    }
    
    //Return item description
    public String itemDesc()
    {
        return itemDesc;
    }
    
    //Return item value
    public double getItemValue()
    {
        return value;
    }
    
    public void printFind()
    {
        System.out.println("\n ******************************************************\n You found " + getItemName() + "! \n [Description] " + itemDesc() + " \n [Fuel Value] " + getItemValue() + 
        "\n ****************************************************** ");
    }
    
    public void print()
    {
        System.out.println("\n * Item: " + getItemName() + " \n[Description] " + itemDesc() + " \n[Fuel Value] " + getItemValue());
    }
   
        
    

}