package guru.ga.country.domain;

import guru.ga.country.data.CountryEntity;

public record Country(String countryName, String code, Integer totalArea) {

    public static Country fromEntity(CountryEntity ce) {
        return new Country(ce.getCountryName(), ce.getCode(), ce.getTotalArea());
    }
}
