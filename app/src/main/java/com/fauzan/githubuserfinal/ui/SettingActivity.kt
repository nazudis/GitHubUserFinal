package com.fauzan.githubuserfinal.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.fauzan.githubuserfinal.R
import com.fauzan.githubuserfinal.receiver.AlarmReceiver
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var mSharedPrefereces: SharedPreferences

    companion object {
        const val PREF_NAME = "SettingPreference"
        private const val DAILY_REMINDER = "daily_remainder"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //Set Up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar_setting)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            title = getString(R.string.setting)
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { finish() }
        }

        alarmReceiver = AlarmReceiver()
        mSharedPrefereces = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        setSwitch()

        switch_reminder.setOnCheckedChangeListener { _, isCheck ->
            if (isCheck){
                savedSetting(true)
                alarmReceiver.setDailyAlarm(this, AlarmReceiver.NOTIF_TYPE, "09:00")
            } else {
                savedSetting(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun setSwitch(){
        switch_reminder.isChecked = mSharedPrefereces.getBoolean(DAILY_REMINDER, false)
    }

    private fun savedSetting(value: Boolean) {
        val editor = mSharedPrefereces.edit()
        editor.putBoolean(DAILY_REMINDER, value)
        editor.apply()
    }
}