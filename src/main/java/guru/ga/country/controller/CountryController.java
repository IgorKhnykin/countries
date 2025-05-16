package guru.ga.country.controller;

import guru.ga.country.model.CountryJson;
import guru.ga.country.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<CountryJson> getAllCountries() {
        return countryService.allCountries();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public CountryJson addCountry(@RequestBody CountryJson country) {
        return countryService.addCountry(country);
    }

    @PatchMapping("/edit")
    public CountryJson editCountry(@RequestBody CountryJson country) {
        return countryService.editCountry(country);
    }

    @GetMapping("/{id}")
    public CountryJson getCountryById(@PathVariable("id") String id) {
        return countryService.byId(id);
    }
}
