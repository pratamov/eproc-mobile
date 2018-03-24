package id.bismillah.e_procumahku.core.network

import id.bismillah.e_procumahku.model.NetworkModel
import id.bismillah.e_procumahku.model.Rekening
import id.bismillah.e_procumahku.model.User
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Created by Subkhan Sarif on 22/02/2018.
 */

interface Server {
    companion object {
        const val PATH_LOGIN = "/api/owner/v2/login"
        const val PATH_SIGNUP = "/api/owner/v2/signup"
        const val PATH_CREATE_REKENING = "/api/owner/v2/users/{user_id}/accounts"
    }

    @POST(PATH_LOGIN)
    fun login(@Query("email") email: String, @Query("password") password: String): Observable<User>

    @POST(PATH_SIGNUP)
    fun signup(@Body signupModel: NetworkModel.SignupModel): Observable<User>

    @POST(PATH_CREATE_REKENING)
    fun createNewRekening(@Path("user_id") userId: String, @Body createAccountModel: NetworkModel.CreateAccountModel): Observable<Rekening>
}

class Connection {
    private val BASE_URL = "http://13.229.128.229/"

    private fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    private fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(provideInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build()
    }

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(provideClient())
                .build()
    }

    fun init(): Server {
        return provideRetrofit().create(Server::class.java)
    }
}

sealed class Response<T> {
    var error: String? = null
    var code: Int = 0
    var data: T? = null
    fun isSuccess(): Boolean {
        return code in 200..299
    }

    class Default : Response<String>()
    class UserResponse : Response<User>()
}