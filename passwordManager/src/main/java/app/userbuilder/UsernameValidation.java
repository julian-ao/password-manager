package app.userbuilder;

public enum UsernameValidation{
    OK, //username is ok
    alreadyTaken, //username is already in use by another user
    invalidCharacters, //the username contains illegal characters 
    tooShort, //username is too shrot
    tooLong //the username is too long
}