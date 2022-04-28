package andlima.hafizhfy.chapterenamtugas.pertemuan3

import andlima.hafizhfy.chapterenamtugas.R
import andlima.hafizhfy.chapterenamtugas.pertemuan3.adapter.AdapterFilm
import andlima.hafizhfy.chapterenamtugas.pertemuan3.model.GetAllFilmResponseItem
import andlima.hafizhfy.chapterenamtugas.pertemuan3.viewmodel.ViewModelFilm
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_async_task_film.*

class AsyncTaskFilmActivity : AppCompatActivity() {

    lateinit var adapterFilm: AdapterFilm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_async_task_film)

        asyncTaskFilm().execute()
    }

    @Suppress("DEPRECATION")
    inner class asyncTaskFilm() : AsyncTask<Int, Void, List<GetAllFilmResponseItem>>() {

        lateinit var progress : ProgressDialog
        lateinit var dataFilm : List<GetAllFilmResponseItem>
        val viewModel = ViewModelProvider(this@AsyncTaskFilmActivity)[ViewModelFilm::class.java]

        override fun onPreExecute() {
            super.onPreExecute()

            progress = ProgressDialog(this@AsyncTaskFilmActivity)
            progress.show()

        }

        override fun doInBackground(vararg p0: Int?): List<GetAllFilmResponseItem> {

            viewModel.makeApiFilm(this@AsyncTaskFilmActivity)

            /*
            * Below code cannot run because of wrong thread
            */

//            viewModel.liveDataFilm.observe(this@AsyncTaskFilmActivity, Observer {
//                if (it != null) {
//                    dataFilm = it
//                }
//                else {
//                }
//            })

            // Give init value since the first idea was to make dataFilm have value from viewModel
            dataFilm = arrayListOf(GetAllFilmResponseItem("", "","","","","",""))
            return dataFilm
        }

        override fun onProgressUpdate(vararg values: Void?) {
            super.onProgressUpdate(*values)
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun onPostExecute(result: List<GetAllFilmResponseItem>?) {
            super.onPostExecute(result)
            viewModel.liveDataFilm.observe(this@AsyncTaskFilmActivity, Observer {
                adapterFilm.setDataFilm(it)
                adapterFilm.notifyDataSetChanged()
            })
            initRecycler()
            progress.dismiss()

        }
    }

    fun initRecycler() {
        rv_film.layoutManager = LinearLayoutManager(this)
        adapterFilm = AdapterFilm {
            // onClick item code
        }
        rv_film.adapter = adapterFilm
    }

}