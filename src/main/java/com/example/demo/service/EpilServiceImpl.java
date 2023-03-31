package com.example.demo.service;

import com.example.demo.model.Epil;
import com.example.demo.repository.EpilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.text.DecimalFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class EpilServiceImpl implements EpilService {
    private final EpilRepository epilRepository;
    public int i = 0;
    public int apr = 0;
    public int in = 0;
    public int inter = 0;

    public void getEarnings() {
        i = 0;
        apr = 0;
        in = 0;
        inter = 0;
        for (Epil ep : epilRepository.findAllByMasterName("Мандрик")) {
            if (ep.getDate().contains(".03.2023")) {
                i += Integer.parseInt(ep.getPrice());
            } else if (ep.getDate().contains(".04.2023")) {
                apr += Integer.parseInt(ep.getPrice());
            }
        }
        System.out.println("Март Мандрик = " + i + "р.");
        System.out.println("Апрель Мандрик = " + apr + "р.");

        for (Epil ep : epilRepository.findAllByMasterName("Шамилова")) {
            in += Integer.parseInt(ep.getPrice());
        }
        System.out.println("Шамилова = " + in);

        for (Epil ep : epilRepository.findAllByMasterName("Мазикова")) {
            inter += Integer.parseInt(ep.getPrice());
        }
        System.out.println("Мазикова = " + inter);
    }
}