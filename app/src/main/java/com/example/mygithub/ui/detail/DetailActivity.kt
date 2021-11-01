package com.example.mygithub.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithub.R
import com.example.mygithub.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_HTML = "extra_html"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val username: String = intent.getStringExtra(EXTRA_USERNAME).toString()
        val id: Int = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl: String = intent.getStringExtra(EXTRA_AVATAR).toString()
        val htmlUrl: String = intent.getStringExtra(EXTRA_HTML).toString()


        if (username != null) {
            viewModel.setUserDetail(username)
        }

        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvLocation.text = it.location
                    tvCompany.text = it.company
                    tvFollower.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    tvRepository.text = it.public_repos.toString()
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transform(CircleCrop())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(imgUser)
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if ( count != null ) {
                    if ( count > 0) {
                        binding.toggleFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.toggleFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addFavorite(username, id, avatarUrl, htmlUrl)
            } else {
                viewModel.removeFromFavorite(id)
            }
            binding.toggleFav.isChecked = _isChecked
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailActivity, username)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }
}