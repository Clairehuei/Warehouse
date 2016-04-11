package com.fabric.warehouse.Api;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * An <code>enum</code> of the code of API return response.
 *
 * @author Allen
 */
public enum ApiResponseCode {
    Success("0000"),
    Transaction_Failure("0001"),
    Required_Field_Null("0011"),
    Digital_Patterns_Illegal("0012"),
    Date_Patterns_Illegal("0013"),
    Option_Illegal("0014"),
    Parameter_Code_Error("0015"),
    Record_Not_Found("0016"),
    Account_or_Password_Error("1011"),
    Token_Expired("1012"),
    Validation_Error("1099"),
    Total_Amount_And_Bill_Amount_not_Matched("1211"),
    Already_Collect_Chosen_Item("1311"),
    Certificate_validation_failures("1901"),
    Certificate_validation_failures_User_Error("1902");

    ApiResponseCode(String code) {
        this.code = code;
    }

    private String code;
    private static Map<String, ApiResponseCode> map = new HashMap<>();

    static {
        for (ApiResponseCode c : EnumSet.allOf(ApiResponseCode.class)) {
            map.put(c.code, c);
        }
    }

    /**
     * Get the response code of the <code>enum</code>.
     * @return code of the enumeration. e.g: {@value 0015}
     */
    public String getCode() {
        return code;
    }

    /**
     * Get {@link ApiResponseCode} from the given code.
     * @param id the response code
     * @return an {@link ApiResponseCode} <code>enum</code> object.
     */
    public static ApiResponseCode getState(String id) {
        return map.get(id);
    }

}
