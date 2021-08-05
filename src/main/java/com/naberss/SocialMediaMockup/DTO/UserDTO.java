package com.naberss.SocialMediaMockup.DTO;

import java.io.Serializable;
import java.util.Objects;

import com.naberss.SocialMediaMockup.entities.User;

public class UserDTO implements Serializable {

    private static final long serialVersionUID = -6872531064472586865L;
    private String id;
    private String name;
    private String email;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(getId(), userDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
