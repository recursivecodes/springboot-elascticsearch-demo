package codes.recursive.searchdemo.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "sbfavorites")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Data
@Builder
public class Favorite {
    @Id
    private String id;
    @Field(type = FieldType.Text, name = "favoriteArtist")
    private String favoriteArtist;
    @Field(type = FieldType.Text, name = "favoriteBeer")
    private String favoriteBeer;
    @Field(type = FieldType.Text, name = "favoriteBook")
    private String favoriteBook;
    @Field(type = FieldType.Text, name = "favoriteCat")
    private String favoriteCat;
    @Field(type = FieldType.Text, name = "favoriteColor")
    private String favoriteColor;
    @Field(type = FieldType.Text, name = "favoriteSuperhero")
    private String favoriteSuperhero;
}
