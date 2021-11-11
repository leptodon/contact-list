package ru.cactus.contactlist.utils

import android.annotation.SuppressLint
import ru.cactus.contactlist.data.response_models.User
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val ENG_DATE_DASH_FORMAT = "yyyy-MM-dd"
    private const val RU_DATE_DASH_FORMAT = "dd MMMM yyyy"
    private const val RU_MONTH_DAY_FORMAT = "MMM d"


    private val engDateFormatDash: DateFormat = SimpleDateFormat(ENG_DATE_DASH_FORMAT, Locale("ru"))
    private val calendar = Calendar.getInstance()

    @SuppressLint("SimpleDateFormat")
    private val sdfEn = SimpleDateFormat(ENG_DATE_DASH_FORMAT)

    @SuppressLint("SimpleDateFormat")
    private val sdfRu = SimpleDateFormat(RU_DATE_DASH_FORMAT)

    @SuppressLint("SimpleDateFormat")
    private val sdfRuMonthDay = SimpleDateFormat(RU_MONTH_DAY_FORMAT)


    @Throws(ParseException::class)
    fun parse(dataStr: String?): Date? {
        if (dataStr == null) return null
        return engDateFormatDash.parse(dataStr)
    }

    fun toDateLong(dateString: String): Long {
        return parse(dateString)?.time ?: 0
    }

    fun toMonthAndDayFormat(dateString: String): String {
        return sdfRuMonthDay.format(sdfEn.parse(dateString)!!)
    }

    fun toRuFormat(dateString: String): String {
        return sdfRu.format(sdfEn.parse(dateString)!!)
    }

    fun getAge(birthDay: String): String {
        val bDay = Calendar.getInstance()
        bDay.time = sdfEn.parse(birthDay)!!

        return ageDescription(calendarYearsBetween(bDay, calendar))
    }

    fun calendarYearsBetween(startCal: Calendar, endCal: Calendar): Int {
        val start = Calendar.getInstance()
        start.timeZone = startCal.timeZone
        start.timeInMillis = startCal.timeInMillis

        val end = Calendar.getInstance()
        end.timeZone = endCal.timeZone
        end.timeInMillis = endCal.timeInMillis

        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)
        start.set(Calendar.MILLISECOND, 0)

        end.set(Calendar.HOUR_OF_DAY, 0)
        end.set(Calendar.MINUTE, 0)
        end.set(Calendar.SECOND, 0)
        end.set(Calendar.MILLISECOND, 0)

        return end.get(Calendar.YEAR) - start.get(Calendar.YEAR)
    }

    fun ageDescription(age: Int): String =
        when {
            age % 100 in 11..14 -> "$age лет"
            age % 10 == 1 -> "$age год"
            age % 10 in 2..4 -> "$age года"
            else -> "$age лет"
        }

    fun compareWithCurrentDay(bDay: String): Boolean {
        val changeYear = bDay.replace(bDay.subSequence(0, 4).toString(), "2021")
        val currentDay = Calendar.getInstance()
        currentDay.time = sdfEn.parse(changeYear)!!
        return when {
            currentDay.after(calendar) -> {
                true
            }
            currentDay.before(calendar) -> {
                false
            }
            else -> {
                false
            }
        }
    }
}