package org.example.kickoffcommunity.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "이름", nullable = false, unique = true)
    private String name;

    @Column(name = "팀장", nullable = false)
    private String leaderName;

    @Column(name = "소개")
    private String desc;

    @Column(name = "imgpath")
    private String imgPath;

    @Column(name = "type")
    private String type;
}
