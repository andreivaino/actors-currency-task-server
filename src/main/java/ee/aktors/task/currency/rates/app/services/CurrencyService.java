package ee.aktors.task.currency.rates.app.services;

import ee.aktors.task.currency.rates.app.domains.Currency;
import ee.aktors.task.currency.rates.app.dto.CurrencyDTO;
import ee.aktors.task.currency.rates.app.exceptions.CustomException;
import ee.aktors.task.currency.rates.app.repositories.CurrencyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @PostConstruct
    private void configureMapper() {
        modelMapper.typeMap(CurrencyDTO.class,Currency.class).addMappings(mapper -> {
            mapper.skip(Currency::setCreateDate);
            mapper.skip(Currency::setUpdateDate);
        });
    }

    @Transactional
    public CurrencyDTO getCurrencyById(Long id) throws CustomException {
        Currency currency = currencyRepository.findById(id).orElseThrow(() -> {
            log.warn("getCurrencyById: currency with id: " + id);
            return new CustomException("No currency found with id " + id, HttpStatus.NOT_FOUND);
        });
        return modelMapper.map(currency, CurrencyDTO.class);
    }

    @Transactional
    public List<CurrencyDTO> getAllCurrencies() {
        List<Currency> currencyList = currencyRepository.findAll();
        return currencyList.stream()
                .map(currency -> modelMapper.map(currency, CurrencyDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCurrencyById(Long id) throws CustomException {
        if (!currencyRepository.existsById(id)) {
            log.warn("deleteCurrencyById: currency with not existed id: " + id);
            throw new CustomException("No currency found with id " + id, HttpStatus.NOT_FOUND);
        } else {
            this.currencyRepository.deleteById(id);
        }
    }

    @Transactional
    public CurrencyDTO updateCurrency(CurrencyDTO currencyDto) throws CustomException {
        if (currencyDto.getId() == null) {
            log.warn("updateCurrency: currency with null id");
            throw new CustomException("Currency id can't be null while updating", HttpStatus.NOT_FOUND);
        } else if (!currencyRepository.existsById(currencyDto.getId())) {
            log.warn("updateCurrency: currency with not existed id: " + currencyDto.getId());
            throw new CustomException("No currency found with id " + currencyDto.getId(), HttpStatus.NOT_FOUND);
        } else {
            currencyDto.setCode(currencyDto.getCode().toUpperCase());
            Currency currency = currencyRepository.saveAndFlush(modelMapper.map(currencyDto, Currency.class));
            entityManager.refresh(currency);
            return modelMapper.map(currency, CurrencyDTO.class);
        }
    }

    @Transactional
    public CurrencyDTO createCurrency(CurrencyDTO currencyDto) {
        currencyDto.setCode(currencyDto.getCode().toUpperCase());
        Currency currency = modelMapper.map(currencyDto, Currency.class);
        Currency savedCurrency = currencyRepository.save(currency);
        entityManager.refresh(savedCurrency);
        return modelMapper.map(savedCurrency, CurrencyDTO.class);
    }

}
