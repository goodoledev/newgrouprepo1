import java.util.*;
import java.io.*;
import java.util.Random;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item item; 
    private Item thisItem;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    public int itemRoom;
    public int rNumber;
    public String description;
    private int genCap=0;
    private Item lamp, oil; 
    public ArrayList<Room> rooms = new ArrayList<Room>();
    private ArrayList<Item> items= new ArrayList<Item>();
    Scanner scanner = new Scanner(System.in);
    public boolean canAdd = false;
    public double fuelBar;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        createItems();
        parser = new Parser();

    }

    boolean noItem = false; 
    private void itemGen(ArrayList<Item> a, Room b)
    {
        items.add(lamp);
        items.add(lamp);

        if(genCap<8 && !b.getShortDescription().contains("exit"))
        {
            Random random = new Random();
            rNumber = random.nextInt(4);
            if(rNumber==0 || rNumber==1)
            {
                //System.out.println("\nSetting Item\n");
                thisItem = items.get(rNumber);
                b.setItem(thisItem); 
                

            }

            else
            {
                //System.out.println("\nSetting null");
                b.setItem(null);

            }
        }
        items.remove(lamp);
        items.remove(lamp);

    }

    private String getName(Room g, Item h)
    {
        String roomItem = h.getItemName();
        return roomItem;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room entrance,left1,left2,left3,portalLeft, mid1, mid2, mid3, mid4, right1, right2, right3, farRight1, farRight2, exit;

        // create the rooms
        entrance = new Room("the entrance to to a poorly lit cavern", thisItem);
        rooms.add(entrance);
        itemGen(items, entrance);
        left1 = new Room("left to the entrance", thisItem);
        rooms.add(left1);
        itemGen(items, left1);
        left2 = new Room("north of left 1", thisItem);
        rooms.add(left2);
        itemGen(items, left2);
        left3 = new Room("north of left 2", thisItem);
        rooms.add(left3);
        itemGen(items, left3);
        portalLeft = new Room("You hear a strange humming noise", thisItem);
        rooms.add(portalLeft);
        itemGen(items, portalLeft);
        mid1 = new Room("north of the entrance", thisItem);
        rooms.add(mid1);
        itemGen(items, mid1);
        mid2 = new Room("north of mid1", thisItem);
        rooms.add(mid2);
        itemGen(items, mid2);
        mid3 = new Room("north of mid2", thisItem);
        rooms.add(mid3);
        itemGen(items, mid3);
        mid4 = new Room("north of mid3", thisItem);
        rooms.add(mid4);
        itemGen(items, mid4);

        right1 = new Room("in a lecture theater", thisItem);
        rooms.add(right1);
        itemGen(items, right1);
        right2 = new Room("in the campus pub", thisItem);
        rooms.add(right2);
        itemGen(items, right2);
        right3 = new Room("in a computing lab", thisItem);
        rooms.add(right3);
        itemGen(items, right3);

        farRight1 = new Room("in the computing admin office", thisItem);
        rooms.add(farRight1);
        itemGen(items, farRight1);
        farRight2 = new Room("north of farRight1", thisItem);
        rooms.add(farRight2);
        itemGen(items, farRight2);

        exit = new Room("Finally the exit", null);

        // initialise room exits

        entrance.setExit("north", mid1);

        mid1.setExit("north", mid2);
        mid1.setExit("east", right1);

        mid2.setExit("north", mid3);
        mid2.setExit("west", left2);
        mid2.setExit("south",mid1);

        mid3.setExit("north", mid4);
        mid3.setExit("south",mid2);

        mid4.setExit("south", mid3);

        left1.setExit("north", left2);

        left2.setExit("east", mid2);
        left2.setExit("north", left3);
        left2.setExit("south",left1);

        left3.setExit("south", left2);
        left3.setExit("west", portalLeft);

        portalLeft.setExit("portal", farRight1);
        portalLeft.setExit("west", left3);

        right1.setExit("north",right2);
        right1.setExit("west", mid1);
        right1.setExit("east", farRight1);

        right2.setExit("north", right3);
        right2.setExit("south", right1);

        right3.setExit("west", mid3);
        right3.setExit("south", right2);

        farRight1.setExit("west", right1);

        farRight2.setExit("west", right2);
        farRight2.setExit("north", exit);

        exit.setExit("north", exit);
        currentRoom = entrance;  // start game at entrance
    }

    /**
     * Create items. 
     */

    public void createItems()
    {

        lamp = new Item("Lamp", "Source of light", 100);
        oil = new Item("Oil", "Refuel your lamp", 10);


        inventory.add(lamp);
        items.add(oil);
        items.add(oil); 
        items.add(oil);
        items.add(oil);
        items.add(oil);
        items.add(oil);
        items.add(oil);

        
     

    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        //System.out.println(items.size());
        printItems();
         fuelBar = lamp.getItemValue();
        //fuelBar = 100;
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        
        else if (commandWord.equals("get")) {
            printInven(command);
        }
        
        else if (commandWord.equals("add")) {
           addItem(command);
        }
       
        
        // else command not recognised.
        return wantToQuit;
    }


    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        // String input = parser.getInput().toLowerCase();
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else 
        {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            
            fuelBar=fuelBar - 10;
            System.out.println("\n|| Fuel Bar Level: " + fuelBar + " ||\n");
            if (fuelBar == 0)
            {
                System.out.println("Your lamp has run out of fuel and the room is pitch dark!!!");
                System.out.println("There's no hope for you to find the exit and you've been consumed by the dead...");
                System.out.println("GAME OVER!!!!!");
                System.exit(0);
            }

            
            //System.out.println(getItem(currentRoom));
            if(getItem(currentRoom) != null)
            {
                
                String roomItem = getName(currentRoom, thisItem);
                //System.out.println(roomItem);
                if(roomItem.equals("Oil"))
                {
                    thisItem.printFind();
                    System.out.println("\nEnter \"add item\" to add this to your inventory.\n");
                    canAdd = true;
                    
                }
                

            }
        }

    }

    public Item getItem(Room currentRoom)
    {
        return thisItem;
    }

    public void printInventory()
    {
        System.out.println("\n///////////////////////////////////////////////////////\n");
        //System.out.println("\n--------------------------------");
        System.out.println("                  I N V E N T O R Y");
        System.out.println("                        Size: " + inventory.size());
        //System.out.println("--------------------------------");
        int index = 0;
        while(index < inventory.size())
        {
            thisItem = inventory.get(index);
            thisItem.print();

            index ++;
        }
        // Print a blank line at the bottom
        System.out.println("\n///////////////////////////////////////////////////////\n");

    }

    public void printItems()
    {
        //System.out.println("Items.");
        //System.out.println("Size: " + items.size());
        int index = 0;
        while(index < items.size())
        {
            thisItem = items.get(index);
            //thisItem.print();

            index ++;
        }
    }
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    private boolean printInven(Command command)
    {
        
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Get what?");
            return false;
        }
        
        else if(command.getSecondWord().equalsIgnoreCase("inventory"))
        {
        printInventory();
        return true;
        }
        
        else if(command.getSecondWord().equalsIgnoreCase("fuel"))
        {
        System.out.println("Fuel Bar Level: " + fuelBar);
        return true;
        }
        
        else 
        {
            System.out.println("Please enter a valid command.");
            return false; 
        }
    }
    
    private boolean addItem(Command command)
    {
           if(!command.hasSecondWord()) 
           {
            // if there is no second word, we don't know where to go...
            System.out.println("Add what?");
            return false;
          }
            

            else if(command.getSecondWord().equalsIgnoreCase("item") && canAdd)
                    {
                        
                        inventory.add(thisItem);
                        thisItem = null; 
                        System.out.println("> Item has been added to your inventory. Enter \"get inventory\" to see your inventory.");
                        fuelBar=fuelBar + 10;
                        //System.out.println("Fuel Bar Level: " + fuelBar);
                        canAdd = false;
                        return true;
                    }
              else{
                  System.out.println("You have no items to add! Please enter a valid command.");
                  return false;
                }
              
           
                    
                    
                
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
