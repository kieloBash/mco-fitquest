package com.mobdeve.s13.group.mcofitquest.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.group.mcofitquest.MyActivitiesAdapter
import com.mobdeve.s13.group.mcofitquest.R
import com.mobdeve.s13.group.mcofitquest.WorkoutActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null




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
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gradientBox: ConstraintLayout = view.findViewById(R.id.gradient_box)
        gradientBox.setOnClickListener {
            // Use requireActivity() to get the activity context
            val intent = Intent(requireActivity(), WorkoutActivity::class.java)
            intent.putExtra(dayKey,"1")
            intent.putExtra(caloriesKey,"2780")
            intent.putExtra(minutesKey,"23")
            startActivity(intent)
        }
    }

    companion object {
        // These are static keys that you can use for the passing data around.
        const val dayKey : String = "DAY_KEY"
        const val caloriesKey : String = "CALORIES_KEY"
        const val minutesKey : String = "MINUTES_KEY"

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}