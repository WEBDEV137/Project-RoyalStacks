const regex = (function () {

    const MIN_USERNAME_LENGTH = 3;
    const MAX_USERNAME_LENGTH = 20;
    const BSN_LENGTH = 9;

    const USERNAME_REGEX = '^[a-zA-Z0-9_-]';
    let username = new RegExp(USERNAME_REGEX + '{' + MIN_USERNAME_LENGTH + ',' + MAX_USERNAME_LENGTH + '}$');
    let bsn = new RegExp('^\\d{' + BSN_LENGTH + '}$');

    const EMAIL = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    const NAMES = /^([-a-zA-Z\-']+(\s+[-a-zA-Z\-']+)*){2,100}$/;
    const PHONE_NUMBER = /^(((0)[1-9]{2}[0-9][-]?[1-9][0-9]{5})|((\\+31|0|0031)[1-9][0-9][-]?[1-9][0-9]{6}))$|^(((\\+31|0|0031)6)[1-9][0-9]{7})$/;
    const POSTAL_CODE = /^[1-9][0-9]{3} ?(?!sa|SA|Sa|sA|sd|SD|sD|Sd|ss|SS|sS)[a-zA-Z]{2}$/;
    const LOWERCASE = /[a-z]/g;
    const UPPERCASE = /[A-Z]/g;
    const NUMBERS = /[0-9]/g;
    const SPECIALS = /[!"#$%&'()*+,\-./:;<=>?@^_`{|}~\[\]]/g;
    const PASS_LENGTH = /^.{10,100}$/;

    return {
        username : username,
        min_username_length : MIN_USERNAME_LENGTH,
        max_username_length : MAX_USERNAME_LENGTH,
        bsn : bsn,
        bsn_length : BSN_LENGTH,
        email : EMAIL,
        names : NAMES,
        pass_length : PASS_LENGTH,
        phoneNumber : PHONE_NUMBER,
        postalCode : POSTAL_CODE,
        lowerCase : LOWERCASE,
        upperCase : UPPERCASE,
        numbers : NUMBERS,
        specials : SPECIALS,
    }
})();