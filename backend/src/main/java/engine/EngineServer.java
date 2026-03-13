package engine;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;
import engine.models.SimulationRequest;
import engine.models.SimulationResponse;
import engine.models.Step;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.List;

public class EngineServer {

    private static final Gson gson = new Gson();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(3001), 0);
        server.createContext("/simulate", EngineServer::handleSimulate);
        server.setExecutor(null);
        System.out.println("Server running on port 3001");
        server.start();
    }

    private static void handleSimulate(HttpExchange exchange) throws IOException {
        // CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        // Preflight
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        if (!exchange.getRequestMethod().equalsIgnoreCase("POST")) {
            exchange.sendResponseHeaders(405, -1);
            return;
        }

        try {
            // Parse request
            String body = new String(exchange.getRequestBody().readAllBytes());
            SimulationRequest request = gson.fromJson(body, SimulationRequest.class);

            // Dispatch
            List<Step> steps = SimulationDispatcher.dispatch(request);

            // Build response
            SimulationResponse response = new SimulationResponse();
            response.structure = request.structure;
            response.operation = request.operation;
            response.steps = steps;

            // Send response
            String json = gson.toJson(response);
            byte[] bytes = json.getBytes();
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();

        } catch (Exception e) {
            String error = gson.toJson(new ErrorResponse(e.getMessage()));
            byte[] bytes = error.getBytes();
            exchange.sendResponseHeaders(500, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.getResponseBody().close();
        }
    }

    record ErrorResponse(String error) {}
}