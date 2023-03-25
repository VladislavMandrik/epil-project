package com.example.demo.service;

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

    public void getEpil() {

    }
}