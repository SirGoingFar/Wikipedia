package com.eemf.sirgoingfar.wikipedia.models

class WikiResult {
    val query: WikiQueryData? = null

    class WikiQueryData {
        val pages: List<WikiPage> = ArrayList()
    }

    class WikiPage {
        var pageid: Int? = null
        var title: String? = null
        var fullurl: String? = null
        var thumbnail: WikiThumbnail? = null
    }

    class WikiThumbnail {
        val source: String? = null
        val width: Int? = 0
        val height: Int? = 0
    }

}