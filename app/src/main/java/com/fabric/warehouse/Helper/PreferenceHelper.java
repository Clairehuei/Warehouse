package com.fabric.warehouse.Helper;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.fabric.warehouse.Helper.PreferenceHelper.PreferenceKey.AccountAndPassword;
import static com.fabric.warehouse.Helper.PreferenceHelper.PreferenceKey.Cates;
import static com.fabric.warehouse.Helper.PreferenceHelper.PreferenceKey.StoppedTimestamp;
import static com.fabric.warehouse.Helper.PreferenceHelper.PreferenceKey.TokenId;
import static com.fabric.warehouse.Helper.PreferenceHelper.PreferenceKey.UserInfo;

/**
 * A helper class for dealing with the related operations from {@link SharedPreferences}.
 *
 * @author Allen
 */
@Singleton
public class PreferenceHelper {

    private static final int DEF_STOPPED_TIMESTAMP = 0;

    private final SharedPreferences preferences;

    @Inject
    public PreferenceHelper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

//    /**
//     * Store the information of the logged-in user in {@link SharedPreferences} with the form of
//     * flat JSON text.
//     *
//     * @param info an {@link UserInfo} object to store.
//     */
//    public void setUserInfo(UserInfo info) {
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(UserInfo.name(), new Gson().toJson(info));
//        editor.apply();
//    }

//    /**
//     * Read the flatten JSON text from {@link SharedPreferences} and convert to an {@link UserInfo} object.
//     *
//     * @return an {@link UserInfo} object.
//     */
//    public UserInfo getUserInfo() {
//        return new Gson().fromJson(preferences.getString(UserInfo.name(), ""), UserInfo.class);
//    }

    /**
     * Store the token id of the logged in user into {@link SharedPreferences}.
     *
     * @param id the token id.
     */
    public void setTokenId(String id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(TokenId.name(), id);
        editor.apply();
    }

    /**
     * Read the token id of the logged in user from {@link SharedPreferences}.
     *
     * @return <code>String</code> of the token id, empty <code>String</code> if <code>null</code>.
     */
    public String getTokenId() {
        return preferences.getString(TokenId.name(), "");
    }

    /**
     * Save the concatenated account and password into {@link SharedPreferences}.
     *
     * @param accountAndPassword <code>String</code> of concatenated account and password
     */
    public void setAccountAndPassword(String accountAndPassword) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(AccountAndPassword.name(), accountAndPassword);
        editor.apply();
    }

    /**
     * Read the concatenated account and password from {@link SharedPreferences}.
     *
     * @return <code>String</code> of the concatenated account and password
     */
    public String getAccountAndPassword() {
        return preferences.getString(AccountAndPassword.name(), "");
    }

    public void setStoppedTimestamp() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(StoppedTimestamp.name(), System.currentTimeMillis());
        editor.apply();
    }

    public long getStoppedTimestamp() {
        return preferences.getLong(StoppedTimestamp.name(), DEF_STOPPED_TIMESTAMP);
    }

    public boolean hasStoppedTimestamp() {
        return getStoppedTimestamp() > DEF_STOPPED_TIMESTAMP;
    }

    public void clearStoppedTimestamp() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(StoppedTimestamp.name());
        editor.apply();
    }

    public String getCates() {
        return preferences.getString(Cates.name(), "");
    }

    public void setCates(String cates) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Cates.name(), cates);
        editor.apply();
    }

    /**
     * The <code>enum</code> as the key of the temporary preference.
     */
    enum PreferenceKey {
        UserInfo,
        TokenId,
        AccountAndPassword,
        StoppedTimestamp,
        Cates
    }
}
