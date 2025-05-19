package guru.ga.country.controller.graphqls;

import guru.ga.country.model.CountryJson;
import guru.ga.country.model.gql.CountryGql;
import guru.ga.country.model.gql.CountryGqlInput;
import guru.ga.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {

    private final CountryService countryService;

    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument CountryGqlInput input) {
        return countryService.addCountryGql(input);
    }

    @MutationMapping
    public CountryGql editCountry(@Argument CountryGqlInput input) {
        return countryService.editCountryGql(input);
    }
}
