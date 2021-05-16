package com.example.applikasigithubuser2.preferencefragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.example.applikasigithubuser2.AlarmReceiver
import com.example.applikasigithubuser2.R

class PreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var Alarm: String
    private lateinit var AlarmSwitch: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver


    override fun onCreatePreferences(bundle: Bundle?, s: String?) {
        addPreferencesFromResource(R.xml.preferences)
        init()
        settingAlarm()
        alarmReceiver = AlarmReceiver()

    }

    private fun init() {
        Alarm = resources.getString(R.string.set)
        AlarmSwitch = findPreference<SwitchPreference>(Alarm) as SwitchPreference
    }

    private fun settingAlarm() {
        val sh = preferenceManager.sharedPreferences
        AlarmSwitch.isChecked = sh.getBoolean(Alarm, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val setAlarmStatus = AlarmSwitch.isChecked
        if (key == Alarm) {
            if (sharedPreferences != null) {
                AlarmSwitch.isChecked = sharedPreferences.getBoolean(Alarm, false)
                if (setAlarmStatus) {
                    alarmReceiver.setRepeatingAlarm(activity)
                } else alarmReceiver.cancelAlarm(activity)
            }
        }
    }
}
