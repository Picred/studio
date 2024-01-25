package test.films;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="flist")
public class FilmModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titolo;
    private String regista;
    private Integer copie_vendute;
    private String trama;


    public String getTrama() {
        return trama;
    }
    public void setTrama(String trama) {
        this.trama = trama;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitolo() {
        return titolo;
    }
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }
    public String getRegista() {
        return regista;
    }
    public void setRegista(String regista) {
        this.regista = regista;
    }
    public Integer getCopie_vendute() {
        return copie_vendute;
    }
    public void setCopie_vendute(Integer copie_vendute) {
        this.copie_vendute = copie_vendute;
    }



}
