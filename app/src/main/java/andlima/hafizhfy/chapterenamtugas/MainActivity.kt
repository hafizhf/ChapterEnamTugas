package andlima.hafizhfy.chapterenamtugas

import andlima.hafizhfy.chapterenamtugas.pertemuan2.BMIActivity
import andlima.hafizhfy.chapterenamtugas.pertemuan3.AsyncTaskFilmActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        splash(BMIActivity::class.java)

        Handler(Looper.getMainLooper()).postDelayed({
            splash_container.visibility = View.GONE
        }, 3000)
        goToChosenApp()
    }

    private fun splash(cls: Class<*>) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, cls))
            finish()
        }, 3000)
    }

    private fun goToChosenApp() {
        btn_goto_bmi_calculator.setOnClickListener {
            startActivity(Intent(this, BMIActivity::class.java))
            finish()
        }

        btn_goto_asynctask_film.setOnClickListener {
            startActivity(Intent(this, AsyncTaskFilmActivity::class.java))
            finish()
        }
    }

}