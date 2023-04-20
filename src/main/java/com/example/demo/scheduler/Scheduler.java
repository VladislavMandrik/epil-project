package com.example.demo.scheduler;

import com.example.demo.repository.EpilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final EpilRepository epilRepository;

    @Scheduled(fixedRate = 900000)
    public void scheduleFixedRateTask() {

        URL url = null;
        HttpURLConnection connection = null;

        try {
            url = new URL("https://epil.onrender.com/get");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            System.out.println(LocalDateTime.now() + " " + connection.getRequestMethod() + " warm up request was performed with status " + connection.getResponseCode());
        } catch (IOException e) {
            System.err.println(LocalDateTime.now() + " " + "An error occurred during warm up request" + url);
        } finally {
            Objects.requireNonNull(connection).disconnect();
        }
    }

    @Scheduled(fixedRate = 86400000)
    public void scheduleBackUpDBTask() {

        try (PrintWriter printWriter = new PrintWriter(new FileWriter("backup.txt"))) {
            epilRepository.findAll().forEach(epil -> printWriter.write(epil.getId() + "," +
                    epil.getName() + "," +
                    epil.getNumber() + "," +
                    epil.getDate() + "," +
                    epil.getZone() + "," +
                    epil.getParams() + "," +
                    epil.getPrice() + "," +
                    epil.getComments() + "," +
                    epil.getMasterName() + "\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


