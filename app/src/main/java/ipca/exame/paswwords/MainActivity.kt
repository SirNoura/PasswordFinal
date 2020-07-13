package ipca.exame.paswwords

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.recycleview_item.*
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1
    private lateinit var passViewModel: PassViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = PassListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        passViewModel = ViewModelProvider(this).get(PassViewModel::class.java)

        passViewModel.allWords.observe(this, Observer { passwords ->

            passwords?.let { adapter.setWords(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPasswordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val pass = Pass(data.getStringExtra(NewPasswordActivity.EXTRA_REPLY_pass),data.getStringExtra(NewPasswordActivity.EXTRA_REPLY_site),data.getStringExtra(NewPasswordActivity.EXTRA_REPLY_disc), LocalDateTime.now().toString())
                passViewModel.insert(pass)
                Unit
            }
        } else {
            Toast.makeText(
                    applicationContext,
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG
            ).show()
        }
    }

}