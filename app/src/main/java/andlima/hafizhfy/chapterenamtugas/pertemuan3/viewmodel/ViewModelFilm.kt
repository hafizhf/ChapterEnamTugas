package andlima.hafizhfy.chapterenamtugas.pertemuan3.viewmodel

import andlima.hafizhfy.chapterenamtugas.func.alertDialog
import andlima.hafizhfy.chapterenamtugas.pertemuan3.model.GetAllFilmResponseItem
import andlima.hafizhfy.chapterenamtugas.pertemuan3.network.ApiClient
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

class ViewModelFilm : ViewModel() {
    lateinit var liveDataFilm : MutableLiveData<List<GetAllFilmResponseItem>>

    init {
        liveDataFilm = MutableLiveData()
    }

    fun getLiveFilmObserver() : MutableLiveData<List<GetAllFilmResponseItem>> {
        return liveDataFilm
    }

    fun makeApiFilm(context: Context) {
        ApiClient.instance.getAllFilm()
            .enqueue(object : retrofit2.Callback<List<GetAllFilmResponseItem>>{
                override fun onResponse(
                    call: Call<List<GetAllFilmResponseItem>>,
                    response: Response<List<GetAllFilmResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        liveDataFilm.postValue(response.body())
                    } else {
                        alertDialog(context, "Request Failed", response.message()) {}
                    }
                }

                override fun onFailure(call: Call<List<GetAllFilmResponseItem>>, t: Throwable) {
                    alertDialog(context, "Error", t.message!!) {}
                }

            })
    }
}