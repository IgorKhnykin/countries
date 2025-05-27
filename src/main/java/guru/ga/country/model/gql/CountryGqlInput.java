package guru.ga.country.model.gql;

import guru.ga.country.data.CountryEntity;
import jakarta.annotation.Nonnull;

public record CountryGqlInput(
        @Nonnull String country,
        @Nonnull String code,
        @Nonnull Integer totalArea) {
}
