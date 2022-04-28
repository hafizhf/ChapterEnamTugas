package andlima.hafizhfy.chapterenamtugas.pertemuan3.network

import andlima.hafizhfy.chapterenamtugas.pertemuan3.model.GetAllFilmResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("film")
    fun getAllFilm() : Call<List<GetAllFilmResponseItem>>
}