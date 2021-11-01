package com.example.mygithub.ui.favourite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.data.local.FavouriteUser
import com.example.mygithub.data.model.User
import com.example.mygithub.databinding.ActivityFavouriteBinding
import com.example.mygithub.ui.detail.DetailActivity
import com.example.mygithub.ui.main.UserAdapter

class FavouriteActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var binding: ActivityFavouriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavouriteViewModel::class.java)


        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavouriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_HTML, data.html_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavouriteActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if ( it != null ) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })

    }

    private fun mapList(users: List<FavouriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()

        for ( user in users) {
            val userMapped = User (
                user.login,
                user.id,
                user.avatar_url,
                user.html_url
            )
            listUsers.add(userMapped)
        }
        return listUsers
    }
}