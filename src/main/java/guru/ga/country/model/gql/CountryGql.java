package guru.ga.country.model.gql;

import guru.ga.country.data.CountryEntity;

import java.util.UUID;

public record CountryGql(UUID id, String country, String code, Integer totalArea) {

    public static CountryGql fromEntity(CountryEntity ce) {
        return new CountryGql(ce.getId(), ce.getCountryName(), ce.getCode(), ce.getTotalArea());
    }
}
