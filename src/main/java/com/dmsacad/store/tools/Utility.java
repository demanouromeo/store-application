package com.dmsacad.store.tools;

import java.util.UUID;

public class Utility {
    public static UUID hexStringToUUID(String hexString){
        try{
            //String hexString = "0x017e96988e6c4fa0810267b644e4b78d";

            // 1. Remove the "0x" prefix if it exists
            String cleanHex = hexString.startsWith("0x") ? hexString.substring(2) : hexString;

            // 2. Insert hyphens to match the standard UUID pattern: 8-4-4-4-12
            String formattedUuid = cleanHex.replaceFirst(
                    "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{12})",
                    "$1-$2-$3-$4-$5"
            );

            // 3. Convert to UUID object
            UUID uuid = UUID.fromString(formattedUuid);
            return uuid;
        } catch (Exception e) {
            System.err.println("Utility.hexStringToUUID(): Failed to convert ["+hexString+"] to UUID+\n"+e);
            //throw new IllegalArgumentException(e);
            return null;
        }
    }
}
