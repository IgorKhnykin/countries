package guru.ga.country.service;

import guru.ga.country.data.CountryEntity;
import guru.ga.country.data.CountryRepository;
import guru.ga.country.exception.CountryNotFoundException;
import guru.ga.country.model.CountryJson;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Transactional(readOnly = true)
    @Override
    @Nonnull
    public List<CountryJson> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(CountryJson::fromEntity)
                .toList();
    }

    @Transactional
    @Override
    @Nonnull
    public CountryJson addCountry(@Nonnull CountryJson country) {
        countryRepository.save(CountryEntity.fromJson(country));
        return country;
    }

    @Transactional
    @Override
    @Nonnull
    public CountryJson editCountry(@Nonnull CountryJson country) {
        CountryEntity countryFromDb = countryRepository.findByCountryName(country.countryName());
        countryFromDb.setTotalArea(country.totalArea());
        countryFromDb.setCode(country.code());
        CountryEntity updatedEntity = countryRepository.save(countryFromDb);
        return CountryJson.fromEntity(updatedEntity);
    }

    @Override
    public CountryJson byId(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .stream()
                .map(CountryJson::fromEntity)
                .findAny().orElseThrow(CountryNotFoundException::new);
    }
}
