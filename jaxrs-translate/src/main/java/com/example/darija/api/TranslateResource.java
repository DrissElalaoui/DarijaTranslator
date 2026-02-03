package com.example.darija.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/translate")
public class TranslateResource {

    private static final String OPENROUTER_API_KEY = "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";
    private static final String OPENROUTER_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String OPENROUTER_MODEL = "google/gemma-3-27b-it:free";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testHello() {
        return "Hello World!";
    }

    @POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response translateText(Map<String, String> input) {
    String text = input.get("text");
    String translation = "Erreur: impossible de traduire";

    try {
        // Prompt simplifié: juste la traduction en darija sans description
        Map<String, Object> payload = new HashMap<>();
        payload.put("model", OPENROUTER_MODEL);
        Map<String, String>[] messages = new Map[1];
        Map<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", "Traduire ce texte en darija MAROCAINE écrite uniquement avec les lettres arabes, sans explication, sans mots en latin, sans salutations supplémentaires, juste la traduction: " + text);
        messages[0] = msg;
        payload.put("messages", messages);

        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(payload);

        // Envoyer la requête HTTP POST
        URL url = new URL(OPENROUTER_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + OPENROUTER_API_KEY);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonPayload.getBytes("UTF-8"));
        }

        // Lire la réponse
        Map<String, Object> responseMap = mapper.readValue(conn.getInputStream(), Map.class);
        if (responseMap.containsKey("choices")) {
            Object choices = responseMap.get("choices");
            if (choices instanceof java.util.List) {
                Map firstChoice = (Map) ((java.util.List) choices).get(0);
                if (firstChoice.containsKey("message")) {
                    Map msgResp = (Map) firstChoice.get("message");
                    translation = (String) msgResp.get("content");
                }
            }
        }

        // Supprimer les sauts de ligne et espaces inutiles
        translation = translation.trim();

    } catch (Exception e) {
        e.printStackTrace();
    }

    Map<String, String> output = new HashMap<>();
    output.put("translation", translation);
    return Response.ok(output).build();
}
}
