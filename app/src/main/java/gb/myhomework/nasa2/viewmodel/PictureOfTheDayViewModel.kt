package gb.myhomework.nasa2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gb.myhomework.nasa2.BuildConfig
import gb.myhomework.nasa2.model.api.PODRetrofitImpl
import gb.myhomework.nasa2.model.entity.PODServerResponseData
import gb.myhomework.nasa2.model.repo.PictureOfTheDayData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {

    private var date: String = "2021-01-01"

    fun getData(delayDay : Int): LiveData<PictureOfTheDayData> {
        sendServerRequest(delayDay)
        return liveDataForViewToObserve
    }

    private fun sendServerRequest(delayDay : Int) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)
        val apiKey: String = BuildConfig.NASA_APOD_API_KEY
        if (apiKey.isBlank()) {
            PictureOfTheDayData.Error(Throwable("You need API key"))
        } else {
           // val delayDay = 1
            date = setData(delayDay)
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey, date).enqueue(object :
                Callback<PODServerResponseData> {
                override fun onResponse(
                    call: Call<PODServerResponseData>,
                    response: Response<PODServerResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value =
                            PictureOfTheDayData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                    liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                }
            })
        }
    }

    private fun setData(delayDay: Int) : String {
        val today = GregorianCalendar()
        val newDate = StringBuilder() // Месяц отсчитывается с 0, поэтому добавляем 1
            .append(today.get(GregorianCalendar.YEAR)).append("-")
            .append(today.get(GregorianCalendar.MONTH) + 1).append("-")
            .append(today.get(GregorianCalendar.DAY_OF_MONTH) - delayDay)
            .toString()
        return  newDate
    }

}
