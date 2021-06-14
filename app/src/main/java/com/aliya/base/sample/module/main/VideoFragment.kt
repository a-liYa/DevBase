package com.aliya.base.sample.module.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aliya.base.sample.ktx.viewBindings
import com.aliya.base.sample.R
import com.aliya.base.sample.databinding.FragmentVideoBinding

/**
 * Video Fragment
 */
class VideoFragment : Fragment() {

    private val viewBinding by viewBindings(FragmentVideoBinding::bind)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: " + viewBinding.root)
    }
}