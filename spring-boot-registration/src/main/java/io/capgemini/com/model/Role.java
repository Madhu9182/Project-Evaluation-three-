package io.capgemini.com.model;

public enum Role {
    USER(new String[] {"read","write","update","delete"});

    String[] authority;
    Role(String[] strings) {
        this.authority = strings;
    }

    String[] getAuthorityName(){
        return this.authority;
    }

}
