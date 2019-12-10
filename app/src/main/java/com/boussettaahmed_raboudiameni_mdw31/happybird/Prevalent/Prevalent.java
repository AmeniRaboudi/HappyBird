package com.boussettaahmed_raboudiameni_mdw31.happybird.Prevalent;

import com.boussettaahmed_raboudiameni_mdw31.happybird.Model.User;


public class Prevalent {
    public static User currentOnlineUser;

    public static final String UserPhoneKey = "UserPhone";
    public static final String UserPasswordKey = "UserPassword";

    public static User getCurrentOnlineUser() {
        return currentOnlineUser;
    }

    public static void setCurrentOnlineUser(User currentOnlineUser) {
        Prevalent.currentOnlineUser = currentOnlineUser;
    }

    public static String getUserPhoneKey() {
        return UserPhoneKey;
    }

    public static String getUserPasswordKey() {
        return UserPasswordKey;
    }
}
