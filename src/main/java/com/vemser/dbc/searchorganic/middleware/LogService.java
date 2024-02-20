package com.vemser.dbc.searchorganic.middleware;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.dto.log.LogDTO;
import com.vemser.dbc.searchorganic.model.Log;
import com.vemser.dbc.searchorganic.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public Page<LogDTO> findAll(Pageable pageable) {
        return logRepository.findAll(pageable).map(log -> objectMapper.convertValue(log, LogDTO.class));
    }

    public LogDTO save(Log log) {
        return objectMapper.convertValue(logRepository.save(log), LogDTO.class);
    }
}
