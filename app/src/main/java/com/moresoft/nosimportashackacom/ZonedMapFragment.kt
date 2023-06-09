package com.moresoft.nosimportashackacom

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import java.net.URLEncoder
import kotlin.properties.Delegates


class ZonedMapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var statusUser by Delegates.notNull<Boolean>()
    private lateinit var redzone: List<LatLng>
    //private var riskZone: Boolean = false
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationClientProviderClient: FusedLocationProviderClient
    private val perimissionCode=101
    val polyPointsGreen : PolygonOptions = PolygonOptions().add(LatLng(-17.354830, -66.156136))
        .add(LatLng(-17.355147, -66.155085))
        .add(LatLng(-17.355834, -66.155254))
        .add(LatLng(-17.355311, -66.156316))
        .add(LatLng(-17.354830, -66.156136))
        .fillColor(Color.parseColor("#95FF99"))
        .strokeColor(Color.GREEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClientProviderClient=LocationServices.getFusedLocationProviderClient(this.requireContext())
        getCurrentLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Initialize view
        val view: View = inflater.inflate(R.layout.fragment_zoned_map, container, false)

        // Initialize map fragment


        // Return view
        //createMarker()
        return view
    }
    fun pointIsInPolygon(point: LatLng, polygon: List<LatLng>): Boolean {
        var isInPolygon = false
        val size = polygon.size

        //Iterate through each point of the polygon
        for (i in 0 until size) {
            val currentPoint = polygon[i]
            val nextPoint = polygon[(i + 1) % size]

            //Check if the point is on the polygon edge
            if (point == currentPoint || point == nextPoint) {
                return true
            }

            //Check if the point is inside the polygon
            if ((currentPoint.latitude > point.latitude) != (nextPoint.latitude > point.latitude) &&
                point.longitude < (nextPoint.longitude - currentPoint.longitude) * (point.latitude - currentPoint.latitude) /
                (nextPoint.latitude - currentPoint.latitude) + currentPoint.longitude
            ) {
                isInPolygon = !isInPolygon
            }
        }
        return isInPolygon
    }

    private fun createPolyline( ): PolygonOptions {
        val polyPoints : PolygonOptions = PolygonOptions()
            .add(LatLng(-17.354822, -66.156136))
            .add(LatLng(-17.355161, -66.155111))
            .add(LatLng(-17.354780, -66.155021))
            .add(LatLng(-17.354520, -66.156023))
            .add(LatLng(-17.354822, -66.156136))
            .fillColor(Color.parseColor("#BFF06262"))
            .strokeColor(Color.RED)
        //val polygon : Polyline = googleMap.addP

        if(pointIsInPolygon(LatLng(currentLocation.latitude,currentLocation.longitude),polyPoints.points)){
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("!Cuidado usted acaba de entra a una zona de riesgo")
                setMessage("Estimado usuario usuario acaba de ingresar a una zona de riesgo alto por lo cual estaremos preguntando por su integridad cada cierto tiempo")
                setPositiveButton("Estoy Bien"){_:DialogInterface,_:Int ->
                    statusUser=true
                }
                setNegativeButton("Ayuda"){_,_ ->
                    sentMessage()
                    val myfragment = PanicButtonFragment()
                    val fragmentManager = requireActivity().supportFragmentManager
                    val fragmentTransaction= fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frame_layout,myfragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()

                }
            }.create().show()
        }

        redzone=polyPoints.points

        return polyPoints
    }
    private fun createMarker(){
        val coordinates = LatLng(-17.394178, -66.145860)
        val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Mi markador")
        mMap.addMarker(marker)
    }
    private fun getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this.requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),perimissionCode)
            return
        }
        val getLocation=fusedLocationClientProviderClient.lastLocation.addOnSuccessListener {
            location->
            if (location!=null){
                currentLocation=location
                Toast.makeText(context,currentLocation.latitude.toString()+""+currentLocation.longitude.toString(),Toast.LENGTH_LONG).show()
                val supportMapFragment =
                    childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

                // Async map
                supportMapFragment!!.getMapAsync { googleMap ->

                    /*currentLocation.latitude=-17.393287
                    currentLocation.longitude=-66.144586*/
                    val coordinates = LatLng(currentLocation.latitude,currentLocation.longitude)
                    val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Mi markador")
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
                    googleMap.addMarker(marker)
                    googleMap.addPolygon(createPolyline())
                    googleMap.addPolygon(polyPointsGreen)


                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            perimissionCode->if (grantResults.isNotEmpty()&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getCurrentLocation()
            }
        }
    }
    fun sentMessage(){
        val  number = +59175989769
        val i = Intent(Intent.ACTION_VIEW)
        i.type="text/plain"

        //i.putExtra(Intent.EXTRA_TEXT,"Hola")
        val url = "https://api.whatsapp.com/send?phone=" + number.toString() + "&text="+
                URLEncoder.encode("Ayuda me encuentro en peligro, en la siguiente ubicacion: https://www.google.com/maps/search/?api=1&query=${currentLocation.latitude},${currentLocation.longitude}","UTF-8")
        i.setPackage("com.whatsapp")
        i.data = Uri.parse(url)

        startActivity(i)


    }
}

