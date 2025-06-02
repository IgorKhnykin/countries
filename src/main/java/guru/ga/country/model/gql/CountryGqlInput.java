package guru.ga.country.model.gql;

import jakarta.annotation.Nonnull;

public record CountryGqlInput(
        @Nonnull String country,
        @Nonnull String code,
        @Nonnull Integer totalArea) {
}
