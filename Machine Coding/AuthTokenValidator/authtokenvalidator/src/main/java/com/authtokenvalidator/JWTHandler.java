package com.authtokenvalidator;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class JWTHandler {
    private static final Long EXPIRATION_DURATION = 15 * 1000L; // 15 sec
    
    public boolean validateJwt(String token) {
        try {
            if (token == null || token.isBlank()) throw new IllegalArgumentException("JWT cannot be null / blank");
            String[] parts = token.split("\\.");
            Arrays.asList(parts).forEach(System.out::println);
            if (parts.length != 3) throw new IllegalArgumentException("JWT should have exactly 3 parts");

            String header = parts[0], payload = parts[1], signature = parts[2];
            validate(header);
            validate(payload);
            validate(signature);

            byte[] bytes = Base64.getDecoder().decode(header);
            String decodedHeader = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("Decoded Header : " + decodedHeader);

            Map<String, String> decodedPayload = extractPayload(payload);
            if (decodedPayload == null || decodedPayload.isEmpty()) throw new IllegalArgumentException("Invalid payload");
            System.out.println("Decoded Payload : " + decodedPayload);

            String iat = decodedPayload.get("iat");
            if (iat == null) throw new IllegalArgumentException("Invalid payload. Issued at time missing");

            Long issuedAtTime = Long.parseLong(iat);
            Long expiryTime = issuedAtTime + EXPIRATION_DURATION;
            if (expiryTime <= System.currentTimeMillis()) {
                System.out.println("Token expired.");
                return false;
            }

            if (!isValidSignature(header, payload, signature)) return false;

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean isValidSignature(String header, String payload, String signature) {
        return true;
    }

    private Map<String, String> extractPayload(String encodedPayload) {
        byte[] bytes = Base64.getDecoder().decode(encodedPayload);
        String payload = new String(bytes, StandardCharsets.UTF_8);
        if (!payload.startsWith("{") || !payload.endsWith("}")) throw new IllegalArgumentException("Invalid payload");
        Map<String, String> pairs = new HashMap<>();
        
        String trimmedPayload = payload.substring(1, payload.length() - 1);
        String[] keyValPairs = trimmedPayload.split(",");

        for (String pair : keyValPairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length != 2) return null;
            String key = keyValue[0].trim(), value = keyValue[1].trim();
            
            key = key.replaceAll("^\"|\"$", "");
            value = value.replaceAll("^\"|\"$", "");
            pairs.put(key, value);
        }
        
        if (pairs.isEmpty()) throw new IllegalArgumentException("Empty payload");
        return pairs;
    }

    private void validate(String type) {
        if (type == null || type.isBlank()) throw new IllegalArgumentException("Invalid JWT. JWT cannot have empty arguments");
    }

    public void run(String token) {
        if (!validateJwt(token)) return;
        System.out.println("User validated to run the method");
    }
}