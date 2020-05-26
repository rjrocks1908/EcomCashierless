package com.haxon.capstoneproject.Prevalent;

import com.haxon.capstoneproject.Models.Users;

//For remember me feature and forget password it basically contains the common data of users
public class Prevalent {

    private static Users currentOnlineUser; //user who is going to login
    //these variables will store the user phone and password for the remember me feature.
    public static final String userPhoneKey = "UserPhone";
    public static final String userPasswordKey = "UserPassword";

}
