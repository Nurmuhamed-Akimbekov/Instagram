package peaksoft.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.entity.Like;
import peaksoft.entity.Post;
import peaksoft.entity.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    private Long id;
    @Column(length = 500)
    private String comment;
    private LocalDate createAt;

    @ManyToOne(cascade = DETACH)
    private User user;

    @ManyToOne(cascade = DETACH)
    private Post post;

    @OneToMany(mappedBy = "comment", cascade = {REMOVE, PERSIST, MERGE})
    private List<Like> likes;

    @PrePersist
    public void preSave(){
        this.createAt = LocalDate.now();
    }

}