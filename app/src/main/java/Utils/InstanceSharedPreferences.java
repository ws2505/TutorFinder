package Utils;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * Created by wenbo on 2017/7/2.
 */

public class InstanceSharedPreferences implements SharedPreferences {
    @Override
    public boolean getBoolean(String s, boolean b) {
        return false;
    }

    @Override
    public long getLong(String s, long l) {
        return 0;
    }

    @Override
    public int getInt(String s, int i) {
        return 0;
    }

    @Override
    public Editor edit() {
        return null;
    }

    @Override
    public boolean contains(String s) {
        return false;
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String s, @Nullable Set<String> set) {
        return null;
    }

    @Override
    public float getFloat(String s, float v) {
        return 0;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }

    @Override
    public Map<String, ?> getAll() {
        return null;
    }

    @Nullable
    @Override
    public String getString(String s, @Nullable String s1) {
        return null;
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {

    }
}
