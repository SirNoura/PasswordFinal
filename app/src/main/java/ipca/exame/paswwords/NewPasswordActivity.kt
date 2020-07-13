package ipca.exame.paswwords

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewPasswordActivity : AppCompatActivity() {

    private lateinit var editWordView: EditText
    private lateinit var editSiteView: EditText
    private lateinit var editDiscView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)

        editSiteView = findViewById(R.id.edit_site)
        editWordView = findViewById(R.id.edit_ord)
        editDiscView = findViewById(R.id.edit_desc)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val site = editSiteView.text.toString()
                val word = editWordView.text.toString()
                val disc = editSiteView.text.toString()

                replyIntent.putExtra(EXTRA_REPLY_pass, site)
                replyIntent.putExtra(EXTRA_REPLY_site, word)
                replyIntent.putExtra(EXTRA_REPLY_disc, disc)

                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY_pass = "password"
        const val EXTRA_REPLY_site = "site"
        const val EXTRA_REPLY_disc= "disc"
    }
}