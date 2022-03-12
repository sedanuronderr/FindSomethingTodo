package com.example.findsomethingtodo.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.findsomethingtodo.R
import com.example.findsomethingtodo.databinding.FragmentRandomActivitiyBinding
import com.example.findsomethingtodo.model.RandomActivity
import com.example.findsomethingtodo.viewmodel.RandomActivityViewModel
import java.util.*


class RandomActivitiyFragment : Fragment() {

private lateinit var binding :FragmentRandomActivitiyBinding

private lateinit var mRandomActivityViewModel:RandomActivityViewModel
private lateinit var  mProgressDialog :Dialog
    private lateinit var currActivity: RandomActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomActivitiyBinding.inflate(inflater,container,false)
        return binding.root // Inflate the layout for this fragment

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRandomActivityViewModel= ViewModelProvider(this).get(RandomActivityViewModel::class.java)
        mRandomActivityViewModel.getRandomActivityFromAPI()

        binding.swipeRefresh.setOnRefreshListener {
            mRandomActivityViewModel.getRandomActivityFromAPI()
        }

        randomActivityObserver()
    }
    private fun randomActivityObserver(){
        mRandomActivityViewModel.randomActivityResponse.observe(viewLifecycleOwner,{
            randomActivity->

            randomActivity?.let{
                currActivity = it

                if(binding.swipeRefresh.isRefreshing == true){
                    binding.swipeRefresh.isRefreshing=false
                }
                binding.tvActivity.text = it.activity
                binding.tvType.text = it.type
                binding.tvAccessibility.text = it.accessibility.toString()
                binding.tvParticipants.text = it.participants.toString()
                binding.tvPrice.text = it.price.toString()
color()
                if (it.link.isBlank()) {
                    binding.btnSeeMoreDetails.visibility = View.GONE
                } else {
                    binding.btnSeeMoreDetails.visibility = View.VISIBLE
                    showActivityDetails(it)
                }
            }

        })
        mRandomActivityViewModel.randomActivityloadERROR.observe(
            viewLifecycleOwner,
            { dataError ->
                dataError?.let {
                    Log.e(
                        "aaa", "randomDishViewModelObserver: $dataError"
                    )
                    if (binding.swipeRefresh.isRefreshing) {
                        binding.swipeRefresh.isRefreshing = false
                    }
                }
            })

        mRandomActivityViewModel.loadRandomActivity.observe(viewLifecycleOwner, { loadRandomDish ->
            loadRandomDish?.let {
                if (loadRandomDish && !binding.swipeRefresh.isRefreshing) {
                    showProgressDialog()
                } else {
                    hideProgressDialog()
                }
            }
        })

    }
    private fun color() {
        val random = Random()
        val color =
            Color.argb(
                255,
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256)
            )

        binding.ivActImage.setBackgroundColor(color)
    }
    private fun showProgressDialog() {
        mProgressDialog = Dialog(requireActivity())
        mProgressDialog.let {
            it.setContentView(R.layout.dialog_custom_progress)
            it.show()
        }
    }

    private fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    private fun showActivityDetails(activity: RandomActivity) {
        binding.btnSeeMoreDetails.setOnClickListener { mView ->
            val direction = RandomActivitiyFragmentDirections
                .actionRandomActivitiyFragmentToActivityDetailFragment(activity)
            mView.findNavController().navigate(direction)
        }
    }


}