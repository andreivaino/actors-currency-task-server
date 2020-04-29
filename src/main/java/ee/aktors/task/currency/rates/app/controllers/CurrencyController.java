package ee.aktors.task.currency.rates.app.controllers;

import ee.aktors.task.currency.rates.app.dto.CurrencyDTO;
import ee.aktors.task.currency.rates.app.exceptions.CustomException;
import ee.aktors.task.currency.rates.app.services.CurrencyService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    private CurrencyService currencyService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCurrencyCode(@PathVariable Long id) throws CustomException {
        CurrencyDTO currencyDto = currencyService.getCurrencyById(id);
        log.info("Retrieving currency (" + currencyDto.getCode() + ", " + currencyDto.getName() + ")");
        return new ResponseEntity<>(currencyDto, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCurrencies() {
        List<CurrencyDTO> allCurrencies = currencyService.getAllCurrencies();
        log.info("Retrieving all currencies");
        return new ResponseEntity<>(allCurrencies, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createCurrency(@Validated @RequestBody CurrencyDTO currencyDto) throws CustomException {
        CurrencyDTO createdCurrencyDTO = currencyService.createCurrency(currencyDto);
        log.info("Currency (" + createdCurrencyDTO.getCode() + ", " + currencyDto.getName() + ") created");
        return new ResponseEntity<>(createdCurrencyDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id) throws CustomException {
        currencyService.deleteCurrencyById(id);
        log.info("Currency with id " + id + " deleted");
        return new ResponseEntity<>("Currency with id " + id + " deleted", HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<?> updateCurrency(@Validated @RequestBody CurrencyDTO currencyDto) throws CustomException {
        CurrencyDTO updatedCurrencyDTO = currencyService.updateCurrency(currencyDto);
        log.info("Currency (" + updatedCurrencyDTO.getCode() + ", " + currencyDto.getName() + ") updated");
        return new ResponseEntity<>(updatedCurrencyDTO, HttpStatus.OK);
    }


}
