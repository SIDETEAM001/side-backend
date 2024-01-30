package com.sideteam.groupsaver.global.config.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sideteam.groupsaver.global.exception.BusinessException;
import com.sideteam.groupsaver.global.exception.firebase.FirebaseErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FcmConfig {

    @Value("${firebase.fcm.config-path}")
    private String fcmConfigPath;

    @Bean
    FirebaseMessaging firebaseMessaging() {
        FirebaseApp firebaseApp = FirebaseApp.getApps().stream()
                .filter(app -> FirebaseApp.DEFAULT_APP_NAME.equals(app.getName()))
                .findFirst()
                .orElseGet(this::initializeNewFirebaseApp);

        return FirebaseMessaging.getInstance(firebaseApp);
    }

    private FirebaseApp initializeNewFirebaseApp() {
        try {
            InputStream refreshToken = new ClassPathResource(fcmConfigPath).getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException ioException) {
            throw new BusinessException(FirebaseErrorCode.FAILED_INIT_FIREBASE_APP, ioException.getMessage());
        }

    }

}
