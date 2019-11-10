package Entities;

import javax.persistence.*;

@Entity
@Table(name = "artikel")
@NamedQueries(value =
        {
                @NamedQuery(name= ArtikelEntity.GET_ALL,
                        query = "SELECT a FROM artikel a"),
                @NamedQuery(name= ArtikelEntity.GET_NAKUPOVALNI_SEZNAM_BY_ID,
                        query = "SELECT a FROM artikel a WHERE a.id = :id"),
                @NamedQuery(name= ArtikelEntity.GET_NAKUPOVALNI_SEZNAM_BY_NAZIV,
                        query = "SELECT a FROM artikel a WHERE a.status = :status"),
                @NamedQuery(name= ArtikelEntity.GET_NAKUPOVALNI_SEZNAM_BY_ZALOGA,
                        query = "SELECT a FROM artikel a WHERE a.zaloga = :zaloga")
        })
public class ArtikelEntity {

    public static final String GET_ALL = "ArtikelEntity.getAll";
    public static final String GET_NAKUPOVALNI_SEZNAM_BY_ID = "ArtikelEntity.getById";
    public static final String GET_NAKUPOVALNI_SEZNAM_BY_ZALOGA = "ArtikelEntity.getByZaloga";
    public static final String GET_NAKUPOVALNI_SEZNAM_BY_NAZIV = "ArtikelEntity.getByNaziv";

    private int id;
    private String naziv;
    private Boolean zaloga;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "naziv")
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Basic
    @Column(name = "zaloga")
    public Boolean getZaloga() {
        return zaloga;
    }

    public void setZaloga(Boolean zaloga) {
        this.zaloga = zaloga;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtikelEntity that = (ArtikelEntity) o;

        if (id != that.id) return false;
        if (naziv != null ? !naziv.equals(that.naziv) : that.naziv != null) return false;
        if (zaloga != null ? !zaloga.equals(that.zaloga) : that.zaloga != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (naziv != null ? naziv.hashCode() : 0);
        result = 31 * result + (zaloga != null ? zaloga.hashCode() : 0);
        return result;
    }
}
