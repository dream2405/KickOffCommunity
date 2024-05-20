package org.example.kickoffcommunity.database.team_member;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "team_member")
@AllArgsConstructor
@NoArgsConstructor
public class TeamMember {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "팀이름")
    private String teamName;

    @Column(name = "팀원")
    private String memberName;
}
