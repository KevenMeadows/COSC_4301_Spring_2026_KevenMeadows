// Keven Meadows
// Class: Modern Programming-COSC-4301
// Professor: Jon-Mikel Pearson
// Date: 02/10/26
// Assignment: Project 1 - Dockerized Java Monster App

// Create a simple Monster class with at least:
// name (String)
// type (String) (examples: Fire, Shadow, Lunar, Void)
// a constructor
// a method that returns a clear description of the monster

// Basic structure (example outline only):
// class Monster
// fields: name, type, description
// constructor: Monster(String name, String type)
// method: getDescription() → returns a simple String

// Create Monster class
public class Monster {

    // Establish what a monster is made of (name and type).
    private String name; // Name the monster (Emberclaw from the example)
    private String type; // Abilities or type of monster like fire, lunar, void


    // Create the constructor
    public Monster(String name, String type) {
        this.name = name; // Assign name to Monster object
        this.type = type; // Assign type to Monster object
    }

    // Create the return method
    // Return similar format as expected output with name + type
    public String getDescription() {
        // Format similar to example output
        return name + " is a " + type + "-type monster from the Neon Ark training program.";
    }
}
