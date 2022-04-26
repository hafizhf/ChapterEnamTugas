package andlima.hafizhfy.chapterenamtugas.pertemuan2

import andlima.hafizhfy.chapterenamtugas.R
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        // Check if form has been filled by user. If not, make calculate button un-clickable
        isFormFilled()

        // Check BMI results based on values from the field. This only run when isFormFilled() true
        checkBMI()
    }

    // Function to get BMI results based on values from the field ----------------------------------
    @SuppressLint("SetTextI18n")
    private fun checkBMI() {
        // Handler used to show BMI category based on the results
        val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                // The message was obtained from the Thread below
                val message = msg.obj as String
                tv_category_result.text = ": $message"
            }
        }

        // Thread used to get and show results to the view
        Thread(Runnable {
            // Calculate button listener
            btn_calculate_bmi.post(Runnable {
                btn_calculate_bmi.setOnClickListener {
                    // Hide virtual keyboard after user done providing input
                    et_weight.post(Runnable {
                        et_weight.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    })

                    // Hide virtual keyboard after user done providing input
                    et_height.post(Runnable {
                        et_height.onEditorAction(EditorInfo.IME_ACTION_DONE)
                    })

                    // Get weight and height values from EditText
                    val weight = et_weight.text.toString().toInt()
                    val height = et_height.text.toString().toInt()

                    // Calculate the result with calculateBMI() function
                    val result = calculateBMI(weight, height)

                    // Show the result container below button calculate
                    container_bmi_result.post(Runnable {
                        container_bmi_result.visibility = View.VISIBLE
                    })

                    // Show user weight to result TextView
                    tv_weight_result.post {
                        tv_weight_result.text = ": $weight kg"
                    }

                    // Show user height to result TextView
                    tv_height_result.post {
                        tv_height_result.text = ": $height cm"
                    }

                    // Show BMI result to result TextView
                    tv_bmi_result.post {
                        tv_bmi_result.text = ": $result kg/m2"
                    }

                    // Get BMI category with categoryBMI() function and send to handler above
                    val msg = Message.obtain()
                    msg.obj = categoryBMI(result)
                    msg.target = handler
                    msg.sendToTarget()
                }
            })
        }).start()
    }

    // Function to calculate BMI based on user weight and height input -----------------------------
    private fun calculateBMI(weight: Int, height: Int) : Float {
        // The result is obtained from BMI formula <weight(cm)/height^2(m)>
        val result : Float =
            weight.toFloat().div((height.toFloat() * height.toFloat())/10000)

        // Round the result in half up as the return
        return result.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toFloat()
    }

    // Function to check user category. Categories obtained from BMI categorization on the mean age
    private fun categoryBMI(bmiResult: Float) : String {
        return if (bmiResult < 18.5) {
            "Underweight (kurus)"
        } else if (bmiResult >= 18.5 && bmiResult < 25.0) {
            "Healthy (normal)"
        } else if (bmiResult >= 25.0 && bmiResult < 30.0) {
            "Overweight"
        } else {
            "Obese (obesitas)"
        }
    }

    // Function to check if form has been filled by user. If not, make calculate button un-clickable
    @SuppressLint("ResourceAsColor", "UseCompatLoadingForColorStateLists")
    private fun isFormFilled() : Boolean {
        if (et_height.text.toString() != "" && et_weight.text.toString() != "") {
            btn_calculate_bmi.backgroundTintList = applicationContext
                .resources.getColorStateList(R.color.bmi_1)
            btn_calculate_bmi.isClickable = true
        } else {
            btn_calculate_bmi.backgroundTintList = applicationContext
                .resources.getColorStateList(R.color.bmi_grey)
            btn_calculate_bmi.isClickable = false
        }

        Handler(Looper.getMainLooper()).postDelayed( {
            kotlin.run {
                isFormFilled()
            }
        }, 40)

        return btn_calculate_bmi.isCheckable
    }
}