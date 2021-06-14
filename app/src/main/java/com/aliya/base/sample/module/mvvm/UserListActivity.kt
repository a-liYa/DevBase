package com.aliya.base.sample.module.mvvm

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aliya.base.sample.ktx.viewBindings
import com.aliya.base.sample.R
import com.aliya.base.sample.base.ActionBarActivity
import com.aliya.base.sample.databinding.ActivityUserListBinding
import com.aliya.base.sample.module.mvvm.model.LoadStatus
import com.aliya.base.sample.module.mvvm.model.User
import com.aliya.base.sample.module.mvvm.viewmodel.UserListViewModel

/**
 * User List
 *
 * @author a_liYa
 * @date 2021/6/12 11:57.
 */
class UserListActivity : ActionBarActivity() {
    private val viewModel by viewModels<UserListViewModel>() // 委托机制，此处需引入 activity-ktx
    private val viewBinding by viewBindings(ActivityUserListBinding::bind)
    private lateinit var userAdapter: UserAdapter
    private lateinit var loadPager: LoadPager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)
        initView()
        initViewModel()
        requestData()
    }

    private fun initView() {
        viewBinding.recycler.layoutManager = LinearLayoutManager(this)
        viewBinding.recycler.adapter = UserAdapter(null).also { userAdapter = it }
        loadPager = LoadPager(viewBinding.recycler)
        loadPager.setAction { requestData() }
    }

    private fun initViewModel() {
        viewModel.userListLiveData.observe(this, { users: List<User?> -> userAdapter.setData(users, true) })
        viewModel.loadingLiveData.observe(this,
                { resultData: LoadStatus ->
                    if (resultData.isLoading) {
                        loadPager.showLoading()
                    } else if (resultData.isFailed) {
                        loadPager.showFailed()
                    } else {
                        loadPager.finishLoad()
                    }
                })
    }

    private fun requestData() {
        viewModel.getUserInfo()
    }
}