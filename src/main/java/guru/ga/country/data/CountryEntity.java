package guru.ga.country.data;

import guru.ga.country.model.CountryJson;
import guru.ga.country.model.gql.CountryGqlInput;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "countries")
public class CountryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, columnDefinition = "UUID default gen_random_uuid()")
    private UUID id;

    @Column(name = "country", nullable = false)
    private String countryName;

    @Column(nullable = false)
    private String code;

    @Column(name = "total_area", nullable = false)
    private Integer totalArea;

    public static CountryEntity fromJson(CountryJson countryJson) {
        CountryEntity ce = new CountryEntity();
        ce.setCountryName(countryJson.countryName());
        ce.setCode(countryJson.code());
        ce.setTotalArea(countryJson.totalArea());
        return ce;
    }

    public static CountryEntity fromGql(CountryGqlInput countryGqlInput) {
        CountryEntity ce = new CountryEntity();
        ce.setCountryName(countryGqlInput.country());
        ce.setCode(countryGqlInput.code());
        ce.setTotalArea(countryGqlInput.totalArea());
        return ce;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CountryEntity that = (CountryEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
