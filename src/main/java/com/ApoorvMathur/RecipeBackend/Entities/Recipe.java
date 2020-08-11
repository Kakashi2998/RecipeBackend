package com.ApoorvMathur.RecipeBackend.Entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "bookmarked")
    private boolean bookmarked;

    @OneToMany(mappedBy = "recipe")
    private List<Ingredient> ingredients;

    public void toggleBookmark(){
        bookmarked = !bookmarked;
    }
}
