package com.project.hypeball.service;

import com.project.hypeball.domain.Point;
import com.project.hypeball.domain.Review;
import com.project.hypeball.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Point get(Long id) {
        return pointRepository.findById(id).orElse(null);}

    public List<Point> findAll() {
        return pointRepository.findAll();
    }
}
