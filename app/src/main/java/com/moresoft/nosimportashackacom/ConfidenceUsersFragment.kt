package com.moresoft.nosimportashackacom

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.moresoft.domain.ConfidenceUser
import com.moresoft.framework.RestApiAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConfidenceUsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfidenceUsersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var rootView: View
    lateinit var recyclerView : RecyclerView

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
        val restApiAdapter = RestApiAdapter()
        val endPoint = restApiAdapter.connectionApi()
        val bookResponseCall = endPoint.getAllPost()
        /*ookResponseCall.enqueue( object : Callback<List<ConfidenceUser>> {
            override fun onFailure(call: Call<List<ConfidenceUser>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<List<ConfidenceUser>>, response: Response<List<ConfidenceUser>>) {
                val posts = response.body()
                Log.d("RESP POST", Gson().toJson(posts))
                posts?.forEach {
                    Log.d("RESP user name", it.name)
                    Log.d("RESP user email", it.email)
                }
            }
        })*/

        val list = arrayListOf<ConfidenceUser>( ConfidenceUser("roberto1", "calisaya","calyr.software@gmail.com", "image"),
            ConfidenceUser("roberto2", "calisaya", "calyr.software@gmail.com", "image"),
            ConfidenceUser("roberto3", "calisaya","calyr.software@gmail.com", "image"),
            ConfidenceUser("roberto4", "calisaya","calyr.software@gmail.com", "image")
        )

        rootView = inflater.inflate(R.layout.fragment_confidence_users, container, false)

        val linearLayoutManager = LinearLayoutManager(this.context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = UserListAdapter(list, this)

        // Inflate the layout for this fragment
        return rootView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConfidenceUsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfidenceUsersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}