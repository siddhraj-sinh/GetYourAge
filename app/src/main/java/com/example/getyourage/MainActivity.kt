package com.example.getyourage

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.time.Year
import java.util.*

class MainActivity : AppCompatActivity() {
    private var resultDate: Int = 0
    private var resultMonth: Int = 0
    private var resultYear: Int = 0
   private var finalYear: Int = 0
    private var finalMonth: Int = 0
    private var finalDate: Int = 0
    private lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonDatePicker: Button = findViewById(R.id.btn_find)
        buttonDatePicker.setOnClickListener {view ->
            clickDatePicker(view)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun clickDatePicker(view: View?) {
         val myCalender = Calendar.getInstance()
        val year = myCalender.get(Calendar.YEAR)
        val month = myCalender.get(Calendar.MONTH)
        val day = myCalender.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, selectedYear, selectedmonth, selecteddayOfMonth ->

            // storing user selected data
            val birthDate = selecteddayOfMonth
            val birthMonth = selectedmonth + 1
            val birthYear = selectedYear
            val selectedDate = "$selecteddayOfMonth/${selectedmonth + 1}/$selectedYear"
            // simple data format
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val theDate = sdf.parse(selectedDate)
            val currentYear  = Calendar.getInstance().get(Calendar.YEAR)
            val currentMonth  = Calendar.getInstance().get(Calendar.MONTH) + 1
            val currentDate  = Calendar.getInstance().get(Calendar.DATE)

            calculateAge(birthDate, birthMonth, birthYear, currentDate, currentMonth, currentYear)
            finalDate = resultDate
            finalMonth = resultMonth
            finalYear = resultYear
            openDialog(finalYear,finalMonth,finalDate)

        }
            ,year
            ,month
            ,day).show()
    }

    private fun openDialog(y: Int, m: Int, d: Int) {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.age_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val closeImageView : ImageView = dialog.findViewById(R.id.btn_close)
        val yearTextView : TextView = dialog.findViewById(R.id.tv_year)
        val monthDayTextView : TextView = dialog.findViewById(R.id.tv_month_day)
        yearTextView.text ="$y\n" + "Years"
        monthDayTextView.text = "$m "+"Months "+"$d "+"Days"
        dialog.show()
        closeImageView.setOnClickListener{
            dialog.dismiss()

        }
    }

    private fun calculateAge(birthDate: Int, birthMonth: Int, birthYear: Int, currentDate: Int, currentMonth: Int, currentYear: Int) {
          calculateYear(currentYear,birthYear)
         calculateMonth(currentMonth,birthMonth)
        calculateDay(currentDate,birthDate)


    }

    private fun calculateDay(currentDate: Int, birthDate: Int) {
        if(currentDate>=birthDate)
        {
            resultDate= currentDate-birthDate
        }
        else
        {
            resultDate=currentDate-birthDate
            resultDate=30+resultDate
            if(resultMonth==0)
            {
                resultMonth=11
                resultYear--
            }
            else
            {
                resultMonth--
            }

        }
    }

    private fun calculateMonth(currentMonth: Int, birthMonth: Int) {
        if(currentMonth>=birthMonth)
        {
            resultMonth= currentMonth-birthMonth
        }
        else
        {
            resultMonth=currentMonth-birthMonth
            resultMonth += 12
            resultYear--
        }
    }

    private fun calculateYear(currentYear: Int, birthYear: Int) {
        resultYear = currentYear - birthYear
    }
}