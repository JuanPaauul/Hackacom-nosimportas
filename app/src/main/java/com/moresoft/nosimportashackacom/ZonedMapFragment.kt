package com.moresoft.nosimportashackacom

import android.Manifest
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlin.properties.Delegates


class ZonedMapFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var statusUser by Delegates.notNull<Boolean>()
    private lateinit var redzone: List<LatLng>
    public var riskZone=false
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val polyPointsGreen : PolygonOptions = PolygonOptions().add(LatLng(-17.392308, -66.145205))
        .add(LatLng(-17.394992, -66.144584))
        .add(LatLng(-17.395635, -66.148144))
        .add(LatLng(-17.392914, -66.148822))
        .add(LatLng(-17.392308, -66.145205))
        .fillColor(Color.parseColor("#95FF99"))
        .strokeColor(Color.GREEN)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            // When map is loaded
            /*googleMap.setOnMapClickListener { latLng -> // When clicked on map
                // Initialize marker options
                val markerOptions = MarkerOptions()
                // Set position of marker
                markerOptions.position(latLng)
                // Set title of marker
                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude)
                // Remove all marker
                googleMap.clear()
                // Animating to zoom the marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
                // Add marker on map
                googleMap.addMarker(markerOptions)*/
            val coordinates = LatLng(-17.393287, -66.144586)
            val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Mi markador")
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
            googleMap.addMarker(marker)
            val polygon: Polygon = googleMap.addPolygon(createPolyline())
            googleMap.addPolygon(polyPointsGreen)


        }
        if (riskZone){
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("!Cuidado usted acaba de entra a una zona de riesgo")
                setMessage("Estimado usuario usuario acaba de ingresar a una zona de riesgo alto por lo cual estaremos preguntando por su integridad cada cierto tiempo")
                setPositiveButton("Estoy Bien"){_:DialogInterface,_:Int ->
                    statusUser=true
                }
                setNegativeButton("Ayuda",null)
            }
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

        riskZone=pointIsInPolygon(LatLng(-17.393287, -66.144586),polyPoints.points)

        redzone=polyPoints.points

        return polyPoints
    }
    private fun createMarker(){
        val coordinates = LatLng(-17.394178, -66.145860)
        val marker: MarkerOptions = MarkerOptions().position(coordinates).title("Mi markador")
        mMap.addMarker(marker)
    }
    private fun islocationPermissionOn()=ContextCompat.checkSelfPermission(requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED

     private fun enableLocation(){
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
     }
     /*private fun requestLocationPermission () {
         if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
         ) {
             Toast.makeText( requireActivity(), "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
         } else {
             ActivityCompat.requestPermissions(
                 requireActivity(),
             arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
         }
     }*/

}
