package com.javatechie.spring.ws.api.model;

import com.javatechie.spring.ws.api.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Man {

    String id;
    String name;
  //  int tokenId;
    int rank;
    Status status;

    public Man() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Man man = (Man) o;
        return id.equals(man.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
