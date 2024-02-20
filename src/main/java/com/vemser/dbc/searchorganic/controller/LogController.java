package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.dto.log.LogDTO;
import com.vemser.dbc.searchorganic.middleware.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/log")
public class LogController {
    private final LogService logService;

    @GetMapping
    public ResponseEntity<Page<LogDTO>> findAll(@PageableDefault(page = 0, size = 10, sort = "data", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(logService.findAll(pageable));
    }
}
