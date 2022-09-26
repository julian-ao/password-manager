package app.userbuilder;

public enum PasswordValidation{
    OK, //password is ok
    tooShort, //password is too short
    tooLong, //password is too long
    diversityError, //password does not contain one or more of the following: uppercase char, lowercase char, digit, symbol //!rename this

}