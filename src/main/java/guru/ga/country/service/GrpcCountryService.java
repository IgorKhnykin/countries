package guru.ga.country.service;

import com.google.protobuf.Empty;
import guru.ga.country.model.CountryJson;
import guru.qa.countries.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@GrpcService
public class GrpcCountryService extends CountriesServiceGrpc.CountriesServiceImplBase {

    private static final Logger LOG = LoggerFactory.getLogger(GrpcCountryService.class);

    private final CountryService countryService;

    public GrpcCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    public void getAllCountries(Empty request, StreamObserver<CountriesResponse> responseObserver) {
        final List<CountryJson> countries = countryService.allCountries();

        CountriesResponse.Builder countriesBuilder = CountriesResponse.newBuilder();

        countries.forEach(cj -> {
            CountryResponse cr = CountryResponse.newBuilder()
                    .setCountryName(cj.countryName())
                    .setCode(cj.code())
                    .setTotalArea(cj.totalArea())
                    .build();
            countriesBuilder.addAllCountries(cr);
        });

        responseObserver.onNext(countriesBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getCountry(CountryId request, StreamObserver<CountryResponse> responseObserver) {
        final CountryJson country = countryService.byId(request.getId());
        responseObserver.onNext(CountryResponse.newBuilder()
                .setCountryName(country.countryName())
                .setCode(country.code())
                .setTotalArea(country.totalArea())
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<CountryRequest> addCountry(StreamObserver<CountriesAddedCount> responseObserver) {
        return new StreamObserver<CountryRequest>() {
            AtomicInteger count = new AtomicInteger();

            @Override
            public void onNext(CountryRequest value) {
                countryService.addCountry(
                        new CountryJson(
                                value.getCountryName(),
                                value.getCode(),
                                value.getTotalArea()
                        )
                );
                count.incrementAndGet();
            }

            @Override
            public void onError(Throwable t) {
                responseObserver.onError(Status.INTERNAL
                        .withDescription("Не получилось добавить страну" + t)
                        .asRuntimeException());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(
                        CountriesAddedCount.newBuilder()
                                .setCount(count.get())
                                .build()
                );
                responseObserver.onCompleted();
            }
        };
    }


    @Override
    public void editCountry(CountryRequest request, StreamObserver<CountryResponse> responseObserver) {
        CountryJson country = countryService.editCountry(
                new CountryJson(
                        request.getCountryName(),
                        request.getCode(),
                        request.getTotalArea()
                )
        );
        responseObserver.onNext(CountryResponse.newBuilder()
                .setCode(country.code())
                .setTotalArea(country.totalArea())
                .build());
        responseObserver.onCompleted();
    }
}
