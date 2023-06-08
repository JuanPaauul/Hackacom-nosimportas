package com.moresoft.nosimportashackacom

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
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
import com.google.android.gms.maps.model.*
import kotlin.properties.Delegates


class ZonedMapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var statusUser by Delegates.notNull<Boolean>()
    private lateinit var redzone: List<LatLng>
    //private var riskZone: Boolean = false
    //private lateinit var lastLocation: Location
    private lateinit var currentLocation: Location
   // private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var fusedLocationClientProviderClient: FusedLocationProviderClient
    private val perimissionCode=101
    val polyPointsGreen : PolygonOptions = PolygonOptions().add(LatLng(-17.392308, -66.145205))
        .add(LatLng(-17.394992, -66.144584))
        .add(LatLng(-17.395635, -66.148144))
        .add(LatLng(-17.392914, -66.148822))
        .add(LatLng(-17.392308, -66.145205))
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
    ): View? {
        // Initialize view
        val view: View = inflater.inflate(R.layout.fragment_zoned_map, container, false)

        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        // Async map
        supportMapFragment!!.getMapAsync { googleMap ->
            val coordinates = LatLng(currentLocation.latitude,currentLocation.longitude)
            val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Mi markador")
            googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
            googleMap.addMarker(marker)
            val polygon: Polygon = googleMap.addPolygon(createPolyline())
            googleMap.addPolygon(polyPointsGreen)


        }

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
        val polyPoints : PolygonOptions = PolygonOptions().add(LatLng(-17.392308, -66.145205))
            .add(LatLng(-17.392308, -66.145205))
            .add(LatLng(-17.394992, -66.144584))
            .add(LatLng(-17.394440, -66.142310))
            .add(LatLng(-17.391860, -66.142803))
            .add(LatLng(-17.392308, -66.145205))
            .fillColor(Color.parseColor("#BFF06262"))
            .strokeColor(Color.RED)
        //val polygon : Polyline = googleMap.addP

        if(pointIsInPolygon(LatLng(-17.393287, -66.144586),polyPoints.points)){
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("!Cuidado usted acaba de entra a una zona de riesgo")
                setMessage("Estimado usuario usuario acaba de ingresar a una zona de riesgo alto por lo cual estaremos preguntando por su integridad cada cierto tiempo")
                setPositiveButton("Estoy Bien"){_:DialogInterface,_:Int ->
                    statusUser=true
                }
                setNegativeButton("Ayuda",null)
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
    //private fun islocationPermissionOn()=ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED

    /*private fun enableLocation(){
        if(!::mMap.isInitialized)return
        if (islocationPermissionOn()){
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            mMap.isMyLocationEnabled=true
        }
        else{
            // requestLocationPermission()
        }
    }*/
    private fun getCurrentLocation(){
        if(ActivityCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
            PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this.requireContext() as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),perimissionCode)
            return
        }
        val getLocation=fusedLocationClientProviderClient.lastLocation.addOnSuccessListener {
            location-> if (location!=null){
                currentLocation=location
                Toast.makeText(context,currentLocation.latitude.toString()+""+currentLocation.longitude.toString(),Toast.LENGTH_LONG).show()
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
}
/*private fun requestLocationPermission () {*/
