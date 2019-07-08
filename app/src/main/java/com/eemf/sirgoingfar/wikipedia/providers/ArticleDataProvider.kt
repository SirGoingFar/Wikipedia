package com.eemf.sirgoingfar.wikipedia.providers

import android.accounts.NetworkErrorException
import com.eemf.sirgoingfar.wikipedia.BuildConfig
import com.eemf.sirgoingfar.wikipedia.models.WikiResult
import com.eemf.sirgoingfar.wikipedia.utils.Urls
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.gson.GsonDeserializer
import com.github.kittinunf.fuel.httpGet
import java.io.Reader

class ArticleDataProvider {

    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to BuildConfig.APPLICATION_ID)
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {

        Urls.getSearchUrl(term, skip, take).httpGet().responseObject(WikiDeserializer()) { _, response, result ->

            if (response.httpStatusCode != 200)
                throw NetworkErrorException("Unable to fetch data")

            val (data, _) = result

            responseHandler.invoke(data as @ParameterName(name = "result") WikiResult)
        }

    }

    fun random(take: Int, responseHandler: (result: WikiResult) -> Unit?) {

        Urls.getRandomUrl(take).httpGet().responseObject(WikiDeserializer()) { _, response, result ->

            if (response.httpStatusCode != 200)
                throw NetworkErrorException("Unable to fetch data")

            val (data, _) = result

            responseHandler.invoke(data as @ParameterName(name = "result") WikiResult)
        }

    }

    class WikiDeserializer : ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader): WikiResult? = GsonDeserializer<WikiResult>().deserialize(reader)
    }
}