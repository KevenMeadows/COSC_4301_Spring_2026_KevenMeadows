package org.example.wardenconsole;

import java.util.Map;

public class SimRequestDisplay {

    // Displays simulated request
    public void displayRequest(String method, String endpoint, String description, Map<String, Object> payload) {
        // Following instruction format.
        System.out.println();
        System.out.println("WOULD SEND: " + method + " " + endpoint);
        System.out.println("BRIEF DESCRIPTION: " + description);
        System.out.println("PAYLOAD (JSON)");
        System.out.println("{");

        // current payload
        int index = 0;

        // store total payload
        int size = payload.size();

        // loop through payloads
        for (Map.Entry<String, Object> entry : payload.entrySet()) {
            // next item
            index++;

            // add commas to each part except last
            String comma = index < size ? "," : "";

            // get value
            Object value = entry.getValue();

            // Check value
            if (value instanceof Number || value instanceof Boolean) {
                System.out.println("  \"" + entry.getKey() + "\": " + value + comma); // display number or boolean
            } else {
                System.out.println("  \"" + entry.getKey() + "\": \"" + value + "\"" + comma); // display string
            }
        }

        // end simulated JSON object and display simulated results.
        System.out.println("}");
        System.out.println("Result: SUCCESS (simulated)");
        System.out.println("Backend: Not contacted. Data Written: None.");
    }

    public void displayBlocked(String reason) {
        System.out.println();
        // display blocked result.
        System.out.println("Result: BLOCKED (simulated)");
        System.out.println("Reason: " + reason);
        System.out.println("Request: Not prepared. Data Changed: None.");
    }
}