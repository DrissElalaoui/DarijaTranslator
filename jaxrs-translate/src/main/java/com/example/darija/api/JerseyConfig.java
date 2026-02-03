package com.example.darija.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
public class JerseyConfig extends Application {
    // Jersey تلقائياً غادي يلقى TranslateResource + HelloResource
}
