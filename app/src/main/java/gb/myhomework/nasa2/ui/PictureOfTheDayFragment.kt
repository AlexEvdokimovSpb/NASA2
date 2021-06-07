package gb.myhomework.nasa2.ui

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.IconMarginSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import gb.myhomework.nasa2.R
import gb.myhomework.nasa2.model.repo.PictureOfTheDayData
import gb.myhomework.nasa2.viewmodel.PictureOfTheDayViewModel
import kotlinx.android.synthetic.main.bottom_sheet_layout.*

private const val DELAY = "delay"

class PictureOfTheDayFragment : Fragment() {

    private var delayDay: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            delayDay = it.getInt(DELAY)
        }
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.picture_of_day_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        input_layout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${input_edit_text.text.toString()}")
            })
        }
        activity?.let {
            bottom_sheet_title.typeface =
                Typeface.createFromAsset(it.assets, "FranxurterTotallyFat.ttf")
            bottom_sheet_date.typeface =
                Typeface.createFromAsset(it.assets, "FranxurterTotallyMedium.ttf")
            bottom_sheet_copyright.typeface =
                Typeface.createFromAsset(it.assets, "FranxurterTotallyFat.ttf")
            bottom_sheet_explanation.typeface =
                Typeface.createFromAsset(it.assets, "FranxurterTotallyMedium.ttf")
        }

        viewModel.getData(delayDay)
            .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //showSuccess()
                    childFragmentManager
                        .beginTransaction()
                        .replace(
                            R.id.child_fragment_container,
                            if (serverResponseData.mediaType == "image") {
                                PictureFragment.newInstance(url)
                            } else { //mediaType =="video"
                                VideoFragment.newInstance(url)
                            }
                        )
                        .commit()

                    titleSpan(bottom_sheet_title, serverResponseData.title)
                    dateSpan(bottom_sheet_date, serverResponseData.date)
                    copyrightSpan(bottom_sheet_copyright, serverResponseData.copyright)
                    explanationSpan(bottom_sheet_explanation, serverResponseData.explanation)
                }
            }
            is PictureOfTheDayData.Loading -> {
                //showLoading()
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun Fragment.toast(string: String?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).apply {
            setGravity(Gravity.BOTTOM, 0, 250)
            show()
        }
    }

    fun titleSpan(textView: TextView, text: String?) {
        if (text != null) {
            val spannable = SpannableString(text)
            spannable.setSpan(
                IconMarginSpan(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.icons8_tag_64
                    ), 30
                ), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorAccent
                    )
                ),
                0, text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannable
        }
    }

    fun dateSpan(textView: TextView, text: String?) {
        if (text != null) {
            val spannable = SpannableString(text)
            spannable.setSpan(
                IconMarginSpan(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.icons8_date_64
                    ), 30
                ), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorAccent
                    )
                ),
                0, text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannable
        }
    }

    fun copyrightSpan(textView: TextView, text: String?) {
        if (text != null) {
            val spannable = SpannableString(text)
            spannable.setSpan(
                IconMarginSpan(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.icons8_copyright_64
                    ), 30
                ), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.colorAccent
                    )
                ),
                0, text.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannable
        }
    }

    fun explanationSpan(textView: TextView, text: String?) {
        if (text != null) {
            val spannable = SpannableString(text)
            spannable.setSpan(
                IconMarginSpan(
                    BitmapFactory.decodeResource(
                        resources,
                        R.drawable.icons8_telescope_50
                    ), 0
                ), 0, text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.text = spannable
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(delayDay: Int) =
            PictureOfTheDayFragment().apply {
                arguments = Bundle().apply {
                    putInt(DELAY, delayDay)
                }
            }
    }

}
