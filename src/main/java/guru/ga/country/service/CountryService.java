package guru.ga.country.service;

import guru.ga.country.model.CountryJson;
import guru.ga.country.model.gql.CountryGql;
import guru.ga.country.model.gql.CountryGqlInput;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CountryService {

    List<CountryJson> allCountries();

    CountryJson byId(String id);

    CountryJson addCountry(CountryJson country);

    CountryJson editCountry(CountryJson country);

    Slice<CountryGql> allCountriesGql(Pageable pageable);

    CountryGql byIdGql(String id);

    CountryGql addCountryGql(CountryGqlInput country);

    CountryGql editCountryGql(CountryGqlInput country);
}
