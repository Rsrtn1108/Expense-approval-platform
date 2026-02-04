package org.example.domain.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    protected Role() {
      //JPA
    }

    public String getName(){
        return name;
    }

    public UUID getId(){
        return id;
    }
}
