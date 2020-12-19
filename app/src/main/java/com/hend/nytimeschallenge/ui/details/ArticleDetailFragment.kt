package com.hend.nytimeschallenge.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.hend.nytimeschallenge.R
import com.hend.nytimeschallenge.databinding.FragmentArticleDetailsBinding
import com.hend.nytimeschallenge.utils.IMAGE_FORMAT_POSTER
import com.hend.nytimeschallenge.utils.loadImageUrl
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

/**
 * A fragment representing a single Article detail screen.
 * This fragment is either contained in a [ArticleListActivity]
 * in two-pane mode (on tablets) or a [ArticleDetailActivity]
 * on handsets.
 */
@AndroidEntryPoint
class ArticleDetailFragment : Fragment() {

    private lateinit var detailsViewModel: ArticleDetailsViewModel
    private lateinit var binding: FragmentArticleDetailsBinding

    private var articleId by Delegates.notNull<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailsViewModel = ViewModelProvider(this).get(ArticleDetailsViewModel ::class.java)

        arguments?.let {
            if (it.containsKey(ARG_ARTICLE_ID)) {
                articleId = it.getLong(ARG_ARTICLE_ID)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentArticleDetailsBinding.inflate(layoutInflater)

        detailsViewModel.getArticle().observe(viewLifecycleOwner,  {
            activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title = it.title
            binding.txtTitle.text = it.title
            binding.txtPublishedDate.text = binding.txtPublishedDate.text.toString().plus(" ").plus(it.published_date)
            binding.txtByline.text = it.byline
            binding.txtSection.text = it.section
            binding.txtDescription.text = it.description

            if(it.subsection.isNotEmpty()){
                binding.txtSubsection.text = "/".plus(it.subsection)
            }

            it.media.forEach { media ->
                media.metadata.forEach { mediaMetaData ->
                    if (mediaMetaData.format == IMAGE_FORMAT_POSTER){
                        binding.imgPoster.loadImageUrl(mediaMetaData.url)
                        binding.txtCaption.text= media.caption
                    }
                }
            }
        })
        return binding.root
    }

    companion object {
        const val ARG_ARTICLE_ID = "article_id"
    }
}