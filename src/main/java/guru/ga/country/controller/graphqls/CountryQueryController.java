package guru.ga.country.controller.graphqls;

import guru.ga.country.model.CountryJson;
import guru.ga.country.model.gql.CountryGql;
import guru.ga.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CountryQueryController {

    private final CountryService countryService;

    @Autowired
    public CountryQueryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @QueryMapping
    public Slice<CountryGql> countries(@Argument int page, @Argument int size) {
        return countryService.allCountriesGql(PageRequest.of(page, size));
    }

    @QueryMapping
    public CountryGql country(@Argument String id) {
        return countryService.byIdGql(id);
    }
}
