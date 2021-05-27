package adatb;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Adatbazis {

    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String nev;

    @Column(nullable = false)
    private String cim;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private int osszeg;
}
