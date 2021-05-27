package gb.myhomework.nasa2.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import gb.myhomework.nasa2.R
import kotlinx.android.synthetic.main.fragment_video.*

private const val URL = "url"

class VideoFragment() : Fragment() {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        url?.let { loadVideo(it) }
    }

    private fun loadVideo(url: String) {
        val uri: Uri = Uri.parse(url)
        val videoId = uri.getPathSegments().get(1)
        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0f)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        youtube_player_view.release();
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            VideoFragment().apply {
                arguments = Bundle().apply {
                    putString(URL, url)
                }
            }
    }
}