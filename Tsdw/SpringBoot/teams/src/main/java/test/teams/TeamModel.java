package test.teams;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="teams")
public class TeamModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer giocatori;
    private String nazione;


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
    public Integer getGiocatori() {
        return giocatori;
    }
    public void setGiocatori(Integer giocatori) {
        this.giocatori = giocatori;
    }
    public String getNazione() {
        return nazione;
    }
    public void setNazione(String nazione) {
        this.nazione = nazione;
    }

}
