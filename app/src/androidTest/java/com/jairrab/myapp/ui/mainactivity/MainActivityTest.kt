package com.jairrab.myapp.ui.mainactivity

import androidx.test.rule.ActivityTestRule
import com.jairrab.myapp.Rndom
import com.jairrab.myapp.TestApp
import com.jairrab.domain.entities.Data
import com.jairrab.myapp.view.main.MainActivity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Rule
import org.junit.Test
import java.util.*


class MainActivityTest {

    private val domainRepository get() = TestApp.appComponent().domainRepository()

    @Rule @JvmField
    val activity = ActivityTestRule<MainActivity>(
        MainActivity::class.java,
        false, false
    )

    @Test
    fun assertsTableItemsCorrect() {
        val pause = 2000L

        val data = makeData()

        whenever(domainRepository.getData(any()))
            .thenReturn(Observable.just(data))

        //check activity launches successfully
        activity.launchActivity(null)

    }

    private fun makeData(): Data {
        return Data(
            stringA = Rndom.string(),
            mapA = makeQuotes(),
            longA = Rndom.long()
        )
    }

    private fun getRateString(rate: Double): String {
        return when {
            rate == 0.0    -> "0.00"
            rate < 0.00001 -> String.format("%.8f", rate)
            rate < 0.0001  -> String.format("%.7f", rate)
            rate < 0.001   -> String.format("%.6f", rate)
            rate < 0.01    -> String.format("%.5f", rate)
            rate < 0.1     -> String.format("%.4f", rate)
            rate < 1       -> String.format("%.3f", rate)
            rate < 1000    -> String.format("%.2f", rate)
            rate < 10000   -> String.format("%.1f", rate)
            else           -> String.format("%.0f", rate)
        }
    }

    private fun makeQuotes(): Map<String, Double> {

        val a = TreeMap<String, Double>()
        a["AAA"] = 1.0
        a["BBB"] = 2.0
        a["CCC"] = 3.0
        a["DDD"] = 4.0
        a["EEE"] = 5.0
        a["FFF"] = 6.0
        a["GGG"] = 7.0
        a["HHH"] = 8.0
        a["III"] = 9.0
        a["JJJ"] = 10.0
        a["KKK"] = 11.0
        a["LLL"] = 12.0
        a["MMM"] = 13.0
        a["NNN"] = 14.0
        a["OOO"] = 15.0
        a["PPP"] = 16.0
        a["QQQ"] = 17.0
        a["RRR"] = 18.0
        a["SSS"] = 19.0
        a["TTT"] = 20.0
        a["UUU"] = 21.0
        a["VVV"] = 22.0
        a["WWW"] = 23.0
        a["XXX"] = 24.0
        a["YYY"] = 25.0
        a["ZZZ"] = 26.0

        return a
    }
}