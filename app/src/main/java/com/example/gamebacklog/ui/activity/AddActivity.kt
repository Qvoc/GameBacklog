package com.example.gamebacklog.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.gamebacklog.R
import com.example.gamebacklog.model.Game
import com.example.gamebacklog.ui.viewmodel.AddActivityViewModel

import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

class AddActivity : AppCompatActivity() {

    private lateinit var addViewModel: AddActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.title_activity_add)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        fab.setOnClickListener { onAddGame() }
    }

    private fun initViewModel() {
        addViewModel = ViewModelProviders.of(this).get(AddActivityViewModel::class.java)
    }

    private fun onAddGame() {
        val title = etTitle.text.toString()
        val platform = etPlatform.text.toString()
        val day = etDay.text.toString()
        val month = etMonth.text.toString()
        val year = etYear.text.toString()

        if (checkForInput(title, platform, day, month, year)) {
            val game =
                Game(
                    title,
                    platform,
                    day.toInt(),
                    month.toInt(),
                    year.toInt()
                )

            addViewModel.insertGame(game)
            startMainActivity()
        }
    }

    // Check for valid input
    private fun checkForInput(
        title: String,
        platform: String,
        day: String,
        month: String,
        year: String
    ): Boolean {
        val dayRange = 1..31
        val monthRange = 1..12
        val yearRange = 1958..2030

        when {
            title.isBlank() -> {
                inputInvalidMessage(getString(R.string.error_title))
                return false
            }
            platform.isBlank() -> {
                inputInvalidMessage(getString(R.string.error_platform))
                return false
            }
            day.isBlank() || month.isBlank() || year.isBlank() -> {
                inputInvalidMessage(getString(R.string.error_date))
                return false
            }
            !(day.toInt() in dayRange && month.toInt() in monthRange && year.toInt() in yearRange) -> {
                inputInvalidMessage(getString(R.string.error_date))
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun inputInvalidMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
