package dev.codeio.HelloWorld.models;



import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.processing.Pattern;

@Entity
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
@Data   //For all annotations like getter,setter,constructor,etc,...
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @NotBlank
//    @Pattern(regexp = "^[0-9]{10}$")
//    @Size(min = 5, max = 15)
    String title;

//    @NotBlank
//    @Min(1)
//    @Max(10)
    @Schema(name = "title", example = "Complete Spring Boot")
    String description;

   // @NotNull
    Boolean isCompleted;


}