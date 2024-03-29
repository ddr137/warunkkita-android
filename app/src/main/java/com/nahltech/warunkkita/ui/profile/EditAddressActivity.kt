package com.nahltech.warunkkita.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.data.network.ApiClient
import com.nahltech.warunkkita.utils.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit_address.*

class EditAddressActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel

    private var disposable: Disposable? = null
    private var api = ApiClient.instance()

    private var provinces = mutableListOf<String>()
    var provincesId = mutableListOf<Long>()

    private var cities = mutableListOf<String>()
    var citiesId = mutableListOf<Long>()

    private var districts = mutableListOf<String>()
    var districtsId = mutableListOf<Long>()

    private var villages = mutableListOf<String>()
    var villagesId = mutableListOf<Long>()

    var idProvince: String = ""
    var idCity: String = ""
    var idDistrict: String = ""
    var idVillage: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        toolbarUI()
        getProvince()
        setSpinner()
        setupViewModel()
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_edit_address)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_edit_address.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_edit_address.setNavigationOnClickListener { finish() }
    }

    private fun setSpinner() {
        val adapterProvinsi = ArrayAdapter(
            this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.provinsi)
        )
        adapterProvinsi.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        spinner_province_address.adapter = adapterProvinsi

        val adapterKota = ArrayAdapter(
            this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.kota)
        )
        adapterKota.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        spinner_city_address.adapter = adapterKota
        spinner_city_address.isEnabled = false

        val adapterKecamatan = ArrayAdapter(
            this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.kecamatan)
        )
        adapterKecamatan.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        spinner_district_address.adapter = adapterKecamatan
        spinner_district_address.isEnabled = false

        val adapterDesa = ArrayAdapter(
            this,
            R.layout.spinner_single_simple, resources.getStringArray(R.array.desa)
        )
        adapterDesa.setDropDownViewResource(R.layout.spinner_dropdown_simple)
        spinner_village_address.adapter = adapterDesa
        spinner_village_address.isEnabled = false
    }

    private fun getProvince() {
        provinceLoad(true)
        disposable = api.provinces()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    provinces.clear()
                    provincesId.clear()

                    provinces.add("Pilih Provinsi")
                    provincesId.add(0)

                    result.data!!.forEach {
                        provinces.add(it.provinceName)
                        provincesId.add(it.id)
                    }

                    val adapterProvinsi = ArrayAdapter(
                        this,
                        R.layout.spinner_single_simple, provinces
                    )
                    adapterProvinsi.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    spinner_province_address.adapter = adapterProvinsi

                    provinceLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    provinceLoad(false)
                }
            )

        spinner_province_address.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (provincesId.count() > 0 && position > 0) {
                        idProvince = provinces[position]
                        getCity(provincesId[position]).toString()
                    }
                }
            }


    }

    private fun provinceLoad(status: Boolean) {
        spinner_province_address.isEnabled = !status
        spinner_city_address.isEnabled = false
        spinner_district_address.isEnabled = false
        spinner_village_address.isEnabled = false
    }

    private fun getCity(id: Long) {
        cityLoad(true)
        disposable = api.cities(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->

                    cities.clear()
                    citiesId.clear()

                    cities.add("Pilih Kota/Kab")
                    citiesId.add(0)

                    result.data!!.forEach {
                        cities.add(it.cityName)
                        citiesId.add(it.id)
                    }

                    val adapterCity = ArrayAdapter(
                        this,
                        R.layout.spinner_single_simple, cities
                    )
                    adapterCity.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    spinner_city_address.adapter = adapterCity

                    cityLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    cityLoad(false)
                }
            )

        spinner_city_address.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (citiesId.count() > 0 && position > 0) {
                    idCity = cities[position]
                    getDistrict(citiesId[position]).toString()
                }
            }
        }
    }

    private fun cityLoad(status: Boolean) {
        spinner_province_address.isEnabled = !status
        spinner_city_address.isEnabled = !status
        spinner_district_address.isEnabled = false
        spinner_village_address.isEnabled = false
    }

    private fun getDistrict(id: Long) {
        districtLoad(true)
        disposable = api.districts(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->

                    districts.clear()
                    districtsId.clear()

                    districts.add("Pilih Kecamatan")
                    districtsId.add(0)

                    result.data!!.forEach {
                        districts.add(it.districtName)
                        districtsId.add(it.id)
                    }

                    val adapterDistrict = ArrayAdapter(
                        this,
                        R.layout.spinner_single_simple, districts
                    )
                    adapterDistrict.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    spinner_district_address.adapter = adapterDistrict

                    districtLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    districtLoad(false)
                }
            )



        spinner_district_address.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (districtsId.count() > 0 && position > 0) {
                        idDistrict = districts[position]
                        getVillage(districtsId[position]).toString()
                    }
                }
            }
    }

    private fun districtLoad(status: Boolean) {
        spinner_province_address.isEnabled = !status
        spinner_city_address.isEnabled = !status
        spinner_district_address.isEnabled = !status
        spinner_village_address.isEnabled = false
    }

    private fun getVillage(id: Long) {
        villageLoad(true)
        disposable = api.villages(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    villages.clear()
                    villagesId.clear()

                    villages.add("Pilih Kelurahan/Desa")
                    villagesId.add(0)

                    result.data!!.forEach {
                        villages.add(it.villageName)
                        villagesId.add(it.id)
                    }

                    val adapterVillage = ArrayAdapter(
                        this,
                        R.layout.spinner_single_simple, villages
                    )
                    adapterVillage.setDropDownViewResource(R.layout.spinner_dropdown_simple)
                    spinner_village_address.adapter = adapterVillage

                    villageLoad(false)

                },
                { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    villageLoad(false)
                }
            )

        spinner_village_address.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (villagesId.count() > 0 && position > 0) {
                        idVillage = villages[position]
                        villagesId[position].toString()
                    }
                }
            }
    }

    private fun villageLoad(status: Boolean) {
        spinner_province_address.isEnabled = !status
        spinner_city_address.isEnabled = !status
        spinner_district_address.isEnabled = !status
        spinner_village_address.isEnabled = !status
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        doUpdate()

        profileViewModel.getState().observe(this, Observer {
            handleUIState(it)
        })
    }

    private fun doUpdate() {
        btn_edit_address.setOnClickListener {
            val token = Constants.getToken(this)
            val id = Constants.getIdUser(this)
            val provinceId = idProvince.trim()
            val cityId = idCity.trim()
            val districtId = idDistrict.trim()
            val villageId = idVillage.trim()
            val completeAddress = complete_address_address.text.toString().trim()
            val postalCode = postal_code_address.text.toString().trim()
            if (profileViewModel.validateAddress(
                    idProvince,
                    idCity,
                    idDistrict,
                    idVillage,
                    completeAddress,
                    postalCode
                )
            ) {
                profileViewModel.editAddress(
                    token,
                    id,
                    provinceId,
                    cityId,
                    districtId,
                    villageId,
                    completeAddress,
                    postalCode
                )
            }
        }
    }

    private fun handleUIState(it: UsersState) {
        when (it) {
            is UsersState.IsLoading -> isLoading(it.state)
            is UsersState.Error -> {
                toast(it.err)
                isLoading(false)
            }
            is UsersState.ShowToast -> toast(it.message)
            is UsersState.IsSuccess -> {
                when(it.what){
                    0 -> {
                        toast("Berhasil dibuat")
                        finish()
                    }
                    1 -> {
                        toast("Berhasil diupdate")
                        finish()
                    }
                    2 -> {
                        toast("Berhasil delete")
                        finish()
                    }
                }
            }
        }
    }

    private fun toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun isLoading(state: Boolean) {
        if (state) {
            btn_edit_address.isEnabled = false

            btn_edit_address.visibility = View.GONE
            sh_save_edit_address.visibility = View.VISIBLE
            //loading.isIndeterminate = true
            sh_save_edit_address.startShimmerAnimation()
        } else {
            sh_save_edit_address.apply {
                //isIndeterminate = false
                btn_edit_address.visibility = View.VISIBLE
                sh_save_edit_address.visibility = View.GONE
                sh_save_edit_address.stopShimmerAnimation()
                //progress = 0
            }
            btn_edit_address.isEnabled = true
        }
    }

}