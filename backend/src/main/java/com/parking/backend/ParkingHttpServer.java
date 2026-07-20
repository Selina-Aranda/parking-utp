package com.parking.backend;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import com.parking.backend.controller.RegistroController;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ParkingHttpServer {
    private static void setCorsHeaders(HttpExchange exchange) {
        String origin = exchange.getRequestHeaders().getFirst("Origin");
        String allowedOrigin = (origin != null && !origin.isBlank()) ? origin : "http://localhost:5173";
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", allowedOrigin);
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type, Authorization, Origin");
        exchange.getResponseHeaders().set("Access-Control-Allow-Credentials", "true");
    }

    public static void start() throws Exception {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8080),
                0);
        server.createContext("/api/alumnos/registro", new RegistroHandler());
        server.createContext("/api/alumnos/salida", new SalidaHandler());
        server.createContext("/api/dashboard", new DashboardHandler());
        server.setExecutor(Executors.newCachedThreadPool());
        server.start();
        System.out.println("Servidor backend escuchando en http://localhost:8080");
    }

    static class RegistroHandler implements HttpHandler {
        private final RegistroController registroController = new RegistroController();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCorsHeaders(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                sendJson(exchange, 200, Map.of("message", "ok"));
                return;
            }

            if (!"POST".equals(exchange.getRequestMethod())) {
                sendJson(exchange, 405, Map.of("message", "Método no permitido"));
                return;
            }

            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, Object> request = parseJson(body);
                Map<String, Object> response = registroController.registrarVehiculo(request);
                sendJson(exchange, 200, response);
            } catch (Exception ex) {
                sendJson(exchange, 400, Map.of("message", ex.getMessage()));
            }
        }

        private Map<String, Object> parseJson(String body) {
            Map<String, Object> result = new HashMap<>();
            String normalized = body == null ? "" : body.trim();

            if (normalized.isEmpty() || !normalized.startsWith("{")) {
                return result;
            }

            String content = normalized.substring(1, normalized.length() - 1).trim();
            if (content.isEmpty()) {
                return result;
            }

            for (String part : content.split(",")) {
                String[] entry = part.split(":", 2);
                if (entry.length != 2) {
                    continue;
                }
                String key = entry[0].trim().replace("\"", "");
                String value = entry[1].trim();
                if (value.startsWith("\"")) {
                    result.put(key, value.substring(1, value.length() - 1));
                } else if (value.equalsIgnoreCase("null")) {
                    result.put(key, null);
                } else if (value.matches("-?\\d+")) {
                    result.put(key, Integer.parseInt(value));
                } else {
                    result.put(key, value);
                }
            }
            return result;
        }

        private void sendJson(HttpExchange exchange, int statusCode, Map<String, Object> payload) throws IOException {
            byte[] responseBytes = toJson(payload).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(responseBytes);
            }
        }

        private String toJson(Map<String, Object> payload) {
            StringBuilder builder = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                if (!first) {
                    builder.append(",");
                }
                first = false;
                builder.append('"').append(entry.getKey()).append('"').append(':');
                Object value = entry.getValue();
                if (value == null) {
                    builder.append("null");
                } else if (value instanceof String) {
                    builder.append('"').append(value.toString().replace("\\", "\\\\").replace("\"", "\\\""))
                            .append('"');
                } else if (value instanceof Number || value instanceof Boolean) {
                    builder.append(value);
                } else {
                    builder.append('"').append(value).append('"');
                }
            }
            builder.append("}");
            return builder.toString();
        }
    }

    static class SalidaHandler implements HttpHandler {
        private final RegistroController registroController = new RegistroController();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            setCorsHeaders(exchange);
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                sendJson(exchange, 200, Map.of("message", "ok"));
                return;
            }

            if (!"POST".equals(exchange.getRequestMethod())) {
                sendJson(exchange, 405, Map.of("message", "Método no permitido"));
                return;
            }

            try {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                Map<String, Object> request = parseJson(body);
                Map<String, Object> response = registroController.registrarSalida(request);
                sendJson(exchange, 200, response);
            } catch (Exception ex) {
                sendJson(exchange, 400, Map.of("message", ex.getMessage()));
            }
        }

        private Map<String, Object> parseJson(String body) {
            Map<String, Object> result = new HashMap<>();
            String normalized = body == null ? "" : body.trim();

            if (normalized.isEmpty() || !normalized.startsWith("{")) {
                return result;
            }

            String content = normalized.substring(1, normalized.length() - 1).trim();
            if (content.isEmpty()) {
                return result;
            }

            for (String part : content.split(",")) {
                String[] entry = part.split(":", 2);
                if (entry.length != 2) {
                    continue;
                }
                String key = entry[0].trim().replace("\"", "");
                String value = entry[1].trim();
                if (value.startsWith("\"")) {
                    result.put(key, value.substring(1, value.length() - 1));
                } else if (value.equalsIgnoreCase("null")) {
                    result.put(key, null);
                } else if (value.matches("-?\\d+")) {
                    result.put(key, Integer.parseInt(value));
                } else {
                    result.put(key, value);
                }
            }
            return result;
        }

        private void sendJson(HttpExchange exchange, int statusCode, Map<String, Object> payload) throws IOException {
            byte[] responseBytes = toJson(payload).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(statusCode, responseBytes.length);
            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(responseBytes);
            }
        }

        private String toJson(Map<String, Object> payload) {
            StringBuilder builder = new StringBuilder("{");
            boolean first = true;
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                if (!first) {
                    builder.append(",");
                }
                first = false;
                builder.append('"').append(entry.getKey()).append('"').append(':');
                Object value = entry.getValue();
                if (value == null) {
                    builder.append("null");
                } else if (value instanceof String) {
                    builder.append('"').append(value.toString().replace("\\", "\\\\").replace("\"", "\\\""))
                            .append('"');
                } else if (value instanceof Number || value instanceof Boolean) {
                    builder.append(value);
                } else {
                    builder.append('"').append(value).append('"');
                }
            }
            builder.append("}");
            return builder.toString();
        }
    }
    static class DashboardHandler implements HttpHandler {

    private final RegistroController registroController = new RegistroController();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        setCorsHeaders(exchange);

        if ("OPTIONS".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 200, Map.of("message", "ok"));
            return;
        }

        if (!"GET".equals(exchange.getRequestMethod())) {
            sendJson(exchange, 405, Map.of("message", "Método no permitido"));
            return;
        }

        try {

            Map<String, Object> response =
                    registroController.obtenerDashboard();

            sendJson(exchange, 200, response);

        } catch (Exception ex) {

            sendJson(exchange, 500, Map.of(
                    "message",
                    ex.getMessage()));

        }

    }

    private void sendJson(HttpExchange exchange,
                          int statusCode,
                          Map<String,Object> payload) throws IOException {

        byte[] responseBytes =
                toJson(payload).getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders()
                .set("Content-Type","application/json");

        exchange.sendResponseHeaders(statusCode,responseBytes.length);

        try(OutputStream os = exchange.getResponseBody()){
            os.write(responseBytes);
        }

    }

    private String toJson(Map<String,Object> payload){

        StringBuilder builder = new StringBuilder("{");

        boolean first = true;

        for(Map.Entry<String,Object> entry : payload.entrySet()){

            if(!first){
                builder.append(",");
            }

            first = false;

            builder.append("\"")
                    .append(entry.getKey())
                    .append("\":");

            Object value = entry.getValue();

            if(value instanceof Number || value instanceof Boolean){

                builder.append(value);

            }else{

                builder.append("\"")
                        .append(value)
                        .append("\"");

            }

        }

        builder.append("}");

        return builder.toString();

    }

}
}
