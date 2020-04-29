package ee.aktors.task.currency.rates.app.repositories;

import ee.aktors.task.currency.rates.app.domains.Currency;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {

}
