package com.application.icafapi.common.constant;

public class Email {

    private static String newLine = System.getProperty("line.separator");

    public static final String USER_REGISTRATION_SUBJECT = "Welcome to ICAF 2021";
    public static final String ATTENDEE_REGISTRATION_BODY = "Hello," + newLine + newLine + "Congratulations! Your registration is confirmed!" + newLine +
            "You've successfully completed registration of 'ICAF 2021 Attendee' application." + newLine + newLine + "If you have any questions or concerns, feel free to contact us via," + newLine +
            "icaf2021@gmail.com" + newLine + newLine + "Best Regards," + newLine + "Organizing Committee";

    public static final String RESEARCHER_REGISTRATION_BODY = "Hello," + newLine + newLine + "Thank you for Registering to ICAF 2021" + newLine +
            "You've successfully completed registration of 'ICAF 2021 Researcher' application." + newLine + "Your researcher application is being processed. " +
            "We will review your application and update you about it's status within 48 hours." + newLine + newLine + "If you have any questions or concerns, feel free to contact us via," + newLine +
            "icaf2021@gmail.com" + newLine + newLine + "Best Regards," + newLine + "Organizing Committee";

    public static final String WORKSHOP_REGISTRATION_BODY = "Hello," + newLine + newLine + "Thank you for Registering to ICAF 2021" + newLine +
            "You've successfully completed registration of 'ICAF 2021 Workshop Conductor' application." + newLine + "Your workshop proposal application is being processed. " +
            "We will review your application and update you about it's status within 48 hours." + newLine + newLine + "If you have any questions or concerns, feel free to contact us via," + newLine +
            "icaf2021@gmail.com" + newLine + newLine + "Best Regards," + newLine + "Organizing Committee";

    public static final String COMMITTEE_REGISTRATION_SUBJECT = "Welcome Onboard";

    public static final String REVIEWER_REGISTRATION_BODY = "Hello," + newLine + newLine + "Welcome to ICAF 2021 Organizing Committee" + newLine +
            "This is to inform you that you have assigned as a 'Reviewer' of ICAF 2021" + newLine + "Please login to your account using this email and the auto-generated password down below. " +
            "You can change the password once you login to the account." + newLine + "Generated password: " + newLine;
    public static final String EDITOR_REGISTRATION_BODY = "Hello," + newLine + newLine + "Welcome to ICAF 2021 Organizing Committee" + newLine +
            "This is to inform you that you have assigned as an 'Editor' of ICAF 2021" + newLine + "Please login to your account using this email and the auto-generated password down below. " +
            "You can change the password once you login to the account." + newLine + "Generated password: " + newLine;
    public static final String ADMIN_REGISTRATION_BODY = "Hello," + newLine + newLine + "Welcome to ICAF 2021 Organizing Committee" + newLine +
            "This is to inform you that you have assigned as an 'Admin' of ICAF 2021" + newLine + "Please login to your account using this email and the auto-generated password down below. " +
            "You can change the password once you login to the account." + newLine + "Generated password: " + newLine;

    public static final String COMMITTEE_REGISTRATION_END = newLine + newLine + "If you have any questions or concerns, feel free to contact us via," + newLine +
            "icaf2021@gmail.com" + newLine + newLine + "Best Regards," + newLine + "Organizing Committee";

    public static final String SUBMISSION_STATUS_SUBJECT = "About Your Submission Status";

    public static final String SUBMISSION_APPROVED_BODY = "Congratulations!" + newLine  + newLine +
            "We are excited to inform you that your submission was Approved." + newLine + "Please login to your account to complete the registration and  stay up to date." + newLine;

    public static final String SUBMISSION_REJECTED_BODY = "Hello," + newLine  + newLine +
            "We are really sorry to inform you that your submission was Rejected." + newLine + "Please login to your account to know more about this decision." + newLine;

    public static final String ACCOUNT_REMOVAL_SUBJECT = "Your Account has been Removed";

    public static final String ACCOUNT_REMOVAL_BODY = "Hello," + newLine  + newLine +
            "We are really sorry to inform you that your account has been removed from the ICAF-2021." + newLine + "You will no longer be able to login to your account." + newLine;

    private Email() {}

}
