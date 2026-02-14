// Keven Meadows
// Class: Modern Programming-COSC-4301
// Professor: Jon-Mikel Pearson
// Date: 02/10/26
// Assignment: Project 1 - Dockerized Java Monster App

// This file will run the application.
// Main.java must:
// Import or reference the Monster class
// Create one Monster object inside main
// Print clear output to the terminal

// Expected terminal result:
// Your monster has been created.
// Description: Emberclaw is a Fire-type monster from the Neon Ark training program.

// Create Main class
public class Main {

    public static void main (String[] args) {

        // Use constructor create in Monster file to create Monster object
        // Monster monster = new Monster("Emberclaw", "Fire"); // Referenced name and type from example
        // Make my own monster
        Monster monster = new Monster("Iron Lung", "Metal");

        // Print that the monster was made.
        System.out.println("Your monster has been created.");

        // Call getDescription() method for output
        System.out.println("Description: " + monster.getDescription());
    }
}