package com.jlack.middleware;

public class Signing {

    public static boolean isSigned(String signature) {
        // TODO verify signature
        // https://api.slack.com/authentication/verifying-requests-from-slack
        return signature.equals("SIGNING SECRET HERE");
    }

    public static String getResponse() {
        return "bad signature";
    }
}
