package app.userbuilder;


public enum UsernameValidation{
    OK, //username is ok
    tooShort, //username is too shrot
    allreadyTaken, //username is allready in use by another user
    invalidCharacters, //the username contains illegal characters 
    tooLong //the username is too long
    
}

