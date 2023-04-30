package com.moresoft.nosimportashackacom

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.moresoft.nosimportashackacom.R
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PanicButtonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PanicButtonFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var rootView: View
    lateinit var panicButton: ImageButton
    lateinit var mainLayout: ConstraintLayout
    lateinit var tvPanicButtonTitle: TextView
    lateinit var tvPanicButtonDescription: TextView
    private var buttonTriggered: Boolean = false
    private var panicButtonTitle: String = "Desactivado"
    private var panicButtonDescription: String = "Tus contactos de confianza no saben de tu estado actual"

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
        rootView = inflater.inflate(R.layout.fragment_panic_button, container, false)

        tvPanicButtonTitle = rootView.findViewById<TextView>(R.id.tv_panic_button_title)
        tvPanicButtonDescription = rootView.findViewById<TextView>(R.id.tv_panic_button_state)
        panicButton = rootView.findViewById<ImageButton>(R.id.imageButton)
        mainLayout = rootView.findViewById<ConstraintLayout>(R.id.panic_button_layout)

        tvPanicButtonTitle.setText(panicButtonTitle)
        tvPanicButtonDescription.setText(panicButtonDescription)

        panicButton.setOnClickListener{
            buttonTriggered = !buttonTriggered
            if (buttonTriggered){
                panicButtonTitle = "Activado"
                panicButtonDescription = "¡Tus contactos de confianza fueron notificados!"
                mainLayout.setBackgroundColor(Color.RED)

            }else{
                panicButtonTitle = "Desactivado"
                panicButtonDescription = "Tus contactos de confianza no saben de tu estado actual"
                mainLayout.setBackgroundColor(Color.GRAY)
            }
            tvPanicButtonTitle.setText(panicButtonTitle)
            tvPanicButtonDescription.setText(panicButtonDescription)
        }
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
         * @return A new instance of fragment PanicButtonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PanicButtonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}