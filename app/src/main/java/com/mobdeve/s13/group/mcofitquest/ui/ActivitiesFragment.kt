package com.mobdeve.s13.group.mcofitquest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group.mcofitquest.DataHelper
import com.mobdeve.s13.group.mcofitquest.MyActivitiesAdapter
import com.mobdeve.s13.group.mcofitquest.R
import com.mobdeve.s13.group.mcofitquest.SharedViewModel
import com.mobdeve.s13.group.mcofitquest.models.Activity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ActivitiesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ActivitiesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val activities: ArrayList<Activity> = DataHelper.initializeMyActivitiesData()
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activitiesFetched = ArrayList<Activity>()

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.userDetails.observe(viewLifecycleOwner, { user ->
            // Use the userDetails here
            for(a in user.history){
                activitiesFetched.add(Activity(a.day,a.totalCalories,a.totalMinutes,a.workouts[0].gifUrl))
            }
        })

        // Initialize the RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.my_activities_rv)

        // Set the LayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)


        // Initialize your adapter
        val adapter = MyActivitiesAdapter(activitiesFetched) // Assuming activities is your data list

        // Set the Adapter
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ActivitiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}