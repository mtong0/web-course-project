package com.example.backend.service;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NLIServiceTest {

    NLIService nliService = new NLIService();
    @Test
    void getRegex() {
        Set<String> stringSet = new HashSet<>(Arrays.asList("food"));
        System.out.println(nliService.getRegex(stringSet));
    }
}