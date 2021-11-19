package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.perozzi_package.smashmouthsonggenerator.AlbumGrid
import com.perozzi_package.smashmouthsonggenerator.R
import java.util.*

class WeightAssignmentViewModel(application: Application) : AndroidViewModel(application) {

    var weightGridLayoutManager: GridLayoutManager? =
        GridLayoutManager(application, 3, LinearLayoutManager.VERTICAL, false)

    private val smashMouthDictionary : MutableMap<String,Map<String,Any>> = mutableMapOf(
        Pair(
            "fush_yu_mang", mapOf(
                Pair("name", "Fush Yu Mang"),
                Pair("year", "(1997)"),
                Pair("imageAddress", R.drawable.alb_1_fush_yu_mang_97)
            )
        ),
        Pair(
            "astro_lounge", mapOf(
                Pair("name", "Astro Lounge"),
                Pair("year", "(1999)"),
                Pair("imageAddress", R.drawable.alb_2_astro_lounge_99)
            )
        ),
        Pair(
            "smash_mouth",
            mapOf(
                Pair("name", "Smash Mouth"),
                Pair("year", "(2001)"),
                Pair("imageAddress", R.drawable.alb_3_smash_mouth_01)
            )
        ),
        Pair(
            "get_the_picture", mapOf(
                Pair("name", "Get the Picture"),
                Pair("year", "(2003)"),
                Pair("imageAddress", R.drawable.alb_4_get_the_picture_03)
            )
        ),
        Pair(
            "all_star_smash_hits", mapOf(
                Pair("name", "All Star Smash Hits"),
                Pair("year", "(2005)"),
                Pair("imageAddress", R.drawable.alb_5_all_star_smash_hits_05)
            )
        ),
        Pair(
            "the_gift_of_rock", mapOf(
                Pair("name", "The Gift of Rock"),
                Pair("year", "(2005)"),
                Pair("imageAddress", R.drawable.alb_6_the_gift_of_rock_05)
            )
        ),
        Pair(
            "summer_girl", mapOf(
                Pair("name", "Summer Girl"),
                Pair("year", "(2006)"),
                Pair("imageAddress", R.drawable.alb_7_summer_girl_06)
            )
        ),
        Pair(
            "magic", mapOf(
                Pair("name", "Magic"),
                Pair("year", "(2012)"),
                Pair("imageAddress", R.drawable.alb_8_magic_12)
            )
        )
    )
    private fun oneWeights(map: MutableMap<String, Map<String, Any>>): MutableList<Int> {
        val _list = mutableListOf<Int>()
        for (index in 0 until map.size)
            _list.add(1)
        return _list
    }

    var albumWeights = oneWeights(smashMouthDictionary)
    val yearsList = mutableListOf<String>()
    val titlesList = mutableListOf<String>()
    val imageAddressList = mutableListOf<Int>()
    var arrayForAlbumGrid: ArrayList<AlbumGrid> = arrayListOf()
    fun prepareDataForAdapter() {
        for (album in smashMouthDictionary.keys) {
            yearsList.add(smashMouthDictionary[album]!!["year"] as String)
            titlesList.add(smashMouthDictionary[album]!!["name"] as String)
            imageAddressList.add(smashMouthDictionary[album]!!["imageAddress"] as Int)
        }
        for (index in 0 until yearsList.size) {
            arrayForAlbumGrid.add(
                AlbumGrid(
                    titlesList[index],
                    yearsList[index],
                    imageAddressList[index],
                    albumWeights[index]
                )
            )
        }
    }

/*
    inner class smashMouthAlbum(val name: String, val year: String, val imageAddress: Int)
    val fush_yu_mang: smashMouthAlbum = smashMouthAlbum("Fush Yu Mang", "1997", R.drawable.alb_1_fush_yu_mang_97)
    val astro_lounge = smashMouthAlbum("Astro Lounge", "1999", R.drawable.alb_2_astro_lounge_99)
    val smash_mouth = smashMouthAlbum("Smash Mouth", "2001", R.drawable.alb_3_smash_mouth_01)
    val get_the_picture = smashMouthAlbum("Get the Picture", "2003", R.drawable.alb_4_get_the_picture_03)
    val all_star_smash_hits = smashMouthAlbum("All Star Smash Hits", "2005", R.drawable.alb_5_all_star_smash_hits_05)
    val the_gift_of_rock = smashMouthAlbum("The Gift of Rock", "2005", R.drawable.alb_6_the_gift_of_rock_05)
    val summer_girl = smashMouthAlbum("Summer Girl", "2006", R.drawable.alb_7_summer_girl_06)
    val magic = smashMouthAlbum("Magic", "2012", R.drawable.alb_8_magic_12)
*/

}