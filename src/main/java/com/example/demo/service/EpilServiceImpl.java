package com.example.demo.service;

import com.example.demo.model.Epil;
import com.example.demo.repository.EpilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EpilServiceImpl implements EpilService {
    private final EpilRepository epilRepository;
    public int MANDRIK_MARCH = 0;
    public int MANDRIK_APRIL = 0;
    public int SHAMILOVA_MARCH = 0;
    public int MAZIKOVA_MARCH = 0;

    public void getEarnings() {
        MANDRIK_MARCH = 0;
        MANDRIK_APRIL = 0;
        SHAMILOVA_MARCH = 0;
        MAZIKOVA_MARCH = 0;
        for (Epil ep : epilRepository.findAllByMasterName("Мандрик")) {
            if (ep.getDate().contains(".03.2023")) {
                MANDRIK_MARCH += Integer.parseInt(ep.getPrice());
            } else if (ep.getDate().contains(".04.2023")) {
                MANDRIK_APRIL += Integer.parseInt(ep.getPrice());
            }
        }

        for (Epil ep : epilRepository.findAllByMasterName("Шамилова")) {
            SHAMILOVA_MARCH += Integer.parseInt(ep.getPrice());
        }

        for (Epil ep : epilRepository.findAllByMasterName("Мазикова")) {
            MAZIKOVA_MARCH += Integer.parseInt(ep.getPrice());
        }
    }
}