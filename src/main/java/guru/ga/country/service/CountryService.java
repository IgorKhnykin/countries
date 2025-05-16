package guru.ga.country.service;

import guru.ga.country.model.CountryJson;

import java.util.List;

public interface CountryService {

    List<CountryJson> allCountries();

    CountryJson addCountry(CountryJson country);

    CountryJson editCountry(CountryJson country);

    CountryJson byId(String id);
}
