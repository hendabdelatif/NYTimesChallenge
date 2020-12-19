package com.hend.nytimeschallenge.ui.articles

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hend.nytimeschallenge.databinding.ActivityArticleListBinding
import com.hend.nytimeschallenge.network.response.Resource
import com.hend.nytimeschallenge.ui.adapters.ArticlesAdapter
import com.hend.nytimeschallenge.ui.details.ArticleDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.article_list.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * This activity has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of articles, which when touched,
 * lead to a [ArticleDetailActivity] representing
 * item details. On tablets, the activity presents the list of articles and
 * article details side-by-side using two vertical panes.
 */
@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ArticleListActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleListViewModel
    private lateinit var binding: ActivityArticleListBinding
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
        observeViewModel()
        initViewBinding()
    }

     private fun init() {
        viewModel = ViewModelProvider(this).get(ArticleListViewModel::class.java)
    }

     private fun initViewBinding() {
        binding = ActivityArticleListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (binding.root.article_detail_container != null) {
            twoPane = true
        }
    }

     private fun observeViewModel() {
        viewModel.resultsResponse.observe(this, {
            when (it.status) {
                Resource.Status.LOADING -> {
                    showLoading()
                }
                Resource.Status.SUCCESS -> {
                    hideLoading()

                    with(binding.includeLayout.rvArticles) {
                        adapter = it.data?.results?.let { articlesList ->
                            ArticlesAdapter(this@ArticleListActivity, articlesList, twoPane)
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    hideLoading()
                    showError(it.message!!)
                }
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(msg: String) {
        binding.errorMessage.visibility = View.VISIBLE
        binding.errorMessage.text = msg
    }

}