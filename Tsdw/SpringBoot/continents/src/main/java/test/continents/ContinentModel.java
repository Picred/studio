package test.continents;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
@Table(name="continents")
public class ContinentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer abitanti;
    private String nazioni;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Integer getAbitanti() {
        return abitanti;
    }
    public void setAbitanti(Integer abitanti) {
        this.abitanti = abitanti;
    }
    public String getNazioni() {
        return nazioni;
    }
    public void setNazioni(String nazioni) {
        this.nazioni = nazioni;
    }



}
