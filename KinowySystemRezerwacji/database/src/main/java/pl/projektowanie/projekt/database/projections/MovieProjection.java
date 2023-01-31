package pl.projektowanie.projekt.database.projections;

import java.time.LocalDate;

public interface MovieProjection {
    Long getId();

    String getTitle();

    String getCategory();

    String getImageUrl();

    int getLength();

    int getAge();

    LocalDate getReleaseDate();

    String getDescription();

    //    setters for test

    void setId(Long id);

    void setTitle(String title);

    void setCategory(String category);

    void setImageUrl(String imageUrl);

    void setLength(int length);

    void setReleaseDate(LocalDate releaseDate);

    void setDescription(String description);
}
