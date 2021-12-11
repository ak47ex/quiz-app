package ru.pyatkinmv.controllers;

import org.springframework.lang.NonNull;

import java.util.Locale;

public interface Authorization {

    static Authorization parse(String authorization) {
        if (authorization != null) {
            if (Bearer.isMatch(authorization)) {
                return new Bearer(authorization);
            }
        }
        return null;
    }

    class Bearer implements Authorization {
        public final @NonNull String token;
        private static final String BEARER = "bearer";

        static boolean isMatch(String authorization) {
            return authorization.toLowerCase(Locale.ROOT).startsWith(BEARER);
        }

        private Bearer(@NonNull String token) {
            StringBuilder sb = new StringBuilder();
            boolean tokenBegan = false;
            for (int i = BEARER.length(); i < token.length(); ++i) {
                char c = token.charAt(i);
                if (tokenBegan && Character.isWhitespace(c)) {
                    break;
                }
                if (!tokenBegan && Character.isWhitespace(c)) {
                    continue;
                }
                tokenBegan = true;
                sb.append(c);
            }

            this.token = sb.toString();
        }
    }
}
