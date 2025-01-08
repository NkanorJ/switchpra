package com.user.user_service.commons.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.user.user_service.commons.exception.PhoneNumberException;

import java.util.Optional;

public class PhoneNumberValidator {

    public static String convertToInternationalFormat(String mobile) {

        try {

            var util = PhoneNumberUtil.getInstance();

            var number = util.parse(mobile, "NG");

            if (!util.isValidNumber(number))
                throw new PhoneNumberException(String.format("The phone number [%s] is not a valid nigerian number", mobile), false);

            return util.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);

        } catch (NumberParseException e) {
            throw new PhoneNumberException(String.format("Invalid phone number format [%s]", mobile), false);
        }
    }

    public static boolean isValidPhoneNumber(String reference) {

        var util = PhoneNumberUtil.getInstance();

        try {

            var number = util.parse(reference, "NG");

            return util.isValidNumber(number);

        } catch (NumberParseException e) {

            return false;
        }
    }

    public static Optional<String> convertFromInternationalFormat(String mobile) {
        try {

            var util = PhoneNumberUtil.getInstance();

            var number = util.parse(mobile, "NG");

            util.isValidNumber(number);

            String numberInNationalFormat = util.format(number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL).replace(" ", "");

            return Optional.of(numberInNationalFormat.replace("-", ""));

        } catch (NumberParseException e) {
            return Optional.empty();
        }
    }
}