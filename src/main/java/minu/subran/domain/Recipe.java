package minu.subran.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue
    @Column(name = "recipe_id")
    private Long id;

    private String bread;
    private String mainStuff;
}
