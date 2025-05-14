package guru.ga.country.model;

import guru.ga.country.data.CountryEntity;

public record CountryJson(String countryName, String code, Integer totalArea) {

    public static CountryJson fromEntity(CountryEntity ce) {
        return new CountryJson(ce.getCountryName(), ce.getCode(), ce.getTotalArea());
    }
}
