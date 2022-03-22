package repository;

import model.request.UserReg;

public class UserValidRepository {

    private UserValidRepository(){
    }
    public static UserReg getRegisterValidUser(){
        return UserReg.builder()
                .email("eve.holt@reqres.in") //add pattern regex
                .password("pistol")
                .build();
    }
}
