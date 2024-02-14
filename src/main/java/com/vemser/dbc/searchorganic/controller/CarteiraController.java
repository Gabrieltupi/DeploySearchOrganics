package com.vemser.dbc.searchorganic.controller;

import com.vemser.dbc.searchorganic.controller.interfaces.ICarteiraController;
import com.vemser.dbc.searchorganic.dto.carteira.CarteiraDTO;
import com.vemser.dbc.searchorganic.service.CarteiraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carteira")
@RequiredArgsConstructor
public class CarteiraController implements ICarteiraController {
    private final CarteiraService carteiraService;

    @Override
    @GetMapping
    public ResponseEntity<CarteiraDTO> obterSaldo() throws Exception {
        return new ResponseEntity<>(carteiraService.obterSaldo(), HttpStatus.OK);
    }
}
