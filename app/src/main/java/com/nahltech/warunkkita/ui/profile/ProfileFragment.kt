package com.nahltech.warunkkita.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.auth.LoginActivity
import com.nahltech.warunkkita.data.models.ResponseImageUploader
import com.nahltech.warunkkita.data.models.User
import com.nahltech.warunkkita.data.network.ApiClient
import com.nahltech.warunkkita.utils.Constants
import com.nahltech.warunkkita.utils.WrappedResponse
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var api = ApiClient.instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        logout()
        click()
    }

    @SuppressLint("SetTextI18n")
    private fun click(){
        cb_free_delivery.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tv_free_delivery.text = "Batalkan Free Ongkir"
            } else {
                tv_free_delivery.text = "Free Ongkir"
            }
        }
        btn_change_image_profile.setOnClickListener {
            EasyImage.openChooserWithGallery(
                this@ProfileFragment,
                "Pilih",
                1
            )
        }
        edit_account_profile.setOnClickListener {
            val moveIntent = Intent(context, EditProfileActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun logout() {
        logout.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Konfirmasi")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton(
                    "Ya"
                ) { _: DialogInterface?, _: Int ->

                    context?.let { it1 -> Constants.clearToken(it1) }
                    startActivity(Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    }).also { activity?.finish() }

                }
                .setNegativeButton("Tidak") { dialog: DialogInterface?, _: Int ->
                    dialog!!.dismiss()
                }
                .show()
        }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.fetchProfile(Constants.getToken(requireContext()))
        profileViewModel.getUsers().observe(viewLifecycleOwner, Observer {
            fill(it)
        })

        profileViewModel.getState().observe(viewLifecycleOwner, Observer {
            handleUIState(it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun fill(user: User) {
        name_profile.text = user.name
        email_profile.text = user.email
        no_hp_profile.text = user.phone
        registered_time.text = "Bergabung sejak " + user.registered
        shopping_count.text = user.shoppingCount
        my_selling.text = user.orderCount
        my_product.text = user.shoppingCount
        if (user.image.isNullOrEmpty()) {
            profile_image.setImageDrawable(context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.person_sample
                )
            })
        } else {
            profile_image.load(user.image)
        }
        if (user.seller.isNullOrEmpty()) {
            seller_status.visibility = View.GONE
        }

        if (user.freeOngkir.isNullOrEmpty()) {
            cb_free_delivery.isChecked
            tv_free_delivery.text = "Free Ongkir"
        } else {
            tv_free_delivery.text = "Batalkan Free Ongkir"
        }
    }

    private fun handleUIState(it: UsersState) {
        when (it) {
            is UsersState.IsLoading -> isLoading(it.state)
            is UsersState.Error -> {
                toast(it.err)
                isLoading(false)
            }
            is UsersState.Failed -> {
                toast(it.message)
                isLoading(false)
                context?.let { it1 -> Constants.clearToken(it1) }
                startActivity(Intent(context, LoginActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }).also { activity?.finish() }

            }
            is UsersState.ShowToast -> toast(it.message)
        }
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            sh_profile.visibility = View.VISIBLE
            sh_profile.startShimmerAnimation()
            profile_show.visibility = View.GONE
        } else {
            sh_profile.visibility = View.GONE
            sh_profile.stopShimmerAnimation()
            profile_show.visibility = View.VISIBLE
        }
    }

    private fun toast(message: String?) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            requireActivity(),
            object : DefaultCallback() {
                override fun onImagePicked(
                    imageFile: File,
                    source: EasyImage.ImageSource,
                    type: Int
                ) {
                    sh_profile.visibility = View.VISIBLE
                    sh_profile.startShimmerAnimation()
                    profile_show.visibility = View.GONE
                    //val token = Constants.getToken(requireContext())
                    val id = Constants.getIdUser(requireContext())
                    val requestFile =
                        RequestBody.create(MediaType.parse("multipart/from-data"), imageFile)
                    val image =
                        MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
                    api.changeImage(id, image)
                        .enqueue(object : Callback<WrappedResponse<ResponseImageUploader>> {
                            override fun onFailure(
                                call: Call<WrappedResponse<ResponseImageUploader>>,
                                t: Throwable
                            ) {
                                setupViewModel()
                                Toast.makeText(context, "gagal upload gambar", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            override fun onResponse(
                                call: Call<WrappedResponse<ResponseImageUploader>>,
                                response: Response<WrappedResponse<ResponseImageUploader>>
                            ) {
                                setupViewModel()
                                Toast.makeText(
                                    context,
                                    "Berhasil update profil",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        })

                }

                override fun onImagePickerError(
                    e: Exception,
                    source: EasyImage.ImageSource,
                    type: Int
                ) {
                    super.onImagePickerError(e, source, type)
                    Toast.makeText(context, "" + e.message, Toast.LENGTH_SHORT).show()
                }

            })
    }
}