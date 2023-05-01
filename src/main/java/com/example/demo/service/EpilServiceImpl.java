package com.example.demo.service;

import com.example.demo.model.Epil;
import com.example.demo.repository.EpilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EpilServiceImpl implements EpilService {
    private final EpilRepository epilRepository;

}
