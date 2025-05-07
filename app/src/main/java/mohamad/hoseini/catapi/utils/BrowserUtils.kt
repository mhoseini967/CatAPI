package mohamad.hoseini.catapi.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.net.toUri


object BrowserUtils{
    fun openUrl(context: Context, url: String) {
        try {
            val uri = url.toUri()
            val intent = Intent(Intent.ACTION_VIEW, uri)

            // Check if there's an app that can handle this intent (like a browser)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "No browser found to open the link", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // In case of an invalid URL or any other issue
            Toast.makeText(context, "Failed to open the URL", Toast.LENGTH_SHORT).show()
        }
    }

}