package test.courses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

@Entity
public class CourseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome_corso;
    private String descrizione;
    private Integer crediti;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getNome_corso() {
        return nome_corso;
    }
    public void setNome_corso(String nome_corso) {
        this.nome_corso = nome_corso;
    }
    
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
    public Integer getCrediti() {
        return crediti;
    }
    public void setCrediti(Integer crediti) {
        this.crediti = crediti;
    }
}