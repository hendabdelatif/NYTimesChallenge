package com.hend.nytimeschallenge.utils

import com.hend.nytimeschallenge.persistance.entities.Article
import com.hend.nytimeschallenge.persistance.entities.Media
import com.hend.nytimeschallenge.persistance.entities.MediaMetaData
import com.hend.nytimeschallenge.persistance.entities.ResultResponse

object MockUtil {

    fun mockResultResponse() = ResultResponse(
        resultId = 0,
        status = "OK",
        copyright = "Copyright (c) 2020 The New York Times Company. All Rights Reserved.",
        num_results = 20,
        results = mockArticleList())

    // Mock Article
    fun mockArticle() = Article(
        articleId = 100000007497420,
        uri = "",
        url = "",
        source = "New York Times",
        published_date = "2020-12-13",
        section = "U.S.",
        subsection = "Politics",
        byline = "By Nicholas Fandos and Michael S. Schmidt",
        title = "Trump Allies Eye Long-Shot Election Reversal in Congress, Testing Pence",
        description = "Some House Republicans plan to try to use Congress’s tallying of electoral results on Jan. 6 to tip the election to President Trump. The attempt will put Republicans in a pinch.",
        media = mockMediaList()
    )

    fun mockArticleList() = listOf(mockArticle())

    // Mock Media
    fun mockMedia() = Media(
        mediaId = 0,
        caption = "The ensuing fight promises to shape how President Trump’s base views the election for years to come.",
        metadata = mockMediaMetaDataList()
    )

    fun mockMediaList() = listOf(mockMedia())


    fun mockMediaMetaData() = MediaMetaData(
        metaDataId = 0, url = "", format = IMAGE_FORMAT_THUMB
    )

    fun mockMediaMetaDataList() = listOf(mockMediaMetaData())
}
