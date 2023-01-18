package com.crud.api.dto;

import com.crud.api.entity.User;

public record ViewUser(long id,String name, String surname) {
    public static ViewUser mapper(User user){
        return new ViewUser(user.getId(), user.getName(), user.getSurname());
    }
}
