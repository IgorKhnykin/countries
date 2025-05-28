package guru.ga.country.service;

import guru.ga.country.data.CountryEntity;
import guru.ga.country.data.CountryRepository;
import guru.ga.country.exception.CountryNotFoundException;
import guru.ga.country.model.CountryJson;
import guru.ga.country.model.gql.CountryGql;
import guru.ga.country.model.gql.CountryGqlInput;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public CountryJson byId(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .stream()
                .map(CountryJson::fromEntity)
                .findAny().orElseThrow(CountryNotFoundException::new);
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

    @Transactional(readOnly = true)
    @Override
    @Nonnull
    public Page<CountryGql> allCountriesGql(Pageable pageable) {
        return countryRepository.findAll(pageable).
                map(CountryGql::fromEntity);
    }

    @Override
    public CountryGql byIdGql(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .stream()
                .map(CountryGql::fromEntity)
                .findAny().orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public CountryGql addCountryGql(CountryGqlInput country) {
        CountryEntity countryEntity = countryRepository.save(CountryEntity.fromGql(country));
        return CountryGql.fromEntity(countryEntity);
    }

    @Override
    public CountryGql editCountryGql(CountryGqlInput country) {
        CountryEntity countryEntity = countryRepository.findByCountryName(country.country());
        countryEntity.setTotalArea(country.totalArea());
        countryEntity.setCode(country.code());
        CountryEntity updatedEntity = countryRepository.save(countryEntity);
        return CountryGql.fromEntity(updatedEntity);
    }
}
