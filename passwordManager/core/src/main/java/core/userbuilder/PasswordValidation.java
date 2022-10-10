package core.userbuilder;

public enum PasswordValidation{
    OK, //password is ok
    diversityError, //password does not contain one or more of the following: uppercase char, lowercase char, digit, symbol //!rename this
    tooShort, //password is too short
    tooLong, //password is too long
}