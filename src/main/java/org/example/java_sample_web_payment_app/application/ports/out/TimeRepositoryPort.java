package org.example.java_sample_web_payment_app.application.ports.out;

import java.time.LocalDateTime;

public interface TimeRepositoryPort {

    LocalDateTime getCurrentTime();
}
