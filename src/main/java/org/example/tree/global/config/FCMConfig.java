package org.example.tree.global.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FCMConfig {

    @Value("${firebase.key-path}")
    private String fcmKeyPath;

    @PostConstruct
    public void initialize() {
        try {
            final InputStream refreshToken = new ClassPathResource(fcmKeyPath).getInputStream();

            final FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FCM Setting Completed");
            }

        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
