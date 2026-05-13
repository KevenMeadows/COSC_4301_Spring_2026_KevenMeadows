package org.example.neonarkintaketracker.cli;
import com.fasterxml.jackson.databind.JsonNode; // API response handler for JSON
import com.fasterxml.jackson.databind.ObjectMapper; // To convert JSON strings to node objects.
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient; // Http client
import java.net.http.HttpRequest; // request builder
import java.net.http.HttpResponse; // response support
import java.util.Map; // for request to be made with key pairs.

public class APIClient {

    // store base URL.
    private static final String base_URL = "http://localhost:8080";

    // store HTTP client and object mapper.
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    // Constructor
    public APIClient() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    // get request
    public APIResult get(String path) {
        // build the Http request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base_URL + path)) // set complete URL that is requested.
                .GET() // sets method for request to GET
                .build(); // build object
        return send(request);
    }

    // get method for request to admin role header.
    public APIResult getWithRole(String path, String role) {
        // build the Http request.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base_URL + path)) // set complete URL that is requested.
                .header("Role", role) // Adds role header
                .GET() // sets method for request to GET
                .build(); // build object
        return send(request);
    }

    // send post request
    public APIResult post(String path, Map<String, Object> body){
        // Convert JSON body to JSON string.
        String JSONBody = toJSON(body);

        // build Http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base_URL + path)) // set complete URL that is requested.
                .header("Content-Type", "application/json") // tell API request body is JSON type.
                .POST(HttpRequest.BodyPublishers.ofString(JSONBody)) // method to POST and attach JSON body.
                .build();  // build object
        return send(request);
    }

    // send put request
    public APIResult put(String path, Map<String, Object> body){
        // Convert JSON body to JSON string.
        String JSONBody = toJSON(body);

        // build Http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base_URL + path)) // Set complete URL that is requested.
                .header("Content-Type", "application/json") // tell API request body is JSON type.
                .PUT(HttpRequest.BodyPublishers.ofString(JSONBody)) // method to POST and attach JSON body.
                .build();  // build object
        return send(request);
    }

    // Delete request
    public APIResult delete(String path) {
        // build Http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(base_URL + path)) // Set complete URL that is requested.
                .DELETE() // send to delete
                .build(); // build object
        return send(request);
    }

    // main sender method for API.
    private APIResult send(HttpRequest request) {
        // try exception
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // convert response to JSON.
            JsonNode JSON = parseJSON(response.body());

            // Create APIResult object for use by CLI.
            return new APIResult(
                    response.statusCode(), // store HTTP code
                    JSON, // store converted JSON
                    response.body() // store original body
            );
        }
        catch (IOException e) {
            return APIResult.connectionFail("API connection failed. Ensure the port Spring Boot is running on is 8080.");
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore
            return APIResult.connectionFail("There was an interruption during the request.");
        }
    }

    // Create conversion method for JSON response body.
    // Body string to JsonNode.
    private JsonNode parseJSON(String body) {
        // try exception
        try {if(body == null || body.isBlank()){return objectMapper.createObjectNode();} // return empty JSON
            return objectMapper.readTree(body);} // Parse JSON
        catch (Exception exception) {return objectMapper.createObjectNode();}
    }
    // Map to JSON String.
    private String toJSON(Map<String, Object> body){
        // try exception
        try {return objectMapper.writeValueAsString(body);} // converts to JSON.
        catch (Exception exception) {
            throw new IllegalArgumentException("Request body could not be converted to JSON:", exception);
        }
    }

    // Create APIResult record for response container for API calls.
    public record APIResult (
            int statusCode, // store HTTP status code.
            JsonNode JSON, // Store JSON response.
            String originalBody // Store original response body.
    ){
        // Check if HTTP code is a code that means successful request.
        public boolean success(){return statusCode >= 200 && statusCode < 300;}

        // Create a net for connection failures
        public static APIResult connectionFail(String message){
            // Create object mapper for JSON building.
            ObjectMapper mapper = new ObjectMapper();

            // Empty JSON object
            JsonNode JSON = mapper.createObjectNode()
                    .put("status", 0)
                    .put("error", "Connection Failed")
                    .put("message", message);
            return new APIResult(0, JSON, message);
        }
    }
}